package Validation;

import Domain.Artist;
import Validation.Exceptions.ValidatorException;

/**
 * Created by Sebi on 18-Mar-17.
 */
public class ValidatorArtist implements IValidator<Artist> {
    @Override
    public void validate(Artist artist) throws ValidatorException {
        String errors = "";
        if (artist.getName().length() == 0){
            errors += "Artist name must not be null";
        }
        if (errors.length() != 0){
            throw new ValidatorException(errors);
        }
    }
}
