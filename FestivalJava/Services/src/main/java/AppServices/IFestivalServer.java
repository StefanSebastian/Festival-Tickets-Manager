package AppServices;

import Domain.Artist;
import Domain.Show;
import Domain.User;
import Validation.Exceptions.ServiceException;
import Validation.Exceptions.ValidatorException;

import java.util.List;

/**
 * Created by Sebi on 28-Mar-17.
 */
public interface IFestivalServer {
    /*
    Gets all artists
     */
    List<Artist> getArtists();

    /*
    Gets all shows for a given artist
     */
    List<Show> getShowsForArtist(Integer idArtist);

    /*
    Gets all shows for a given date
     */
    List<Show> getShowsForDate(String date);

    /*
    Attempts to buy tickets for a show
     */
    void buyTicketsForShow(Integer idShow, String clientName, Integer numberOfTickets) throws ServiceException, ValidatorException;

    /*
    Login functionality
     */
    void login(User user, IFestivalClient client) throws ServiceException;

    /*
    Logout functionality
     */
    void logout(User user, IFestivalClient client) throws ServiceException;

}
