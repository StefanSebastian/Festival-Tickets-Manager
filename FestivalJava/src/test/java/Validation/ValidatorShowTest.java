package Validation;

import Domain.Artist;
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
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", "2016-03-03 20:00", 100, 0, artist);
        try{
            validator.validate(show);
            assertTrue(true);
        } catch (ValidatorException e){
            assertTrue(false);
        }
    }

    @Test
    public void validateInvalidShow() throws Exception {
        Artist artist = new Artist(1, "aName");
        Show show = new Show(1, "location", "2016-03-03 20:00", -10, 0, artist);
        try{
            validator.validate(show);
            assertTrue(false);
        } catch (ValidatorException e){
            assertTrue(true);
        }
    }
}