package Repository;

import Domain.Show;
import Domain.Transaction;

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
public class RepositoryTransactionDB implements IRepository<Transaction, Integer> {
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
            statement.setInt(4, transaction.getIdShow());
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
            statement.setInt(4, transaction.getIdShow());
            statement.setInt(5, id);
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Db error" + e);
        }
    }

    @Override
    public Transaction getById(Integer id) {
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement statement = con.prepareStatement("SELECT * FROM transactions WHERE idTransaction = ?")){
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    Integer idTransaction = resultSet.getInt("idTransaction");
                    String clientName = resultSet.getString("clientName");
                    Integer numberOfTickets = resultSet.getInt("numberOfTickets");
                    Integer idShow = resultSet.getInt("idShow");
                    return new Transaction(idTransaction, clientName, numberOfTickets, idShow);
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
        try(PreparedStatement statement = con.prepareStatement("select * from transactions")) {
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Integer idTransaction = resultSet.getInt("idTransaction");
                    String clientName = resultSet.getString("clientName");
                    Integer numberOfTickets = resultSet.getInt("numberOfTickets");
                    Integer idShow = resultSet.getInt("idShow");
                    transactions.add(new Transaction(idTransaction, clientName, numberOfTickets, idShow));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return transactions;
    }
}
