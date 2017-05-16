package festival.Domain;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sebi on 09-Mar-17.
 */
public class ShowTest {
    @Test
    public void getIdShow() throws Exception {
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", new Date(), 100, 0, artist);
        assertTrue(show.getIdShow() == 1);
    }

    @Test
    public void setIdShow() throws Exception {
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", new Date(), 100, 0, artist);
        show.setIdShow(2);
        assertTrue(show.getIdShow() == 2);
    }

    @Test
    public void getLocation() throws Exception {
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", new Date(), 100, 0, artist);
        assertEquals(show.getLocation(), "location");
    }

    @Test
    public void setLocation() throws Exception {
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", new Date(), 100, 0, artist);
        show.setLocation("loc");
        assertEquals(show.getLocation(), "loc");
    }

    @Test
    public void getTicketsAvailable() throws Exception {
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", new Date(), 100, 0, artist);
        assertTrue(show.getTicketsAvailable() == 100);
    }

    @Test
    public void setTicketsAvailable() throws Exception {
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", new Date(), 100, 0, artist);
        show.setTicketsAvailable(120);
        assertTrue(show.getTicketsAvailable() == 120);
    }

    @Test
    public void getTicketsSold() throws Exception {
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", new Date(), 100, 0, artist);
        assertTrue(show.getTicketsSold() == 0);
    }

    @Test
    public void setTicketsSold() throws Exception {
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", new Date(), 100, 0, artist);
        show.setTicketsSold(20);
        assertTrue(show.getTicketsSold() == 20);
    }

    @Test
    public void getIdArtist() throws Exception {
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", new Date(), 100, 0, artist);
        assertTrue(show.getArtist().getIdArtist() == 1);
    }

    @Test
    public void setIdArtist() throws Exception {
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", new Date(), 100, 0, artist);
        show.getArtist().setIdArtist(22);
        assertTrue(show.getArtist().getIdArtist() == 22);
    }

}