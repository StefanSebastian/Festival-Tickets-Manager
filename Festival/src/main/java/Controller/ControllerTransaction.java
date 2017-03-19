package Controller;

import Domain.Transaction;
import Repository.Interfaces.IDatabaseRepository;
import Validation.Exceptions.FormatException;
import Validation.ValidatorTransaction;

/**
 * Created by Sebi on 18-Mar-17.
 */
public class ControllerTransaction extends AbstractController<Transaction, Integer> {
    /*
   Constructor
    */
    public ControllerTransaction(IDatabaseRepository<Transaction, Integer> repositoryTransaction,
                                 ValidatorTransaction validatorTransaction){
        super(repositoryTransaction, validatorTransaction);
    }

    @Override
    public Transaction formatEntity(String... args) throws FormatException {
        Integer id;
        String clientName;
        Integer nrTickets;
        Integer idShow;
        try{
            id = Integer.parseInt(args[0]);
            clientName = args[1];
            nrTickets = Integer.parseInt(args[2]);
            idShow = Integer.parseInt(args[3]);
        } catch (NumberFormatException ex){
            throw new FormatException("Invalid format. Ids and number of tickets must be integer.");
        }
        return new Transaction(id, clientName, nrTickets, idShow);
    }

    @Override
    public Integer formatId(String id) throws FormatException {
        Integer idF;
        try{
            idF = Integer.parseInt(id);
        } catch (NumberFormatException e){
            throw new FormatException("Id must be a number");
        }
        return idF;
    }

}
