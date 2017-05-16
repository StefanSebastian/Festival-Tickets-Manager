package Repository;

import festival.Domain.User;
import festival.JDBC.RepositoryUserDB;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertTrue;

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
            repo.delete(a.getUsername());
        }
        repo = null;
    }

    @Test
    public void save() throws Exception {
        repo.save(new User("nume", "pass"));
        assertTrue(repo.getAll().size() == 1);
    }

    @Test
    public void delete() throws Exception {
        repo.save(new User("nume", "pass"));
        assertTrue(repo.getAll().size() == 1);
        repo.delete("nume");
        assertTrue(repo.getAll().size() == 0);
    }

    @Test
    public void update() throws Exception {
        repo.save(new User("nume", "pass"));
        repo.update("nume", new User("nume2", "pass2"));
        User u = repo.getById("nume2");
        assertTrue(u.getUsername().equals("nume2"));
    }

    @Test
    public void getById() throws Exception {
        repo.save(new User("nume", "pass"));
        User u = repo.getById("nume");
        assertTrue(u.getUsername().equals("nume"));
    }

    @Test
    public void getAll() throws Exception {
        repo.save(new User("nume", "pass"));
        assertTrue(repo.getAll().size() == 1);
        repo.save(new User("nume2", "pass2"));
        assertTrue(repo.getAll().size() == 2);
    }

}