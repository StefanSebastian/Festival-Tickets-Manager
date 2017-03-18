package Controller;

import Domain.User;
import Repository.Interfaces.IRepositoryShow;
import Repository.Interfaces.IRepositoryUser;
import Validation.Exceptions.FormatException;
import Validation.ValidatorShow;
import Validation.ValidatorUser;

/**
 * Created by Sebi on 18-Mar-17.
 */
public class ControllerUser extends AbstractController<User, Integer> {
    /*
    Constructor
     */
    public ControllerUser(IRepositoryUser repositoryUser, ValidatorUser validatorUser){
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
}
