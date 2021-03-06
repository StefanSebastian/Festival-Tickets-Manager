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
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Sebi on 29-Mar-17.
 */
public class FestivalServerRpcProxy implements IFestivalServer {
    //host
    private String host;

    //connection
    private Socket connection;
    private int port;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    //reference to client
    private IFestivalClient client;

    //server responses
    private BlockingQueue<Response> queueResponses;
    //indicates conection status
    private volatile boolean finished;

    //constructor
    public FestivalServerRpcProxy(String host, int port){
        this.host = host;
        this.port = port;
        queueResponses = new LinkedBlockingQueue<>();
    }

    @Override
    public List<Artist> getArtists() throws ServiceException{
        Request request = new Request.Builder().type(RequestType.GET_ARTISTS).build();
        sendRequest(request);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR){
            String msg = (String) response.getData();
            throw new ServiceException(msg);
        }
        List<ArtistDTO> artistDTOs = (List<ArtistDTO>) response.getData();
        return DTOUtils.getListArtistFromDTO(artistDTOs);
    }

    @Override
    public List<Show> getShowsForArtist(Integer idArtist) throws ServiceException {
        Request request = new Request.Builder().type(RequestType.GET_SHOWS_FOR_ARTIST).build();
        request.setData(idArtist);
        sendRequest(request);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR){
            String msg = (String) response.getData();
            throw new ServiceException(msg);
        }
        List<ShowDTO> showDTOs = (List<ShowDTO>) response.getData();
        return DTOUtils.getListShowFromDTO(showDTOs);
    }

    @Override
    public List<Show> getShowsForDate(String date) throws ServiceException{
        Request request = new Request.Builder().type(RequestType.GET_SHOWS_FOR_DATE).build();
        request.setData(date);
        sendRequest(request);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR){
            String msg = (String) response.getData();
            throw new ServiceException(msg);
        }
        List<ShowDTO> showDTOs = (List<ShowDTO>) response.getData();
        return DTOUtils.getListShowFromDTO(showDTOs);
    }

    @Override
    public void buyTicketsForShow(Integer idShow, String clientName, Integer numberOfTickets, String username) throws ServiceException, ValidatorException {

        BuyTicketsDTO buyTicketsDTO = new BuyTicketsDTO(idShow, clientName, numberOfTickets, username);
        Request request = new Request.Builder().type(RequestType.BUY_TICKETS).data(buyTicketsDTO).build();

        sendRequest(request);
        Response response = readResponse();

        if (response.getType() == ResponseType.ERROR){
            String msg = (String) response.getData();
            throw new ServiceException(msg);
        }
    }

    @Override
    public void login(User user, IFestivalClient client) throws ServiceException {
        initializeConnection();
        UserDTO userDTO = DTOUtils.getUserDTO(user);
        Request request = new Request.Builder().type(RequestType.LOGIN).data(userDTO).build();
        sendRequest(request);
        Response response = readResponse();
        if (response.getType() == ResponseType.OK){
            this.client = client;
            return;
        }
        if (response.getType() == ResponseType.ERROR){
            String message = (String)response.getData();
            throw new ServiceException(message);
        }
    }

    @Override
    public void logout(User user, IFestivalClient client) throws ServiceException {
        UserDTO userDTO = DTOUtils.getUserDTO(user);
        Request request = new Request.Builder().type(RequestType.LOGOUT).data(userDTO).build();
        sendRequest(request);
        Response response = readResponse();
        closeConnection();
        if (response.getType() == ResponseType.ERROR){
            throw new ServiceException("Error logout");
        }
    }

    /*
    Sends a request to server
     */
    private void sendRequest(Request request) throws ServiceException{
        try{
            System.out.println("Sending request " + request.getType());
            outputStream.writeObject(request);
            outputStream.flush();
        } catch (IOException e) {
            throw new ServiceException("Error sending request");
        }
    }

    /*
    Reads the first response from the queue
     */
    private Response readResponse(){
        Response response = null;
        try{
            response = queueResponses.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    /*
    Initialize connection to server
     */
    private void initializeConnection() throws ServiceException {
        try{
            connection = new Socket(host, port);
            outputStream = new ObjectOutputStream(connection.getOutputStream());
            outputStream.flush();
            inputStream = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (IOException e) {
            throw new ServiceException("Connection to server refused");
        }
    }

    /*
    Closes the connection
     */
    private void closeConnection(){
        finished = true;
        try{
            inputStream.close();
            outputStream.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //starts the reader thread
    private void startReader(){
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    //checks if a response is update type
    private boolean isUpdate(Response response){
        return response.getType().equals(ResponseType.SHOWS_UPDATED);
    }

    //handles updates from server
    private void handleUpdate(Response response){
        //show values updated after transaction
        if (response.getType() == ResponseType.SHOWS_UPDATED){
            try {
                ShowDTO showDTO = (ShowDTO)response.getData();
                Show show = DTOUtils.getShowFromDTO(showDTO);
                client.showUpdated(show);
            } catch (ServiceException | RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    Reader thread
    reads responses from server
     */
    private class ReaderThread implements Runnable{

        @Override
        public void run() {
            while (!finished){
                try{
                    System.out.println("Tries to read..");
                    Object response = inputStream.readObject();
                    System.out.println("Response received " + ((Response)response).getType());
                    if (isUpdate((Response) response)){
                        handleUpdate((Response) response);
                    } else {
                        try{
                            queueResponses.put((Response) response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException | ClassNotFoundException ex){
                    System.out.println("Client reader stopped");
                    finished = true;
                }
            }
        }
    }
}
