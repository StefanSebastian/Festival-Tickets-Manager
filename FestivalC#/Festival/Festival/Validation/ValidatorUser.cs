using Festival.Model;
using Festival.Validation.Exceptions;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Festival.Validation
{
    public class ValidatorUser : IValidator<User>
    {
        public void validate(User user)
        {
            String errors = "";
            if (user.Username.Length == 0)
            {
                errors += "Username must not be empty";
            }
            if (user.Password.Length == 0)
            {
                errors += "Password must not be empty";
            }
            if (errors.Length != 0)
            {
                throw new ValidatorException(errors);
            }
        }
    }
}
