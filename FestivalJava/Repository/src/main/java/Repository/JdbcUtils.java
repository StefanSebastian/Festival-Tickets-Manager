package Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Sebi on 09-Mar-17.
 */
public class JdbcUtils {
    //properties file
    private Properties properties;

    //Constructor
    public JdbcUtils(Properties properties){
        this.properties = properties;
    }

    //connection to database
    private Connection connection = null;

    //creates a new connection
    private Connection getNewConnection(){
        String driver = properties.getProperty("jdbc.driver");
        String url = properties.getProperty("jdbc.url");
        String user = properties.getProperty("jdbc.user");
        String pass = properties.getProperty("jdbc.pass");

        Connection con = null;
        try{
            Class.forName(driver);
            if (user != null && pass != null){
                con = DriverManager.getConnection(url, user, pass);
            } else {
                con = DriverManager.getConnection(url);
            }
        } catch (ClassNotFoundException e){
            System.out.println("Error loading driver" + e);
        } catch (SQLException e){
            System.out.println("Error connecting" + e);
        }
        return con;
    }

    //returns the connection instance
    public Connection getConnection(){
        try{
            if (connection == null || connection.isClosed()){
                connection = getNewConnection();
            }
        } catch (SQLException e){
            System.out.println("Database error" + e);
        }
        return connection;
    }
}
