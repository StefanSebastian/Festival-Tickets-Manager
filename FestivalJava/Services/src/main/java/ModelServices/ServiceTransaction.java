package ModelServices;

import Domain.Transaction;
import Repository.Interfaces.ITransactionRepository;
import ModelServices.Interfaces.IServiceTransaction;
import Validation.Exceptions.ValidatorException;
import Validation.ValidatorTransaction;

import java.util.List;

/**
 * Created by Sebi on 26-Mar-17.
 */
public class ServiceTransaction implements IServiceTransaction {
    //repo
    private ITransactionRepository repository;

    //validator
    private ValidatorTransaction validator;

    //constructor
    public ServiceTransaction(ITransactionRepository repository, ValidatorTransaction validator)
    {
        this.repository = repository;
        this.validator = validator;
    }

    public void save(Transaction transaction) throws ValidatorException {
        validator.validate(transaction);
        repository.save(transaction);
    }

    public void delete(Integer id)
    {
        repository.delete(id);
    }

    public void update(Integer id, Transaction transaction) throws ValidatorException {
        validator.validate(transaction);
        repository.update(id, transaction);
    }

    public Transaction getById(Integer id)
    {
        return repository.getById(id);
    }

    public List<Transaction> getAll()
    {
        return repository.getAll();
    }

    /*
     * saves transaction and generates another id
     */
    public void saveWithoutId(Transaction transaction) throws ValidatorException {
        validator.validate(transaction);
        repository.saveWithoutId(transaction);
    }
}
