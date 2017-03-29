package Domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sebi on 09-Mar-17.
 */
public class ArtistTest {
    @Test
    public void getIdArtist() throws Exception {
        Artist artist = new Artist(1, "aName");
        assertTrue(artist.getIdArtist() == 1);
    }

    @Test
    public void setIdArtist() throws Exception {
        Artist artist = new Artist(1, "Name");
        artist.setIdArtist(2);
        assertTrue(artist.getIdArtist() == 2);
    }

    @Test
    public void getName() throws Exception {
        Artist artist = new Artist(1, "Name");
        assertEquals(artist.getName(), "Name");
    }

    @Test
    public void setName() throws Exception {
        Artist artist = new Artist(1, "Name");
        artist.setName("Name2");
        assertEquals(artist.getName(), "Name2");
    }

}