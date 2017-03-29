package ModelServices.Interfaces;

import Domain.Transaction;
import Validation.Exceptions.ValidatorException;

import java.util.List;

/**
 * Created by Sebi on 28-Mar-17.
 */
public interface IServiceTransaction {
    /*
    Saves a transaction
     */
    void save(Transaction transaction) throws ValidatorException;

    /*
    Saves a transaction and generates id
     */
    void saveWithoutId(Transaction transaction) throws ValidatorException;

    /*
    Deletes a transaction
     */
    void delete(Integer idTransaction);

    /*
    Updates a transaction
     */
    void update(Integer idTransaction, Transaction transaction) throws ValidatorException;

    /*
    Gets a transaction by id
     */
    Transaction getById(Integer idTransaction);

    /*
    Gets all transactions
     */
    List<Transaction> getAll();
}
