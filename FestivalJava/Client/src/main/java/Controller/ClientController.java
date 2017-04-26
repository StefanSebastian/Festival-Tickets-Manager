package Controller;

import Domain.Artist;
import Domain.Show;
import Domain.User;
import AppServices.IFestivalClient;
import AppServices.IFestivalServer;
import ObserverPattern.AbstractObservable;
import ObserverPattern.Observable;
import ObserverPattern.Observer;
import Validation.Exceptions.ServiceException;
import Validation.Exceptions.ValidatorException;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Sebi on 28-Mar-17.
 * Client controller, communicates with server, serves the UI
 */
public class ClientController implements IFestivalClient, Serializable, Observable<Show> {
    //server
    private IFestivalServer server;

    //the currently logged in user
    private User user;

    /*
    Constructor - gets reference to server
     */
    public ClientController(IFestivalServer server) throws RemoteException{
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
        notifyObservers();
    }

    /*
    Notification from one of the windows that data has changed
    updates the subscribed windows
     */
    public void localDataChanged(){
        notifyObservers();
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


    List<Observer<Show>> observers = new LinkedList<>();

    @Override
    public void addObserver(Observer<Show> o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer<Show> o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer<Show> o : observers){
            o.update();
        }
    }
}
