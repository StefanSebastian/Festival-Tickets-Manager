package Repository;

import Domain.Artist;
import Domain.Show;
import Domain.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.*;

/**
 * Created by Sebi on 09-Mar-17.
 */
public class RepositoryTransactionDBTest {
    RepositoryArtistDB repoArtist;
    RepositoryShowDB repoShow;
    RepositoryTransactionDB repoTrans;

    @Before
    public void setUp() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("jdbc.driver", "com.mysql.jdbc.Driver");
        properties.setProperty("jdbc.url", "jdbc:mysql://localhost/test_festival");
        properties.setProperty("jdbc.user", "root");
        properties.setProperty("jdbc.pass", "pass");
        repoArtist = new RepositoryArtistDB(properties);
        repoShow = new RepositoryShowDB(properties);
        repoTrans = new RepositoryTransactionDB(properties);
    }

    @After
    public void tearDown() throws Exception {
        for (Transaction t : repoTrans.getAll()){
            repoTrans.delete(t.getIdTransaction());
        }
        repoTrans = null;

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
        repoArtist.save(new Artist(1, "name"));
        repoShow.save(new Show(1, "loc", "2016-03-03 20:00", 12, 0, 1));
        repoTrans.save(new Transaction(1, "nume", 2, 1));

        assertTrue(repoTrans.getById(1).getClientName().equals("nume"));

    }

    @Test
    public void delete() throws Exception {
        repoArtist.save(new Artist(1, "name"));
        repoShow.save(new Show(1, "loc", "2016-03-03 20:00", 12, 0, 1));

        repoTrans.save(new Transaction(1, "nume", 2, 1));

        assertTrue(repoTrans.getAll().size() == 1);

        repoTrans.delete(1);

        assertTrue(repoTrans.getAll().size() == 0);

    }

    @Test
    public void update() throws Exception {
        repoArtist.save(new Artist(1, "name"));
        repoShow.save(new Show(1, "loc", "2016-03-03 20:00", 12, 0, 1));
        repoTrans.save(new Transaction(1, "nume", 2, 1));

        repoTrans.update(1, new Transaction(1, "altnume", 2, 1));
        assertTrue(repoTrans.getById(1).getClientName().equals("altnume"));
    }

    @Test
    public void getById() throws Exception {
        repoArtist.save(new Artist(1, "name"));
        repoShow.save(new Show(1, "loc", "2016-03-03 20:00", 12, 0, 1));
        repoTrans.save(new Transaction(1, "nume", 2, 1));

        assertTrue(repoTrans.getById(1).getClientName().equals("nume"));
        assertTrue(repoTrans.getById(2) == null);
    }

    @Test
    public void getAll() throws Exception {
        repoArtist.save(new Artist(1, "name"));
        repoShow.save(new Show(1, "loc", "2016-03-03 20:00", 12, 0, 1));

        assertTrue(repoTrans.getAll().size() == 0);

        repoTrans.save(new Transaction(1, "nume", 2, 1));

        assertTrue(repoTrans.getAll().size() == 1);
    }

}