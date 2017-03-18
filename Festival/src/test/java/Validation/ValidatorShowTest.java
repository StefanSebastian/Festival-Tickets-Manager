package Validation;

import Domain.Show;
import Validation.Exceptions.ValidatorException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Sebi on 18-Mar-17.
 */
public class ValidatorShowTest {
    private ValidatorShow validator = new ValidatorShow();

    @Test
    public void validateValidShow() throws Exception {
        Show s = new Show(1, "loc", "2016-03-22", 12, 0, 1);
        try{
            validator.validate(s);
            assertTrue(true);
        } catch (ValidatorException e){
            assertTrue(false);
        }
    }

    @Test
    public void validateInvalidShow() throws Exception {
        Show s = new Show(1, "loc", "2016-03-22", -12, 0, 1);
        try{
            validator.validate(s);
            assertTrue(false);
        } catch (ValidatorException e){
            assertTrue(true);
        }
    }
}