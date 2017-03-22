package Validation;

import Domain.Transaction;
import Validation.Exceptions.ValidatorException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Sebi on 18-Mar-17.
 */
public class ValidatorTransactionTest {
    private ValidatorTransaction validatorTransaction = new ValidatorTransaction();

    @Test
    public void validateValidTransaction() throws Exception {
        Transaction t = new Transaction(1, "client", 12, 1);
        try{
            validatorTransaction.validate(t);
            assertTrue(true);
        }catch (ValidatorException e){
            assertTrue(false);
        }
    }

    @Test
    public void validateInvalidTransactionTickets() throws Exception {
        Transaction t = new Transaction(1, "client", -3, 1);
        try{
            validatorTransaction.validate(t);
            assertTrue(false);
        }catch (ValidatorException e){
            assertTrue(true);
        }
    }

    @Test
    public void validateInvalidTransactionClient() throws Exception {
        Transaction t = new Transaction(1, "", 3, 1);
        try{
            validatorTransaction.validate(t);
            assertTrue(false);
        }catch (ValidatorException e){
            assertTrue(true);
        }
    }
}