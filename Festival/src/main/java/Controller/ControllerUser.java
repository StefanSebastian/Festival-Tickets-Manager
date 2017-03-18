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
public class ControllerUser extends AbstractController<User, Integer> {
    /*
    Constructor
     */
    public ControllerUser(IDatabaseRepository<User, Integer> repositoryUser, ValidatorUser validatorUser){
        super(repositoryUser, validatorUser);
    }

    @Override
    public User formatEntity(String... args) throws FormatException {
        Integer id;
        String username;
        String password;
        try{
            id = Integer.parseInt(args[0]);
            username = args[1];
            password = args[2];
        } catch (NumberFormatException ex){
            throw new FormatException("Id must be a number");
        }
        return new User(id, username, password);
    }

    @Override
    public Integer formatId(String id) throws FormatException {
        Integer idF;
        try{
            idF = Integer.parseInt(id);
        } catch (NumberFormatException e){
            throw new FormatException("Id must be a number");
        }
        return idF;
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
