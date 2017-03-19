package Controller;

import Repository.Interfaces.IDatabaseRepository;
import Repository.Interfaces.IRepository;
import Utils.AbstractObservable;
import Validation.Exceptions.FormatException;
import Validation.Exceptions.ValidatorException;
import Validation.IValidator;

import java.text.Normalizer;
import java.util.List;

/**
 * Created by Sebi on 18-Mar-17.
 */
public abstract class AbstractController<E, ID> extends AbstractObservable {
    //Repository
    protected IDatabaseRepository<E, ID> repository;

    //Validator
    private IValidator<E> validator;

    //Constructor
    public AbstractController(IDatabaseRepository<E, ID> repository, IValidator<E> validator){
        this.repository = repository;
        this.validator = validator;
    }

    /*
    Formats an entity from a list of strings
    Validates it
    Saves it to repo if it's valid
     */
    public void save(String... args) throws ValidatorException, FormatException{
        E entity = formatEntity(args);
        validator.validate(entity);
        repository.save(entity);
        notifyObservers();
    }

    /*
    Deletes an entity
     */
    public void delete(String id) throws FormatException{
        repository.delete(formatId(id));
        notifyObservers();
    }

    /*
    Updates an entity
    Formats new values and validates them
     */
    public void update(String id, String... args) throws ValidatorException, FormatException{
        E entity = formatEntity(args);
        validator.validate(entity);
        repository.update(formatId(id), entity);
        notifyObservers();
    }

    /*
    Returns the entity with the given id, or null
     */
    public E getById(String id) throws FormatException{
        return repository.getById(formatId(id));
    }

    /*
    Returns all entities in repository
     */
    public List<E> getAll(){
        return repository.getAll();
    }

    /*
    Saves an entity to repo without saving its id
    Id is auto incremented (depending on db table)
    the given id is ignored but must be valid
     */
    public void saveWithoutId(String... args) throws ValidatorException, FormatException {
        E entity = formatEntity(args);
        validator.validate(entity);
        repository.saveWithoutId(entity);
        notifyObservers();
    }

    //formats an entity
    public abstract E formatEntity(String... args) throws FormatException;

    //formats an id
    public abstract ID formatId(String id) throws FormatException;
}
