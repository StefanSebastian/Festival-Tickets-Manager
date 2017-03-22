package Validation;

import Domain.Artist;
import Validation.Exceptions.ValidatorException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Sebi on 18-Mar-17.
 */
public class ValidatorArtistTest {
    private ValidatorArtist validator = new ValidatorArtist();

    @Test
    public void validateValidArtist() throws Exception {
        Artist a = new Artist(1, "name");
        try{
            validator.validate(a);
            assertTrue(true);
        } catch (ValidatorException e){
            assertTrue(false);
        }
    }

    @Test
    public void validateInvalidArtist() throws Exception {
        Artist a = new Artist(1, "");
        try{
            validator.validate(a);
            assertTrue(false);
        } catch (ValidatorException e){
            assertTrue(true);
        }
    }

}