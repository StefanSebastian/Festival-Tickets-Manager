package festival.Validation;

import festival.Validation.Exceptions.ValidatorException;

/**
 * Created by Sebi on 18-Mar-17.
 */
public interface IValidator<E> {
    void validate(E entity) throws ValidatorException;
}
