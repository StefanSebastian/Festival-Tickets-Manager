import festival.Domain.Artist;
import festival.Domain.Show;
import festival.Domain.Transaction;
import festival.Domain.User;
import festival.AppServices.IFestivalClient;
import festival.AppServices.IFestivalServer;
import festival.ModelServices.Interfaces.IServiceArtist;
import festival.ModelServices.Interfaces.IServiceShow;
import festival.ModelServices.Interfaces.IServiceTransaction;
import festival.ModelServices.Interfaces.IServiceUser;
import festival.Validation.Exceptions.ServiceException;
import festival.Validation.Exceptions.ValidatorException;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    //used for notifying users in different threads
    private final int defaultThreadsNumber = 5;

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
    public synchronized List<Artist> getArtists() throws ServiceException {
        return serviceArtist.getAll();
    }

    @Override
    public synchronized List<Show> getShowsForArtist(Integer idArtist) throws ServiceException {
        return serviceShow.getShowsForArtist(idArtist);
    }

    @Override
    public synchronized List<Show> getShowsForDate(String date) throws ServiceException {
        return serviceShow.getShowsForDate(date);
    }

    @Override
    public synchronized void buyTicketsForShow(Integer idShow, String clientName, Integer numberOfTickets, String username) throws ServiceException, ValidatorException {
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

        //notify everyone
        notifyUsersTransaction(username, show);
    }


    /*
    Notifies all logged in users that a transaction has been made for a show
    does not notify the buyer
     */
    private void notifyUsersTransaction(String buyer, Show show) throws ServiceException{
        //get all logged users
        Iterable<String> loggedUsers = loggedClients.keySet();

        //create executor service
        ExecutorService executorService = Executors.newFixedThreadPool(defaultThreadsNumber);

        //notifies all clients
        for (String username : loggedUsers){
            executorService.execute(() -> {
                try {
                    if (!username.equals(buyer)){
                        IFestivalClient client = loggedClients.get(username);
                        client.showUpdated(show);
                    }
                } catch (ServiceException | RemoteException e) {
                    System.err.println("Error notifying user");
                }
            });
        }
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
