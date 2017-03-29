package Validation;

import Domain.User;
import Validation.Exceptions.ValidatorException;

/**
 * Created by Sebi on 18-Mar-17.
 */
public class ValidatorUser implements IValidator<User> {
    @Override
    public void validate(User user) throws ValidatorException {
        String errors = "";
        if (user.getUsername().length() == 0){
            errors += "Username must not be empty";
        }
        if (user.getPassword().length() == 0){
            errors += "Password must not be empty";
        }
        if (errors.length() != 0){
            throw new ValidatorException(errors);
        }
    }
}
