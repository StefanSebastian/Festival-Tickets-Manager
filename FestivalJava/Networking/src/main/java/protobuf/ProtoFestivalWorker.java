package protobuf;

import AppServices.IFestivalClient;
import AppServices.IFestivalServer;
import Domain.Artist;
import Domain.Show;
import Domain.User;
import Validation.Exceptions.ServiceException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by Sebi on 25-Apr-17.
 */
public class ProtoFestivalWorker implements Runnable, IFestivalClient {

    private IFestivalServer server;
    private Socket connection;

    private InputStream inputStream;
    private OutputStream outputStream;
    private volatile boolean connected;

    public ProtoFestivalWorker(IFestivalServer server, Socket connection){
        this.server = server;
        this.connection = connection;
        try{
            inputStream = connection.getInputStream();
            outputStream = connection.getOutputStream();
            connected = true;
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void showUpdated(Show show) throws ServiceException, RemoteException {

    }

    @Override
    public void run() {
        while (connected){
            try {
                System.out.println("waiting requests");
                FestivalProtobufs.FestivalRequest request =
                        FestivalProtobufs.FestivalRequest.parseDelimitedFrom(inputStream);
                System.out.println("Request received" + request);
                FestivalProtobufs.FestivalResponse response = handleRequest(request);
                if (response != null){
                    sendResponse(response);
                }
            } catch (IOException e){
                System.out.println("Client disconnected");
                connected = false;
            }
        }

        try {
            inputStream.close();
            outputStream.close();
            connection.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void sendResponse(FestivalProtobufs.FestivalResponse response) throws IOException{
        System.out.println("Sending response");
        response.writeDelimitedTo(outputStream);
        outputStream.flush();
    }

    private FestivalProtobufs.FestivalResponse handleRequest(FestivalProtobufs.FestivalRequest request){
        FestivalProtobufs.FestivalResponse response = null;
        if (request.getType() == FestivalProtobufs.FestivalRequest.Type.Login){
            System.out.println("Login request");
            User user = ProtoUtils.getUser(request);
            try{
                server.login(user, this);
                return ProtoUtils.createOKResponse();
            } catch (ServiceException e){
                return ProtoUtils.createErrorResponse(e.getMessage());
            }
        }

        if (request.getType() == FestivalProtobufs.FestivalRequest.Type.Logout){
            System.out.println("Logout request");
            User user = ProtoUtils.getUser(request);
            try{
                server.logout(user, this);
                connected = false;
                return ProtoUtils.createOKResponse();
            } catch (ServiceException e){
                return ProtoUtils.createErrorResponse(e.getMessage());
            }
        }

        if (request.getType() == FestivalProtobufs.FestivalRequest.Type.GetArtists){
            System.out.println("Get artists request");
            try {
                List<Artist> artistList = server.getArtists();
                return ProtoUtils.createGetArtistsResponse(artistList);
            } catch (ServiceException e){
                return ProtoUtils.createErrorResponse(e.getMessage());
            }
        }

        if (request.getType() == FestivalProtobufs.FestivalRequest.Type.GetShowsForArtist){
            System.out.println("Get shows for artist request");
            try {
                List<Show> showList = server.getShowsForArtist(request.getIdArtist());
                return ProtoUtils.createGetShowsForArtistResponse(showList);
            } catch (ServiceException e) {
                return ProtoUtils.createErrorResponse(e.getMessage());
            }
        }

        if (request.getType() == FestivalProtobufs.FestivalRequest.Type.GetShowsForDate){
            System.out.println("Get shows for date request");
            try {
                List<Show> showList = server.getShowsForDate(request.getDate());
                return ProtoUtils.createGetShowsForDateResponse(showList);
            } catch (ServiceException e) {
                return ProtoUtils.createErrorResponse(e.getMessage());
            }
        }


        return response;
    }
}
