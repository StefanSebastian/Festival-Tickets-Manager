using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Festival.Model
{
    public class Artist
    {
        //id
        private int idArtist;

        //name
        private String name;

        //Constructor
        public Artist(int idArtist, String name)
        {
            this.idArtist = idArtist;
            this.name = name;
        }

        public int IdArtist
        {
            get
            {
                return idArtist;
            }

            set
            {
                idArtist = value;
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

        public override bool Equals(object obj)
        {
            if (obj == null)
            {
                return false;
            }
            if (!(obj is Artist))
            {
                return false;
            }
            Artist a = (Artist)obj;
            return a.idArtist.Equals(idArtist) && a.name.Equals(name);
        }

        public override string ToString()
        {
            return idArtist + " " + name;
        }

        public override int GetHashCode()
        {
            return base.GetHashCode();
        }
    }
}
