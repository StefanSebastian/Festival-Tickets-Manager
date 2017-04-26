package protobuf;

import AppServices.IFestivalClient;
import AppServices.IFestivalServer;
import Domain.Artist;
import Domain.Show;
import Domain.User;
import Validation.Exceptions.ServiceException;
import Validation.Exceptions.ValidatorException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Sebi on 25-Apr-17.
 */
public class ProtoFestivalProxy implements IFestivalServer {
    private String host;
    private int port;

    private IFestivalClient client;

    private InputStream inputStream;
    private OutputStream outputStream;
    private Socket connection;

    private BlockingQueue<FestivalProtobufs.FestivalResponse> qresponses;
    private volatile boolean finished;

    public ProtoFestivalProxy(String host, int port){
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<>();
    }

    private void initializeConnection(){
        try{
            connection = new Socket(host, port);
            outputStream = connection.getOutputStream();
            inputStream = connection.getInputStream();
            finished = false;
            startReader();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void closeConnection(){
        finished = true;
        try {
            inputStream.close();
            outputStream.close();
            connection.close();
            client = null;
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void startReader(){
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private class ReaderThread implements Runnable {
        public void run(){
            while (!finished){
                try{
                    FestivalProtobufs.FestivalResponse response =
                            FestivalProtobufs.FestivalResponse.parseDelimitedFrom(inputStream);
                    if (isUpdate(response.getType())){

                    } else {
                        try {
                            qresponses.put(response);
                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e){
                    System.out.println("Client reader stopped");
                    finished = true;
                }
            }
        }
    }

    private boolean isUpdate(FestivalProtobufs.FestivalResponse.Type type){
        if (type == FestivalProtobufs.FestivalResponse.Type.ShowUpdated){
            return true;
        } else {
            return false;
        }
    }

    private void sendRequest(FestivalProtobufs.FestivalRequest request){
        try{
            System.out.println("Sending request" + request);
            request.writeDelimitedTo(outputStream);
            outputStream.flush();
            System.out.println("Request sent");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private FestivalProtobufs.FestivalResponse readResponse(){
        FestivalProtobufs.FestivalResponse response = null;

        try{
            response = qresponses.take();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        return response;
    }

    @Override
    public List<Artist> getArtists() throws ServiceException {
        sendRequest(ProtoUtils.createGetArtistsRequest());
        FestivalProtobufs.FestivalResponse response = readResponse();
        if (response.getType() == FestivalProtobufs.FestivalResponse.Type.Error){
            throw new ServiceException(response.getError());
        }
        return ProtoUtils.getArtists(response);
    }

    @Override
    public List<Show> getShowsForArtist(Integer idArtist) throws ServiceException {
        sendRequest(ProtoUtils.createGetShowsForArtistRequest(idArtist));
        FestivalProtobufs.FestivalResponse response = readResponse();
        if (response.getType() == FestivalProtobufs.FestivalResponse.Type.Error){
            throw new ServiceException(response.getError());
        }
        return ProtoUtils.getShows(response);
    }

    @Override
    public List<Show> getShowsForDate(String date) throws ServiceException {
        sendRequest(ProtoUtils.createGetShowsForDateRequest(date));
        FestivalProtobufs.FestivalResponse response = readResponse();
        if (response.getType() == FestivalProtobufs.FestivalResponse.Type.Error){
            throw new ServiceException(response.getError());
        }
        return ProtoUtils.getShows(response);
    }

    @Override
    public void buyTicketsForShow(Integer idShow, String clientName, Integer numberOfTickets, String username) throws ServiceException, ValidatorException {

    }

    @Override
    public void login(User user, IFestivalClient client) throws ServiceException {
        initializeConnection();
        sendRequest(ProtoUtils.createLoginRequest(user));
        FestivalProtobufs.FestivalResponse response = readResponse();
        if (response.getType() == FestivalProtobufs.FestivalResponse.Type.Ok){
            this.client = client;
            return;
        }
        if (response.getType() == FestivalProtobufs.FestivalResponse.Type.Error){
            throw new ServiceException(response.getError());
        }
    }

    @Override
    public void logout(User user, IFestivalClient client) throws ServiceException {
        sendRequest(ProtoUtils.createLogoutRequest(user));
        FestivalProtobufs.FestivalResponse response = readResponse();
        closeConnection();
        if (response.getType() == FestivalProtobufs.FestivalResponse.Type.Error){
            throw new ServiceException(response.getError());
        }
    }
}
