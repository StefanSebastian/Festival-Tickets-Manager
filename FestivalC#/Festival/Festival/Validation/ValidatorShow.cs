using Festival.Model;
using Festival.Validation.Exceptions;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Festival.Validation
{
    public class ValidatorShow : IValidator<Show>
    {
        public void validate(Show show)
        {
            String errors = "";
            if (show.Location.Length == 0)
            {
                errors += "Location must not be null";
            }
            if (show.TicketsAvailable < 0)
            {
                errors += "Availbale tickets must be a positive number";
            }
            if (show.TicketsSold < 0)
            {
                errors += "Sold tickets must be a positive number";
            }
            if (errors.Length != 0)
            {
                throw new ValidatorException(errors);
            }
        }
    }
}
