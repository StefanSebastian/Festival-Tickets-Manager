package Repository;

import Domain.Artist;
import Repository.Interfaces.IArtistRepository;
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
 * Created by Sebi on 09-Mar-17.
 */
public class RepositoryArtistDB implements IArtistRepository {
    //used to get connection
    private JdbcUtils jdbcUtils;

    //constructor
    public RepositoryArtistDB(Properties properties){
        jdbcUtils = new JdbcUtils(properties);
    }

    @Override
    public void save(Artist artist){
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement statement = con.prepareStatement("INSERT INTO artists values(?, ?)")){
            statement.setInt(1, artist.getIdArtist());
            statement.setString(2, artist.getName());
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Db error" + e);
        }
    }

    @Override
    public void delete(Integer integer) {
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement statement = con.prepareStatement("DELETE FROM artists WHERE idArtist = ?")){
            statement.setInt(1, integer);
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Db error" + e);
        }
    }

    @Override
    public void update(Integer integer, Artist artist) {
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement statement =
                    con.prepareStatement("UPDATE artists SET idArtist = ?, name = ? WHERE idArtist = ?")){
            statement.setInt(1, artist.getIdArtist());
            statement.setString(2, artist.getName());
            statement.setInt(3, integer);
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Db error" + e);
        }
    }

    @Override
    public Artist getById(Integer id) {
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement statement = con.prepareStatement("SELECT * FROM artists WHERE idArtist = ?")){
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    Integer idArtist = resultSet.getInt("idArtist");
                    String name = resultSet.getString("name");
                    return new Artist(idArtist, name);
                }
            }
        } catch (SQLException e){
            System.out.println("Db error" + e);
        }
        return null;
    }

    @Override
    public List<Artist> getAll() {
        Connection con = jdbcUtils.getConnection();
        List<Artist> artists = new ArrayList<>();
        try(PreparedStatement statement = con.prepareStatement("select * from artists")) {
            try(ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    Integer id = result.getInt("idArtist");
                    String name = result.getString("name");
                    artists.add(new Artist(id, name));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return artists;
    }

}
