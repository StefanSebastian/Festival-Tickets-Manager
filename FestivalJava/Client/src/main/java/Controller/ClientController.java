package Controller;

import Domain.Artist;
import Domain.Show;
import Domain.User;
import AppServices.IFestivalClient;
import AppServices.IFestivalServer;
import Validation.Exceptions.ServiceException;
import Validation.Exceptions.ValidatorException;

import java.util.List;

/**
 * Created by Sebi on 28-Mar-17.
 */
public class ClientController implements IFestivalClient {
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

    @Override
    public void showsUpdated() {

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

    public void buyTicketsForShow(Integer idShow, String client, Integer numberOfTickets) throws ServiceException, ValidatorException {
        server.buyTicketsForShow(idShow, client, numberOfTickets);
    }

}
