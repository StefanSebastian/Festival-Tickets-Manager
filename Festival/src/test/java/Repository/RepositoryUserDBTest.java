package Repository;

import Domain.Artist;
import Domain.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.*;

/**
 * Created by Sebi on 18-Mar-17.
 */
public class RepositoryUserDBTest {
    RepositoryUserDB repo;

    @Before
    public void setUp() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("jdbc.driver", "com.mysql.jdbc.Driver");
        properties.setProperty("jdbc.url", "jdbc:mysql://localhost/test_festival");
        properties.setProperty("jdbc.user", "root");
        properties.setProperty("jdbc.pass", "pass");
        repo = new RepositoryUserDB(properties);
    }

    @After
    public void tearDown() throws Exception {
        for (User a : repo.getAll()){
            repo.delete(a.getIdUser());
        }
        repo = null;
    }

    @Test
    public void save() throws Exception {
        repo.save(new User(1, "nume", "pass"));
        assertTrue(repo.getAll().size() == 1);
    }

    @Test
    public void delete() throws Exception {
        repo.save(new User(1, "nume", "pass"));
        assertTrue(repo.getAll().size() == 1);
        repo.delete(1);
        assertTrue(repo.getAll().size() == 0);
    }

    @Test
    public void update() throws Exception {
        repo.save(new User(1, "nume", "pass"));
        repo.update(1, new User(2, "nume2", "pass2"));
        User u = repo.getById(2);
        assertTrue(u.getIdUser() == 2);
        assertTrue(u.getUsername().equals("nume2"));
    }

    @Test
    public void getById() throws Exception {
        repo.save(new User(1, "nume", "pass"));
        User u = repo.getById(1);
        assertTrue(u.getIdUser() == 1);
        assertTrue(u.getUsername().equals("nume"));
    }

    @Test
    public void getAll() throws Exception {
        repo.save(new User(1, "nume", "pass"));
        assertTrue(repo.getAll().size() == 1);
        repo.save(new User(2, "nume2", "pass2"));
        assertTrue(repo.getAll().size() == 2);
    }

}