using Festival.Model;
using Festival.Validation.Exceptions;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Festival.Validation
{
    public class ValidatorTransaction : IValidator<Transaction>
    {
        public void validate(Transaction transaction)
        {
            String errors = "";
            if (transaction.ClientName.Length == 0)
            {
                errors += "Client name must not be null.";
            }
            if (transaction.NumberOfTickets < 0)
            {
                errors += "Number of tickets must be positive.";
            }
            if (errors.Length != 0)
            {
                throw new ValidatorException(errors);
            }
        }
    }
}
