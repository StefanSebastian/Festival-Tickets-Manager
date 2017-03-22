using Festival.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Festival.Repository.Interface
{
    public interface IUserRepository : IRepository<User, String>
    {
        /*
         * Used for login
         */
        User getUserFor(String username, String password);
    }
}
