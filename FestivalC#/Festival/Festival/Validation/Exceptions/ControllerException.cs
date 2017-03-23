using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Festival.Validation.Exceptions
{
    public class FormatException : Exception
    {
        public FormatException(String msg) : base(msg) { }
    }
}
