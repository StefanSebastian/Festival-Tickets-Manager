package Validation;

import Domain.Artist;
import Domain.Show;
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
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", "2016-03-03 20:00", 100, 0, artist);
        Transaction t = new Transaction(1, "client", 12, show);
        try{
            validatorTransaction.validate(t);
            assertTrue(true);
        }catch (ValidatorException e){
            assertTrue(false);
        }
    }

    @Test
    public void validateInvalidTransactionTickets() throws Exception {
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", "2016-03-03 20:00", 100, 0, artist);
        Transaction t = new Transaction(1, "client", -3, show);
        try{
            validatorTransaction.validate(t);
            assertTrue(false);
        }catch (ValidatorException e){
            assertTrue(true);
        }
    }

    @Test
    public void validateInvalidTransactionClient() throws Exception {
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", "2016-03-03 20:00", 100, 0, artist);
        Transaction t = new Transaction(1, "", 3, show);
        try{
            validatorTransaction.validate(t);
            assertTrue(false);
        }catch (ValidatorException e){
            assertTrue(true);
        }
    }
}