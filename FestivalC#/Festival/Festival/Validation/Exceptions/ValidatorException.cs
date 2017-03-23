using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Festival.Validation.Exceptions
{
    public class ValidatorException : Exception
    {
        public ValidatorException(String msg) : base(msg)
        {
        }
    }
}
