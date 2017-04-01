package Controller;

import Domain.Artist;
import Domain.Show;
import Domain.User;
import AppServices.IFestivalClient;
import AppServices.IFestivalServer;
import ObserverPattern.AbstractObservable;
import Validation.Exceptions.ServiceException;
import Validation.Exceptions.ValidatorException;

import java.util.List;

/**
 * Created by Sebi on 28-Mar-17.
 */
public class ClientController extends AbstractObservable<Show> implements IFestivalClient {
    private IFestivalServer server;
    private User user;

    public ClientController(IFestivalServer server){
        this.server = server;
    }

    public void login(String username, String password) throws ServiceException {
        User userL = new User(username, password);
        server.login(userL, this);
        user = userL;
    }

    public void logout() throws ServiceException{
        server.logout(user, this);
        user = null;
    }

    //notification from server recieved
    @Override
    public void showUpdated(Show show) throws ServiceException {
        System.out.println("Update received!!!!");
        notifyPushObservers(show);
    }

    /*
    Notification from one of the windows that data has changed
    updates the subscribed windows
     */
    public void localDataChanged(){
        notifyObservers();
    }

    public List<Artist> getArtists() throws ServiceException{
        return server.getArtists();
    }

    public List<Show> getShowsForArtist(Integer idArtist) throws ServiceException {
        return server.getShowsForArtist(idArtist);
    }

    public List<Show> getShowsForDate(String date) throws ServiceException{
        return server.getShowsForDate(date);
    }

    public void buyTicketsForShow(Integer idShow, String client, Integer numberOfTickets, String username) throws ServiceException, ValidatorException {
        server.buyTicketsForShow(idShow, client, numberOfTickets, username);
    }

    /*
    Returns the currently logged in user
     */
    public User getCurrentUser(){
        return this.user;
    }

}
