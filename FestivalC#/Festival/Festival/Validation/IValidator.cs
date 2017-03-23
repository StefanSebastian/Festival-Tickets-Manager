using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Festival.Validation
{
    public interface IValidator<E>
    {
        void validate(E e);
    }
}
