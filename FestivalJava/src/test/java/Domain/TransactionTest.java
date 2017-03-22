package Domain;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Sebi on 09-Mar-17.
 */
public class TransactionTest {
    @Test
    public void getIdTransaction() throws Exception {
        Transaction t = new Transaction(1, "client", 2, 1);
        assertTrue(t.getIdTransaction() == 1);
    }

    @Test
    public void setIdTransaction() throws Exception {
        Transaction t = new Transaction(1, "client", 2, 1);
        t.setIdTransaction(2);
        assertTrue(t.getIdTransaction() == 2);
    }

    @Test
    public void getClientName() throws Exception {
        Transaction t = new Transaction(1, "client", 2, 1);
        assertTrue(t.getClientName().equals("client"));
    }

    @Test
    public void setClientName() throws Exception {
        Transaction t = new Transaction(1, "client", 2, 1);
        t.setClientName("client2");
        assertTrue(t.getClientName().equals("client2"));
    }

    @Test
    public void getNumberOfTickets() throws Exception {
        Transaction t = new Transaction(1, "client", 2, 1);
        assertTrue(t.getNumberOfTickets() == 2);
    }

    @Test
    public void setNumberOfTickets() throws Exception {
        Transaction t = new Transaction(1, "client", 2, 1);
        t.setNumberOfTickets(4);
        assertTrue(t.getNumberOfTickets() == 4);
    }

    @Test
    public void getIdShow() throws Exception {
        Transaction t = new Transaction(1, "client", 2, 1);
        assertTrue(t.getIdShow() == 1);
    }

    @Test
    public void setIdShow() throws Exception {
        Transaction t = new Transaction(1, "client", 2, 1);
        t.setIdShow(2);
        assertTrue(t.getIdShow() == 2);
    }

}