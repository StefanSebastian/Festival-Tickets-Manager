package Controller;

import festival.AppServices.IFestivalClient;
import festival.AppServices.IFestivalServer;
import festival.Domain.Artist;
import festival.Domain.Show;
import festival.Domain.User;
import ObserverPattern.AbstractObservable;
import festival.Validation.Exceptions.ServiceException;
import festival.Validation.Exceptions.ValidatorException;

import java.util.List;

/**
 * Created by Sebi on 28-Mar-17.
 * Client controller, communicates with server, serves the UI
 */
public class ClientController extends AbstractObservable<Show> implements IFestivalClient {
    //server
    private IFestivalServer server;

    //the currently logged in user
    private User user;

    /*
    Constructor - gets reference to server
     */
    public ClientController(IFestivalServer server){
        this.server = server;
    }

    /*
    Login request to server
    if the request is successful then we memorize the currently logged in user
     */
    public void login(String username, String password) throws ServiceException {
        User userL = new User(username, password);
        server.login(userL, this);
        user = userL;
    }

    /*
    Logout request to server
    if the request is successful then we set the currently logged in user to null
     */
    public void logout() throws ServiceException{
        server.logout(user, this);
        user = null;
    }

    /*
    Server notification
    a show has been updated
    Notifies all push observers - windows that display shows
     */
    @Override
    public void showUpdated(Show show) throws ServiceException {
        notifyPushObservers(show);
    }

    /*
    get artists from server
     */
    public List<Artist> getArtists() throws ServiceException{
        return server.getArtists();
    }

    /*
    get all the shows for an artist from server
     */
    public List<Show> getShowsForArtist(Integer idArtist) throws ServiceException {
        return server.getShowsForArtist(idArtist);
    }

    /*
    get all the shows for a date from the server
     */
    public List<Show> getShowsForDate(String date) throws ServiceException{
        return server.getShowsForDate(date);
    }

    /*
    Sends a transaction request to server
    idShow - the show for which tickets are bought
    client - name of the client
    username - name of the user that requests the transaction
     */
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
