package Domain;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Sebi on 09-Mar-17.
 */
public class ShowTest {
    @Test
    public void getIdShow() throws Exception {
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", "2016-03-03 20:00", 100, 0, artist);
        assertTrue(show.getIdShow() == 1);
    }

    @Test
    public void setIdShow() throws Exception {
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", "2016-03-03 20:00", 100, 0, artist);
        show.setIdShow(2);
        assertTrue(show.getIdShow() == 2);
    }

    @Test
    public void getLocation() throws Exception {
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", "2016-03-03 20:00", 100, 0, artist);
        assertEquals(show.getLocation(), "location");
    }

    @Test
    public void setLocation() throws Exception {
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", "2016-03-03 20:00", 100, 0, artist);
        show.setLocation("loc");
        assertEquals(show.getLocation(), "loc");
    }

    @Test
    public void getDate() throws Exception {
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", "2016-03-03 20:00", 100, 0, artist);
        assertEquals(show.getDate(), "2016-03-03 20:00");
    }

    @Test
    public void setDate() throws Exception {
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", "2016-03-03 20:00", 100, 0, artist);
        show.setDate("2015-02-02 18:00");
        assertEquals(show.getDate(), "2015-02-02 18:00");
    }

    @Test
    public void getTicketsAvailable() throws Exception {
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", "2016-03-03 20:00", 100, 0, artist);
        assertTrue(show.getTicketsAvailable() == 100);
    }

    @Test
    public void setTicketsAvailable() throws Exception {
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", "2016-03-03 20:00", 100, 0, artist);
        show.setTicketsAvailable(120);
        assertTrue(show.getTicketsAvailable() == 120);
    }

    @Test
    public void getTicketsSold() throws Exception {
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", "2016-03-03 20:00", 100, 0, artist);
        assertTrue(show.getTicketsSold() == 0);
    }

    @Test
    public void setTicketsSold() throws Exception {
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", "2016-03-03 20:00", 100, 0, artist);
        show.setTicketsSold(20);
        assertTrue(show.getTicketsSold() == 20);
    }

    @Test
    public void getIdArtist() throws Exception {
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", "2016-03-03 20:00", 100, 0, artist);
        assertTrue(show.getArtist().getIdArtist() == 1);
    }

    @Test
    public void setIdArtist() throws Exception {
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", "2016-03-03 20:00", 100, 0, artist);
        show.getArtist().setIdArtist(22);
        assertTrue(show.getArtist().getIdArtist() == 22);
    }

}