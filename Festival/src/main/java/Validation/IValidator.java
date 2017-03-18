package Validation;

import Validation.Exceptions.ValidatorException;

/**
 * Created by Sebi on 18-Mar-17.
 */
public interface IValidator<E> {
    void validate(E entity) throws ValidatorException;
}
