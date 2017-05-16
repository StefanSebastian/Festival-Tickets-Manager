package Repository;

import festival.Domain.Artist;
import festival.JDBC.RepositoryArtistDB;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertTrue;

/**
 * Created by Sebi on 09-Mar-17.
 */
public class RepositoryArtistDBTest {
    RepositoryArtistDB repo;

    @Before
    public void setUp() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("jdbc.driver", "com.mysql.jdbc.Driver");
        properties.setProperty("jdbc.url", "jdbc:mysql://localhost/test_festival");
        properties.setProperty("jdbc.user", "root");
        properties.setProperty("jdbc.pass", "pass");
        repo = new RepositoryArtistDB(properties);
    }

    @After
    public void tearDown() throws Exception {
        for (Artist a : repo.getAll()){
            repo.delete(a.getIdArtist());
        }
        repo = null;
    }

    @Test
    public void save() throws Exception {
        repo.save(new Artist(1, "name"));
        Artist a = repo.getById(1);
        assertTrue(a.getName().equals("name"));
        assertTrue(a.getIdArtist() == 1);
    }

    @Test
    public void delete() throws Exception {
        repo.save(new Artist(1, "name"));
        Integer size = repo.getAll().size();
        assertTrue(size == 1);
        repo.delete(1);
        size = repo.getAll().size();
        assertTrue(size == 0);
    }

    @Test
    public void update() throws Exception {
        repo.save(new Artist(1, "name"));
        repo.update(1, new Artist(2, "altNume"));
        Artist a = repo.getById(2);
        assertTrue(a.getName().equals("altNume"));
    }

    @Test
    public void getById() throws Exception {
        repo.save(new Artist(1, "name"));
        Artist a = repo.getById(1);
        assertTrue(a.getIdArtist() == 1);
        a = repo.getById(2);
        assertTrue(a == null);

    }

    @Test
    public void getAll() throws Exception {
        repo.save(new Artist(1, "name"));
        repo.save(new Artist(2, "name"));

        assertTrue(repo.getAll().size() == 2);
    }

}