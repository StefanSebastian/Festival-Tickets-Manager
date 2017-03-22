package Repository;

import Domain.User;
import Repository.Interfaces.IDatabaseRepository;
import Repository.Interfaces.IRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Sebi on 18-Mar-17.
 */
public class RepositoryUserDB implements IDatabaseRepository<User, String> {
    //used to get connection
    private JdbcUtils jdbcUtils;

    //constructor
    public RepositoryUserDB(Properties properties){
        jdbcUtils = new JdbcUtils(properties);
    }

    @Override
    public void save(User user) {
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement statement = con.prepareStatement("INSERT INTO users values(?, ?)")){
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Db error" + e);
        }
    }

    @Override
    public void delete(String id) {
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement statement = con.prepareStatement("DELETE FROM users WHERE username = ?")){
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Db error" + e);
        }

    }

    @Override
    public void update(String id, User user) {
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement statement =
                    con.prepareStatement("UPDATE users SET " +
                            "username = ?, password = ? WHERE username = ?")){
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, id);
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Db error" + e);
        }
    }

    @Override
    public User getById(String id) {
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement statement = con.prepareStatement("SELECT * FROM users WHERE username = ?")){
            statement.setString(1, id);
            try(ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    return new User(username, password);
                }
            }
        } catch (SQLException e){
            System.out.println("Db error" + e);
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        Connection con = jdbcUtils.getConnection();
        List<User> users = new ArrayList<>();
        try(PreparedStatement statement = con.prepareStatement("select * from users")) {
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    users.add(new User(username, password));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return users;
    }


    @Override
    public List<User> filter(List<String> filters, List<String> arguments) {
        Connection con = jdbcUtils.getConnection();
        List<User> users = new ArrayList<>();

        String command = "select * from users where ";
        for (String filter : filters){
            command += filter + " AND ";
        }
        command = command.substring(0, command.length() - 5);

        try(PreparedStatement statement = con.prepareStatement(command)) {
            for (int i = 0; i < arguments.size(); i++){
                statement.setString(i + 1, arguments.get(i));
            }
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    users.add(new User(username, password));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return users;
    }

    @Override
    public void saveWithoutId(User user) {

    }
}
