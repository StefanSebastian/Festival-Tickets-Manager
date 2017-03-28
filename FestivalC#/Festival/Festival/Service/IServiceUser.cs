using Festival.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Festival.Service
{
    public interface IServiceUser
    {
        /*
         * Saves an user
         */
        void save(User user);

        /*
         * Deletes an user
         */
        void delete(string username);

        /*
         * Updates an user
         */
        void update(string username, User user);

        /*
         * Gets an user
         */
        User getById(string username);

        /*
         * Gets all users
         */
        List<User> getAll();

        /*
         * Gets an user by name + pass
         */
        User getByNamePass(string username, string password);
    }
}
