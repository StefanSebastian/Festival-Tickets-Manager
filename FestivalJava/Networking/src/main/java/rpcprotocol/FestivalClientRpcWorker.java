package rpcprotocol;

import AppServices.IFestivalClient;
import AppServices.IFestivalServer;
import Domain.Artist;
import Domain.Show;
import Domain.User;
import Validation.Exceptions.ServiceException;
import Validation.Exceptions.ValidatorException;
import dto.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 * Created by Sebi on 29-Mar-17.
 */
public class FestivalClientRpcWorker implements Runnable, IFestivalClient {
    //server
    private IFestivalServer server;

    //connection
    private Socket connection;

    //input + output streams
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    //indicates connection state
    private volatile boolean connected;

    //constructor
    public FestivalClientRpcWorker(IFestivalServer server, Socket connection){
        this.server = server;
        this.connection = connection;

        try{
            outputStream = new ObjectOutputStream(connection.getOutputStream());
            outputStream.flush();
            inputStream = new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(connected){
            try{
                //get a request
                Object request = inputStream.readObject();
                System.out.println("Got a request");

                //process it
                Response response = handleRequest((Request) request);
                System.out.println("Processed request");

                //send response if able
                if (response != null){
                    sendResponse(response);
                    System.out.println("Sent response");
                }


            } catch (IOException | ClassNotFoundException ex){
                System.out.println("Disconnected while trying to read");
                break;
            }
        }

        try{
            inputStream.close();
            outputStream.close();
            connection.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    //handles a request
    private Response handleRequest(Request request){
        Response response = null;

        if (request.getType() == RequestType.LOGIN){
            System.out.println("Login request");

            UserDTO userDTO = (UserDTO)request.getData();
            User user = DTOUtils.getUserFromDTO(userDTO);

            try{
                server.login(user, this);
                System.out.println("Login success");
                return new Response.Builder().type(ResponseType.OK).build();
            } catch (ServiceException e) {
                System.out.println("Login error");
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        if (request.getType() == RequestType.LOGOUT){
            System.out.println("Logout request");

            UserDTO userDTO = (UserDTO)request.getData();
            User user = DTOUtils.getUserFromDTO(userDTO);

            try{
                server.logout(user, this);
                connected = false;
                return new Response.Builder().type(ResponseType.OK).build();
            } catch (ServiceException e){
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        if (request.getType() == RequestType.GET_ARTISTS){
            System.out.println("Get artists request");

            try {
                List<Artist> artists = server.getArtists();
                List<ArtistDTO> artistDTOs = DTOUtils.getListArtistDTO(artists);
                return new Response.Builder().type(ResponseType.GET_ARTISTS).data(artistDTOs).build();
            } catch (ServiceException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        if (request.getType() == RequestType.GET_SHOWS_FOR_ARTIST){
            System.out.println("Get shows for artists request");

            try{
                Integer idArtist = (Integer)request.getData();
                List<Show> shows = server.getShowsForArtist(idArtist);
                List<ShowDTO> showDTOs = DTOUtils.getListShowDTO(shows);
                return new Response.Builder().type(ResponseType.GET_SHOWS).data(showDTOs).build();
            } catch (NumberFormatException e){
                return new Response.Builder().type(ResponseType.ERROR).data("Id must be integer").build();
            } catch (ServiceException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        if (request.getType() == RequestType.GET_SHOWS_FOR_DATE){
            System.out.println("Get shows for date request");

            try{
                String date = (String) request.getData();
                List<Show> shows = server.getShowsForDate(date);
                List<ShowDTO> showDTOs = DTOUtils.getListShowDTO(shows);
                return new Response.Builder().type(ResponseType.GET_SHOWS).data(showDTOs).build();
            } catch (ServiceException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        if (request.getType() == RequestType.BUY_TICKETS){
            System.out.println("Buy tickets request");

            BuyTicketsDTO buyTicketsDTO = (BuyTicketsDTO) request.getData();
            try {
                server.buyTicketsForShow(buyTicketsDTO.getIdShow(), buyTicketsDTO.getClientName(),
                        buyTicketsDTO.getNumberOfTickets(), buyTicketsDTO.getUsername());
                return new Response.Builder().type(ResponseType.OK).build();
            } catch (ServiceException | ValidatorException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        return response;
    }

    //sends a response
    private void sendResponse(Response response) throws IOException{
        System.out.println("Sending response " + response);
        outputStream.writeObject(response);
        System.out.println("flushing");
        outputStream.flush();
    }

    @Override
    public void showUpdated(Show show) throws ServiceException {
        ShowDTO showDTO = DTOUtils.getShowDTO(show);
        Response response = new Response.Builder().type(ResponseType.SHOWS_UPDATED).data(showDTO).build();
        try {
            sendResponse(response);
        } catch (IOException e) {
            throw new ServiceException("Update notify send response error");
        }
    }
}
