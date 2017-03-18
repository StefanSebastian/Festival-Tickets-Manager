package Repository;

import Domain.User;
import Repository.Interfaces.IRepositoryUser;

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
public class RepositoryUserDB implements IRepositoryUser {
    //used to get connection
    private JdbcUtils jdbcUtils;

    //constructor
    public RepositoryUserDB(Properties properties){
        jdbcUtils = new JdbcUtils(properties);
    }

    @Override
    public void save(User user) {
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement statement = con.prepareStatement("INSERT INTO users values(?, ?, ?)")){
            statement.setInt(1, user.getIdUser());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Db error" + e);
        }
    }

    @Override
    public void delete(Integer id) {
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement statement = con.prepareStatement("DELETE FROM users WHERE idUser = ?")){
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Db error" + e);
        }

    }

    @Override
    public void update(Integer id, User user) {
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement statement =
                    con.prepareStatement("UPDATE users SET idUser = ?, " +
                            "username = ?, password = ? WHERE idUser = ?")){
            statement.setInt(1, user.getIdUser());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setInt(4, id);
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Db error" + e);
        }
    }

    @Override
    public User getById(Integer id) {
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement statement = con.prepareStatement("SELECT * FROM users WHERE idUser = ?")){
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    Integer idUser = resultSet.getInt("idUser");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    return new User(idUser, username, password);
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
                    Integer idUser = resultSet.getInt("idUser");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    users.add(new User(idUser, username, password));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return users;
    }
}
