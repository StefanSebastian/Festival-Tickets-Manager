package festival.Domain;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertTrue;

/**
 * Created by Sebi on 09-Mar-17.
 */
public class TransactionTest {
    @Test
    public void getIdTransaction() throws Exception {
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", new Date(), 100, 0, artist);
        Transaction t = new Transaction(1, "client", 2, show);
        assertTrue(t.getIdTransaction() == 1);
    }

    @Test
    public void setIdTransaction() throws Exception {
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", new Date(), 100, 0, artist);
        Transaction t = new Transaction(1, "client", 2, show);
        t.setIdTransaction(2);
        assertTrue(t.getIdTransaction() == 2);
    }

    @Test
    public void getClientName() throws Exception {
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", new Date(), 100, 0, artist);
        Transaction t = new Transaction(1, "client", 2, show);
        assertTrue(t.getClientName().equals("client"));
    }

    @Test
    public void setClientName() throws Exception {
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", new Date(), 100, 0, artist);
        Transaction t = new Transaction(1, "client", 2, show);
        t.setClientName("client2");
        assertTrue(t.getClientName().equals("client2"));
    }

    @Test
    public void getNumberOfTickets() throws Exception {
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", new Date(), 100, 0, artist);
        Transaction t = new Transaction(1, "client", 2, show);
        assertTrue(t.getNumberOfTickets() == 2);
    }

    @Test
    public void setNumberOfTickets() throws Exception {
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", new Date(), 100, 0, artist);
        Transaction t = new Transaction(1, "client", 2, show);
        t.setNumberOfTickets(4);
        assertTrue(t.getNumberOfTickets() == 4);
    }

}