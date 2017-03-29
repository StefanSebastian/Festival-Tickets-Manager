package Validation;

import Domain.Show;
import Validation.Exceptions.ValidatorException;

/**
 * Created by Sebi on 18-Mar-17.
 */
public class ValidatorShow implements IValidator<Show> {
    @Override
    public void validate(Show show) throws ValidatorException {
        String errors = "";
        if (show.getLocation().length() == 0){
            errors += "Location must not be null";
        }
        if (show.getDate().length() == 0){
            errors += "Date must not be null";
        }
        if (show.getTicketsAvailable() < 0){
            errors += "Availbale tickets must be a positive number";
        }
        if (show.getTicketsSold() < 0){
            errors += "Sold tickets must be a positive number";
        }
        if (errors.length() != 0){
            throw new ValidatorException(errors);
        }
    }
}
