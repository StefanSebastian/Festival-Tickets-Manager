package Controller;

import Domain.Artist;
import Domain.Show;
import Domain.Transaction;
import Domain.User;
import Services.ServiceArtist;
import Services.ServiceShow;
import Services.ServiceTransaction;
import Services.ServiceUser;
import Utils.Observer;
import Validation.Exceptions.ControllerException;
import Validation.Exceptions.ValidatorException;

import java.util.List;

/**
 * Created by Sebi on 26-Mar-17.
 */
public class AppController {
    //services
    private ServiceUser serviceUser;
    private ServiceArtist serviceArtist;
    private ServiceShow serviceShow;
    private ServiceTransaction serviceTransaction;

    public AppController(
            ServiceUser serviceUser,
            ServiceArtist serviceArtist,
            ServiceShow serviceShow,
            ServiceTransaction serviceTransaction
    )
    {
        this.serviceUser = serviceUser;
        this.serviceArtist = serviceArtist;
        this.serviceShow = serviceShow;
        this.serviceTransaction = serviceTransaction;
    }

    /*
        * Tries to login a user
        * returns null if user is not valid
        */
    public User tryLogin(String username, String password)
    {
        return serviceUser.getByNamePass(username, password);
    }

    /*
     * Gets a list of all the artists
     */
    public List<Artist> getAllArtists()
    {
        return serviceArtist.getAll();
    }

    /*
     * Gets all shows for a given artist
     */
    public List<Show> getShowsForArtist(int idArtist)
    {
        return serviceShow.getShowsForArtist(idArtist);
    }

    /*
     * Gets all shows for a given date
     */
    public List<Show> getShowsForDate(String date)
    {
        return serviceShow.getShowsForDate(date);
    }

    /*
     * Buy tickets for show
     * If not enough tickets -> throw exception
     * Else we update show tickets number
     * And we save the transaction
     */
    public void buyTicketsForShow(int idShow, String clientName, int nrTickets) throws ControllerException, ValidatorException {
        Show show = serviceShow.getById(idShow);

        if (nrTickets < 0)
        {
            throw new ControllerException("You must buy at least one ticket");
        }

        if (show.getTicketsAvailable() < nrTickets)
        {
            throw new ControllerException("Not enough tickets available");
        }

        show.setTicketsAvailable(show.getTicketsAvailable() - nrTickets);
        show.setTicketsSold(show.getTicketsSold() + nrTickets);

        serviceShow.update(idShow, show);

        serviceTransaction.saveWithoutId(new Transaction(clientName, nrTickets, show.getIdShow()));
    }

    /*
    Gets an artist by id
     */
    public Artist getArtistById(Integer idArtist){
        return serviceArtist.getById(idArtist);
    }

    /*
    Sets an observer for transactions
     */
    public void setTransactionObserver(Observer observer){
        serviceTransaction.addObserver(observer);
    }
}
