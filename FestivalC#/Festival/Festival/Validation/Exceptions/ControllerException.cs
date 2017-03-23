using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Festival.Validation.Exceptions
{
    public class ControllerException : Exception
    {
        public ControllerException(String msg) : base(msg) { }
    }
}
