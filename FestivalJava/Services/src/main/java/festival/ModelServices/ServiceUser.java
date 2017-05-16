package festival.ModelServices;

import festival.Domain.User;
import festival.Interfaces.IUserRepository;
import festival.ModelServices.Interfaces.IServiceUser;
import festival.Validation.Exceptions.ValidatorException;
import festival.Validation.ValidatorUser;

import java.util.List;

/**
 * Created by Sebi on 26-Mar-17.
 */
public class ServiceUser implements IServiceUser{
    //repository
    private IUserRepository repository;

    //validator
    private ValidatorUser validator;

    //constructor
    public ServiceUser(IUserRepository repository, ValidatorUser validator){
        this.repository = repository;
        this.validator = validator;
    }

    public void save(User user) throws ValidatorException {
        validator.validate(user);
        repository.save(user);
    }

    public void delete(String username)
    {
        repository.delete(username);
    }

    public void update(String username, User user) throws ValidatorException {
        validator.validate(user);
        repository.update(username, user);
    }

    public User getById(String username)
    {
        return repository.getById(username);
    }

    public List<User> getAll()
    {
        return repository.getAll();
    }

    public User getByNamePass(String username, String password)
    {
        return repository.getUserForLogin(username, password);
    }
}
