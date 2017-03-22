package Domain;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Sebi on 09-Mar-17.
 */
public class ShowTest {
    @Test
    public void getIdShow() throws Exception {
        Show show = new Show(1, "location", "2016-03-03 20:00", 100, 0, 1);
        assertTrue(show.getIdShow() == 1);
    }

    @Test
    public void setIdShow() throws Exception {
        Show show = new Show(1, "location", "2016-03-03 20:00", 100, 0, 1);
        show.setIdShow(2);
        assertTrue(show.getIdShow() == 2);
    }

    @Test
    public void getLocation() throws Exception {
        Show show = new Show(1, "location", "2016-03-03 20:00", 100, 0, 1);
        assertEquals(show.getLocation(), "location");
    }

    @Test
    public void setLocation() throws Exception {
        Show show = new Show(1, "location", "2016-03-03 20:00", 100, 0, 1);
        show.setLocation("loc");
        assertEquals(show.getLocation(), "loc");
    }

    @Test
    public void getDate() throws Exception {
        Show show = new Show(1, "location", "2016-03-03 20:00", 100, 0, 1);
        assertEquals(show.getDate(), "2016-03-03 20:00");
    }

    @Test
    public void setDate() throws Exception {
        Show show = new Show(1, "location", "2016-03-03 20:00", 100, 0, 1);
        show.setDate("2015-02-02 18:00");
        assertEquals(show.getDate(), "2015-02-02 18:00");
    }

    @Test
    public void getTicketsAvailable() throws Exception {
        Show show = new Show(1, "location", "2016-03-03 20:00", 100, 0, 1);
        assertTrue(show.getTicketsAvailable() == 100);
    }

    @Test
    public void setTicketsAvailable() throws Exception {
        Show show = new Show(1, "location", "2016-03-03 20:00", 100, 0, 1);
        show.setTicketsAvailable(120);
        assertTrue(show.getTicketsAvailable() == 120);
    }

    @Test
    public void getTicketsSold() throws Exception {
        Show show = new Show(1, "location", "2016-03-03 20:00", 100, 0, 1);
        assertTrue(show.getTicketsSold() == 0);
    }

    @Test
    public void setTicketsSold() throws Exception {
        Show show = new Show(1, "location", "2016-03-03 20:00", 100, 0, 1);
        show.setTicketsSold(20);
        assertTrue(show.getTicketsSold() == 20);
    }

    @Test
    public void getIdArtist() throws Exception {
        Show show = new Show(1, "location", "2016-03-03 20:00", 100, 0, 1);
        assertTrue(show.getIdArtist() == 1);
    }

    @Test
    public void setIdArtist() throws Exception {
        Show show = new Show(1, "location", "2016-03-03 20:00", 100, 0, 1);
        show.setIdArtist(22);
        assertTrue(show.getIdArtist() == 22);
    }

}