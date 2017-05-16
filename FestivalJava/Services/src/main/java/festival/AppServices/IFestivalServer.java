package festival.AppServices;

import festival.Domain.Artist;
import festival.Domain.Show;
import festival.Domain.User;
import festival.Validation.Exceptions.ServiceException;
import festival.Validation.Exceptions.ValidatorException;

import java.util.List;

/**
 * Created by Sebi on 28-Mar-17.
 */
public interface IFestivalServer {
    /*
    Gets all artists
     */
    List<Artist> getArtists() throws ServiceException;

    /*
    Gets all shows for a given artist
     */
    List<Show> getShowsForArtist(Integer idArtist) throws ServiceException;

    /*
    Gets all shows for a given date
     */
    List<Show> getShowsForDate(String date) throws ServiceException;

    /*
    Attempts to buy tickets for a show
    idShow - for which we buy tickets
    clientName - name of the buyer
    numberOfTickets - number of tickets bought
    username - user that buys the tickets
     */
    void buyTicketsForShow(Integer idShow, String clientName, Integer numberOfTickets, String username) throws ServiceException, ValidatorException;

    /*
    Login functionality
     */
    void login(User user, IFestivalClient client) throws ServiceException;

    /*
    Logout functionality
     */
    void logout(User user, IFestivalClient client) throws ServiceException;

}
