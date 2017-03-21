package Controller;

import Domain.User;
import Repository.Interfaces.IDatabaseRepository;
import Validation.Exceptions.FormatException;
import Validation.ValidatorUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebi on 18-Mar-17.
 */
public class ControllerUser extends AbstractController<User, String> {
    /*
    Constructor
     */
    public ControllerUser(IDatabaseRepository<User, String> repositoryUser, ValidatorUser validatorUser){
        super(repositoryUser, validatorUser);
    }

    @Override
    public User formatEntity(String... args) throws FormatException {
        Integer id;
        String username = args[0];
        String password = args[1];
        return new User(username, password);
    }

    @Override
    public String formatId(String id) throws FormatException {
        return id;
    }

    /*
    Checks whether a user can log in
     */
    public boolean login(String username, String password){
        List<String> filters = new ArrayList<>();
        List<String> parameters = new ArrayList<>();

        filters.add("username = ?");
        filters.add("password = ?");
        parameters.add(username);
        parameters.add(password);

        return repository.filter(filters, parameters).size() != 0;
    }
}
