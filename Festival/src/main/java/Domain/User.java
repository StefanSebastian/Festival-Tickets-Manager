package Domain;

/**
 * Created by Sebi on 18-Mar-17.
 */
public class User {
    //id
    private Integer idUser;

    //username
    private String username;

    //password
    private String password;

    //Constructor
    public User(Integer idUser, String username, String password){
        this.idUser = idUser;
        this.username = username;
        this.password = password;
    }

    //getters and setters
    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

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
