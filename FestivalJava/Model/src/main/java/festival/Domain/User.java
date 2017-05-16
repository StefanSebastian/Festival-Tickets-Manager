package festival.Domain;

import java.io.Serializable;

/**
 * Created by Sebi on 18-Mar-17.
 */
public class User implements Serializable{
    //username
    private String username;

    //password
    private String password;

    public User(){}

    //Constructor
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    //getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
