package ModelServices.Interfaces;

import Domain.User;
import Validation.Exceptions.ValidatorException;

import java.util.List;

/**
 * Created by Sebi on 28-Mar-17.
 */
public interface IServiceUser {
    /*
    Saves an user
     */
    void save(User user) throws ValidatorException;

    /*
    Deletes an user
     */
    void delete(String username);

    /*
    Updates an user
     */
    void update(String id, User user) throws ValidatorException;

    /*
    Gets a user by id
     */
    User getById(String username);

    /*
    Gets all users
     */
    List<User> getAll();

    /*
    Get user by name and password
     */
    User getByNamePass(String username, String password);
}
