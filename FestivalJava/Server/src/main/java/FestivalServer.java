import Domain.Artist;
import Domain.Show;
import Domain.Transaction;
import Domain.User;
import AppServices.IFestivalClient;
import AppServices.IFestivalServer;
import ModelServices.Interfaces.IServiceArtist;
import ModelServices.Interfaces.IServiceShow;
import ModelServices.Interfaces.IServiceTransaction;
import ModelServices.Interfaces.IServiceUser;
import Validation.Exceptions.ServiceException;
import Validation.Exceptions.ValidatorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Sebi on 28-Mar-17.
 */
public class FestivalServer implements IFestivalServer {
    /*
    Model services
     */
    private IServiceArtist serviceArtist;
    private IServiceShow serviceShow;
    private IServiceTransaction serviceTransaction;
    private IServiceUser serviceUser;

    /*
    Logged in clients
     */
    private Map<String, IFestivalClient> loggedClients;

    public FestivalServer(IServiceArtist serviceArtist,
                                  IServiceShow serviceShow,
                                  IServiceTransaction serviceTransaction,
                                  IServiceUser serviceUser){
        this.serviceArtist = serviceArtist;
        this.serviceShow = serviceShow;
        this.serviceTransaction = serviceTransaction;
        this.serviceUser = serviceUser;

        loggedClients = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized List<Artist> getArtists() {
        return serviceArtist.getAll();
    }

    @Override
    public synchronized List<Show> getShowsForArtist(Integer idArtist) {
        return serviceShow.getShowsForArtist(idArtist);
    }

    @Override
    public synchronized List<Show> getShowsForDate(String date) {
        return serviceShow.getShowsForDate(date);
    }

    @Override
    public synchronized void buyTicketsForShow(Integer idShow, String clientName, Integer numberOfTickets) throws ServiceException, ValidatorException {
        Show show = serviceShow.getById(idShow);

        //check if there are enough tickets
        if (numberOfTickets > show.getTicketsAvailable()){
            throw new ServiceException("Not enough tickets available");
        }

        //update selected show
        show.setTicketsAvailable(show.getTicketsAvailable() - numberOfTickets);
        show.setTicketsSold(show.getTicketsSold() + numberOfTickets);
        serviceShow.update(idShow, show);

        //register transaction
        serviceTransaction.saveWithoutId(new Transaction(clientName, numberOfTickets, show));
    }

    @Override
    public synchronized void login(User user, IFestivalClient client) throws ServiceException {
        User userR = serviceUser.getByNamePass(user.getUsername(), user.getPassword());

        //invalid user
        if (userR == null){
            throw new ServiceException("Invalid username/password");
        }

        //already logged in
        if (loggedClients.get(user.getUsername()) != null){
            throw new ServiceException("User is already logged in");
        }

        //saves the user
        loggedClients.put(user.getUsername(), client);
    }

    @Override
    public void logout(User user, IFestivalClient client) throws ServiceException {
        IFestivalClient festivalClient = loggedClients.remove(user.getUsername());
        if (festivalClient == null){
            throw new ServiceException("User " + user.getUsername() + " is not logged in");
        }
    }
}