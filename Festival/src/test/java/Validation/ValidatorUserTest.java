package Validation;

import Domain.Artist;
import Domain.User;
import Validation.Exceptions.ValidatorException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Sebi on 18-Mar-17.
 */
public class ValidatorUserTest {
    private ValidatorUser validator = new ValidatorUser();

    @Test
    public void validateValidUser() throws Exception {
        User a = new User(1, "name", "pass");
        try{
            validator.validate(a);
            assertTrue(true);
        } catch (ValidatorException e){
            assertTrue(false);
        }
    }

    @Test
    public void validateInvalidUser() throws Exception {
        User a = new User(1, "", "");
        try{
            validator.validate(a);
            assertTrue(false);
        } catch (ValidatorException e){
            assertTrue(true);
        }
    }

}