using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking.dto
{
    [Serializable]
    public class ArtistDTO
    {
        private int id;
        private string name;

        public ArtistDTO(int id, string name)
        {
            this.Id = id;
            this.Name = name;
        }

        public int Id
        {
            get
            {
                return id;
            }

            set
            {
                id = value;
            }
        }

        public string Name
        {
            get
            {
                return name;
            }

            set
            {
                name = value;
            }
        }
    }
}
