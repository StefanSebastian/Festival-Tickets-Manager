package festival.JDBC;

import festival.Domain.Artist;
import festival.Domain.Show;
import festival.Domain.Transaction;
import festival.Interfaces.ITransactionRepository;

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
public class RepositoryTransactionDB implements ITransactionRepository {
    //used to get connection
    private JdbcUtils jdbcUtils;

    //constructor
    public RepositoryTransactionDB(Properties properties){
        jdbcUtils = new JdbcUtils(properties);
    }

    @Override
    public void save(Transaction transaction){
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement statement = con.prepareStatement("INSERT INTO transactions values(?, ?, ?, ?)")){
            statement.setInt(1, transaction.getIdTransaction());
            statement.setString(2, transaction.getClientName());
            statement.setInt(3, transaction.getNumberOfTickets());
            statement.setInt(4, transaction.getShow().getIdShow());
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Db error" + e);
        }
    }

    @Override
    public void delete(Integer integer) {
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement statement = con.prepareStatement("DELETE FROM transactions WHERE idTransaction = ?")){
            statement.setInt(1, integer);
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Db error" + e);
        }
    }

    @Override
    public void update(Integer id, Transaction transaction) {
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement statement =
                    con.prepareStatement("UPDATE transactions SET idTransaction = ?, " +
                            "clientName = ?, numberOfTickets = ?," +
                            "idShow = ? WHERE idTransaction = ?")){
            statement.setInt(1, transaction.getIdTransaction());
            statement.setString(2, transaction.getClientName());
            statement.setInt(3, transaction.getNumberOfTickets());
            statement.setInt(4, transaction.getShow().getIdShow());
            statement.setInt(5, id);
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Db error" + e);
        }
    }

    @Override
    public Transaction getById(Integer id) {
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement statement = con.prepareStatement("SELECT " +
                "idTransaction, clientName, numberOfTickets, " +
                "shows.idShow, location, date, ticketsAvailable, ticketsSold, " +
                "artists.idArtist, name " +
                "from transactions " +
                "INNER JOIN shows on transactions.idShow = shows.idShow " +
                "INNER JOIN artists on shows.idArtist = artists.idArtist " +
                "where idTransaction = ?")){
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    Integer idTransaction = resultSet.getInt("idTransaction");
                    String clientName = resultSet.getString("clientName");
                    Integer numberOfTickets = resultSet.getInt("numberOfTickets");
                    Integer idShow = resultSet.getInt("idShow");
                    String location = resultSet.getString("location");
                    java.util.Date date = resultSet.getDate("date");
                    Integer ticketsAvailable = resultSet.getInt("ticketsAvailable");
                    Integer ticketsSold = resultSet.getInt("ticketsSold");
                    Integer idArtist = resultSet.getInt("idArtist");
                    String name = resultSet.getString("name");
                    Artist artist = new Artist(idArtist, name);
                    Show show = new Show(idShow, location, date, ticketsAvailable, ticketsSold, artist);
                    return new Transaction(idTransaction, clientName, numberOfTickets, show);
                }
            }
        } catch (SQLException e){
            System.out.println("Db error" + e);
        }
        return null;
    }

    @Override
    public List<Transaction> getAll() {
        Connection con = jdbcUtils.getConnection();
        List<Transaction> transactions = new ArrayList<>();
        try(PreparedStatement statement = con.prepareStatement("SELECT " +
                "idTransaction, clientName, numberOfTickets, " +
                "shows.idShow, location, date, ticketsAvailable, ticketsSold, " +
                "artists.idArtist, name " +
                "from transactions " +
                "INNER JOIN shows on transactions.idShow = shows.idShow " +
                "INNER JOIN artists on shows.idArtist = artists.idArtist")) {
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Integer idTransaction = resultSet.getInt("idTransaction");
                    String clientName = resultSet.getString("clientName");
                    Integer numberOfTickets = resultSet.getInt("numberOfTickets");
                    Integer idShow = resultSet.getInt("idShow");
                    String location = resultSet.getString("location");
                    java.util.Date date = resultSet.getDate("date");
                    Integer ticketsAvailable = resultSet.getInt("ticketsAvailable");
                    Integer ticketsSold = resultSet.getInt("ticketsSold");
                    Integer idArtist = resultSet.getInt("idArtist");
                    String name = resultSet.getString("name");
                    Artist artist = new Artist(idArtist, name);
                    Show show = new Show(idShow, location, date, ticketsAvailable, ticketsSold, artist);
                    transactions.add(new Transaction(idTransaction, clientName, numberOfTickets, show));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return transactions;
    }

    @Override
    public void saveWithoutId(Transaction transaction) {
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement statement = con.prepareStatement("INSERT INTO " +
                "transactions(clientName, numberOfTickets, idShow)" +
                " values(?, ?, ?)")){
            statement.setString(1, transaction.getClientName());
            statement.setInt(2, transaction.getNumberOfTickets());
            statement.setInt(3, transaction.getShow().getIdShow());
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Db error" + e);
        }
    }
}
