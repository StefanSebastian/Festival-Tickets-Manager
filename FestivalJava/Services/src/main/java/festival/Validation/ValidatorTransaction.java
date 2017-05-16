package festival.Validation;

import festival.Domain.Transaction;
import festival.Validation.Exceptions.ValidatorException;

/**
 * Created by Sebi on 18-Mar-17.
 */
public class ValidatorTransaction implements IValidator<Transaction> {
    @Override
    public void validate(Transaction transaction) throws ValidatorException {
        String errors = "";
        if (transaction.getClientName().length() == 0){
            errors += "Client name must not be null.";
        }
        if (transaction.getNumberOfTickets() < 0){
            errors += "Number of tickets must be positive.";
        }
        if (errors.length() != 0){
            throw new ValidatorException(errors);
        }
    }
}
