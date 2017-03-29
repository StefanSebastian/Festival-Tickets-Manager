package Domain;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Sebi on 18-Mar-17.
 */
public class UserTest {
    @Test
    public void getIdUser() throws Exception {
        User u = new User("nume", "pass");
        assertTrue(u.getUsername().equals("nume"));
    }

    @Test
    public void setIdUser() throws Exception {
        User u = new User("nume", "pass");
        assertTrue(u.getUsername().equals("nume"));
        u.setUsername("nume2");
        assertTrue(u.getUsername().equals("nume2"));
    }

    @Test
    public void getPassword() throws Exception {
        User u = new User("nume", "pass");
        assertTrue(u.getPassword().equals("pass"));
    }

    @Test
    public void setPassword() throws Exception {
        User u = new User("nume", "pass");
        assertTrue(u.getPassword().equals("pass"));
        u.setPassword("pass2");
        assertTrue(u.getPassword().equals("pass2"));
    }

}