package Repository;

import Domain.Artist;
import Domain.Show;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.*;

/**
 * Created by Sebi on 09-Mar-17.
 */
public class RepositoryShowDBTest {
    RepositoryArtistDB repoArtist;
    RepositoryShowDB repoShow;

    @Before
    public void setUp() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("jdbc.driver", "com.mysql.jdbc.Driver");
        properties.setProperty("jdbc.url", "jdbc:mysql://localhost/test_festival");
        properties.setProperty("jdbc.user", "root");
        properties.setProperty("jdbc.pass", "pass");
        repoArtist = new RepositoryArtistDB(properties);
        repoShow = new RepositoryShowDB(properties);
    }

    @After
    public void tearDown() throws Exception {
        for (Show s : repoShow.getAll()){
            repoShow.delete(s.getIdShow());
        }
        repoShow = null;

        for (Artist a : repoArtist.getAll()){
            repoArtist.delete(a.getIdArtist());
        }
        repoArtist = null;
    }

    @Test
    public void save() throws Exception {
        Artist artist = new Artist(1, "name");
        repoArtist.save(artist);
        repoShow.save(new Show(1, "loc", "2016-03-03 20:00", 12, 0, artist));

        assertTrue(repoShow.getById(1).getArtist().getIdArtist() == 1);
        assertTrue(repoShow.getById(1).getLocation().equals("loc"));

    }

    @Test
    public void delete() throws Exception {
        Artist artist = new Artist(1, "name");
        repoArtist.save(artist);

        repoShow.save(new Show(1, "loc", "2016-03-03 20:00", 12, 0, artist));

        assertTrue(repoShow.getAll().size() == 1);
        repoShow.delete(1);
        assertTrue(repoShow.getAll().size() == 0);
    }

    @Test
    public void update() throws Exception {
        Artist artist = new Artist(1, "name");
        repoArtist.save(artist);

        repoShow.save(new Show(1, "loc", "2016-03-03 20:00", 12, 0, artist));

        repoShow.update(1, new Show(1, "locul", "2016-03-03 21:00", 22, 0, artist));
        assertTrue(repoShow.getById(1).getLocation().equals("locul"));

    }

    @Test
    public void getById() throws Exception {
        Artist artist = new Artist(1, "name");
        repoArtist.save(artist);

        repoShow.save(new Show(1, "loc", "2016-03-03 20:00", 12, 0, artist));

        assertTrue(repoShow.getById(1).getLocation().equals("loc"));
        assertTrue(repoShow.getById(2) == null);

    }

    @Test
    public void getAll() throws Exception {
        Artist artist = new Artist(1, "name");
        repoArtist.save(artist);


        assertTrue(repoShow.getAll().size() == 0);
        repoShow.save(new Show(1, "loc", "2016-03-03 20:00", 12, 0, artist));
        repoShow.save(new Show(2, "loc", "2016-03-03 21:00", 12, 0, artist));
        assertTrue(repoShow.getAll().size() == 2);

    }

}