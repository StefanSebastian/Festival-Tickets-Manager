package Repository.Interfaces;

import Domain.User;

/**
 * Created by Sebi on 26-Mar-17.
 */
public interface IUserRepository extends IRepository<User, String> {
    /*
    Returns a user entity if login is possible
    else null
     */
    User getUserForLogin(String username, String password);
}
