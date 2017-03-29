package dto;

import Domain.Artist;
import Domain.Show;
import Domain.Transaction;
import Domain.User;

/**
 * Created by Sebi on 29-Mar-17.
 */
public class DTOUtils {
    public static User getUserFromDTO(UserDTO userDTO){
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        return new User(username, password);
    }

    public static UserDTO getUserDTO(User user){
        String username = user.getUsername();
        String password = user.getPassword();
        return new UserDTO(username, password);
    }

    public static Artist getArtistFromDTO(ArtistDTO artistDTO){
        Integer id = artistDTO.getId();
        String name = artistDTO.getName();
        return new Artist(id, name);
    }

    public static ArtistDTO getArtistDTO(Artist artist){
        Integer id = artist.getIdArtist();
        String name = artist.getName();
        return new ArtistDTO(id, name);
    }

    public static Show getShowFromDTO(ShowDTO showDTO){
        Integer id = showDTO.getId();
        String location = showDTO.getLocation();
        String date = showDTO.getDate();
        Integer ticketsAvailable = showDTO.getTicketsAvailable();
        Integer ticketsSold = showDTO.getTicketsSold();
        Artist artist = showDTO.getArtist();
        return new Show(id, location, date,
                ticketsAvailable, ticketsSold, artist);
    }

    public static ShowDTO getShowDTO(Show show){
        Integer id = show.getIdShow();
        String location = show.getLocation();
        String date = show.getDate();
        Integer ticketsAvailable = show.getTicketsAvailable();
        Integer ticketsSold = show.getTicketsSold();
        Artist artist = show.getArtist();
        return new ShowDTO(id, location, date,
                ticketsAvailable, ticketsSold, artist);
    }

    public static Transaction getTransactionFromDTO(TransactionDTO transactionDTO){
        Integer id = transactionDTO.getId();
        String clientName = transactionDTO.getClientName();
        Integer numberOfTickets = transactionDTO.getNumberOfTickets();
        Show show = transactionDTO.getShow();
        return new Transaction(id, clientName, numberOfTickets, show);
    }

    public static TransactionDTO getTransactionDTO(Transaction transaction){
        Integer id = transaction.getIdTransaction();
        String clientName = transaction.getClientName();
        Integer numberOfTickets = transaction.getNumberOfTickets();
        Show show = transaction.getShow();
        return new TransactionDTO(id, clientName, numberOfTickets, show);
    }
}
