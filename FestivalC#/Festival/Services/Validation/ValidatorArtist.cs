using Festival.Model;
using Festival.Validation.Exceptions;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Festival.Validation
{
    public class ValidatorArtist : IValidator<Artist>
    {
        public void validate(Artist artist)
        {
            String errors = "";
            if (artist.Name.Length == 0)
            {
                errors += "Artist name must not be null";
            }
            if (errors.Length != 0)
            {
                throw new ValidatorException(errors);
            }
        }
    }
}
