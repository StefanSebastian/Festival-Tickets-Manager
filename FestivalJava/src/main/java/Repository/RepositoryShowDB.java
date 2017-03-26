package Repository;

import Domain.Artist;
import Domain.Show;
import Repository.Interfaces.IDatabaseRepository;
import Repository.Interfaces.IRepository;
import Repository.Interfaces.IShowRepository;

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
public class RepositoryShowDB implements IShowRepository{
    //used to get connection
    private JdbcUtils jdbcUtils;

    //constructor
    public RepositoryShowDB(Properties properties){
        jdbcUtils = new JdbcUtils(properties);
    }

    @Override
    public void save(Show show){
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement statement = con.prepareStatement("INSERT INTO shows values(?, ?, ?, ?, ?, ?)")){
            statement.setInt(1, show.getIdShow());
            statement.setString(2, show.getLocation());
            statement.setString(3, show.getDate());
            statement.setInt(4, show.getTicketsAvailable());
            statement.setInt(5, show.getTicketsSold());
            statement.setInt(6, show.getArtist().getIdArtist());
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Db error" + e);
        }
    }

    @Override
    public void delete(Integer integer) {
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement statement = con.prepareStatement("DELETE FROM shows WHERE idShow = ?")){
            statement.setInt(1, integer);
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Db error" + e);
        }
    }

    @Override
    public void update(Integer id, Show show) {
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement statement =
                    con.prepareStatement("UPDATE shows SET idShow = ?, " +
                            "location = ?, date = ?," +
                            "ticketsAvailable = ?, ticketsSold = ?," +
                            "idArtist = ? WHERE idShow = ?")){
            statement.setInt(1, show.getIdShow());
            statement.setString(2, show.getLocation());
            statement.setString(3, show.getDate());
            statement.setInt(4, show.getTicketsAvailable());
            statement.setInt(5, show.getTicketsSold());
            statement.setInt(6, show.getArtist().getIdArtist());
            statement.setInt(7, id);
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Db error" + e);
        }
    }

    @Override
    public Show getById(Integer id) {
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement statement = con.prepareStatement("SELECT idShow, location, date, " +
                "ticketsAvailable, ticketsSold, shows.idArtist, name " +
                "FROM shows inner join artists on shows.idArtist = artists.idArtist " +
                "where idShow = ? ")){
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    Integer idShow = resultSet.getInt("idShow");
                    String location = resultSet.getString("location");
                    String date = resultSet.getString("date");
                    Integer ticketsAvailable = resultSet.getInt("ticketsAvailable");
                    Integer ticketsSold = resultSet.getInt("ticketsSold");
                    Integer idArtist = resultSet.getInt("idArtist");
                    String nameArtist = resultSet.getString("name");
                    Artist artist = new Artist(idArtist, nameArtist);
                    return new Show(idShow, location, date, ticketsAvailable, ticketsSold, artist);
                }
            }
        } catch (SQLException e){
            System.out.println("Db error" + e);
        }
        return null;
    }

    @Override
    public List<Show> getAll() {
        Connection con = jdbcUtils.getConnection();
        List<Show> shows = new ArrayList<>();
        try(PreparedStatement statement = con.prepareStatement("SELECT " +
                "idShow, location, date, " +
                "ticketsAvailable, ticketsSold, shows.idArtist, name " +
                "FROM shows inner join artists on shows.idArtist = artists.idArtist")) {
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Integer idShow = resultSet.getInt("idShow");
                    String location = resultSet.getString("location");
                    String date = resultSet.getString("date");
                    Integer ticketsAvailable = resultSet.getInt("ticketsAvailable");
                    Integer ticketsSold = resultSet.getInt("ticketsSold");
                    Integer idArtist = resultSet.getInt("idArtist");
                    String nameArtist = resultSet.getString("name");
                    Artist artist = new Artist(idArtist, nameArtist);
                    shows.add(new Show(idShow, location, date, ticketsAvailable, ticketsSold, artist));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return shows;
    }


    @Override
    public List<Show> getShowsForArtist(Integer idArtist) {
        Connection con = jdbcUtils.getConnection();
        List<Show> shows = new ArrayList<>();
        try(PreparedStatement statement = con.prepareStatement("SELECT " +
                "idShow, location, date, " +
                "ticketsAvailable, ticketsSold, shows.idArtist, name " +
                "FROM shows inner join artists on shows.idArtist = artists.idArtist" +
                " where shows.idArtist = ?")) {
            statement.setInt(1, idArtist);
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Integer idShow = resultSet.getInt("idShow");
                    String location = resultSet.getString("location");
                    String date = resultSet.getString("date");
                    Integer ticketsAvailable = resultSet.getInt("ticketsAvailable");
                    Integer ticketsSold = resultSet.getInt("ticketsSold");
                    Integer idArt = resultSet.getInt("idArtist");
                    String nameArtist = resultSet.getString("name");
                    Artist artist = new Artist(idArtist, nameArtist);
                    shows.add(new Show(idShow, location, date, ticketsAvailable, ticketsSold, artist));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return shows;
    }

    @Override
    public List<Show> getShowsForDate(String date) {
        Connection con = jdbcUtils.getConnection();
        List<Show> shows = new ArrayList<>();
        try(PreparedStatement statement = con.prepareStatement("SELECT " +
                "idShow, location, date, " +
                "ticketsAvailable, ticketsSold, shows.idArtist, name " +
                "FROM shows inner join artists on shows.idArtist = artists.idArtist" +
                " where date = ?")) {
            statement.setString(1, date);
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Integer idShow = resultSet.getInt("idShow");
                    String location = resultSet.getString("location");
                    String dateShow = resultSet.getString("date");
                    Integer ticketsAvailable = resultSet.getInt("ticketsAvailable");
                    Integer ticketsSold = resultSet.getInt("ticketsSold");
                    Integer idArt = resultSet.getInt("idArtist");
                    String nameArtist = resultSet.getString("name");
                    Artist artist = new Artist(idArt, nameArtist);
                    shows.add(new Show(idShow, location, date, ticketsAvailable, ticketsSold, artist));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return shows;
    }
}
