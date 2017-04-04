using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Festival.Model
{
    public class Show
    {
        //id
        private int idShow;

        //location of the show
        private String location;

        //date 
        private DateTime date;

        //number of available tickets
        private int ticketsAvailable;

        //number of sold tickets
        private int ticketsSold;

        //id of the artist
        private Artist artist;

        //constructor
        public Show(int idShow, String location, DateTime date, int ticketsAvailable, int ticketsSold, Artist artist)
        {
            this.idShow = idShow;
            this.location = location;
            this.date = date;
            this.ticketsAvailable = ticketsAvailable;
            this.ticketsSold = ticketsSold;
            this.artist = artist;
        }

        public int IdShow
        {
            get
            {
                return idShow;
            }

            set
            {
                idShow = value;
            }
        }

        public string Location
        {
            get
            {
                return location;
            }

            set
            {
                location = value;
            }
        }

        public DateTime Date
        {
            get
            {
                return date;
            }

            set
            {
                date = value;
            }
        }

        public int TicketsAvailable
        {
            get
            {
                return ticketsAvailable;
            }

            set
            {
                ticketsAvailable = value;
            }
        }

        public int TicketsSold
        {
            get
            {
                return ticketsSold;
            }

            set
            {
                ticketsSold = value;
            }
        }

        public Artist Artist
        {
            get
            {
                return artist;
            }

            set
            {
                artist = value;
            }
        }

        public override bool Equals(object obj)
        {
            if (obj == null)
            {
                return false;
            }
            if (!(obj is Show))
            {
                return false;
            }
            Show s = (Show)obj;
            return s.idShow == idShow && s.location.Equals(location)
                && s.date.Equals(date) && s.ticketsAvailable == ticketsAvailable 
                && s.ticketsSold == ticketsSold && s.artist.Equals(artist);
        }

        public override string ToString()
        {
            return idShow + " " + location + " " + date.ToString() + " " + ticketsAvailable + " " + ticketsSold + " " + artist.IdArtist;
        }

        public override int GetHashCode()
        {
            return base.GetHashCode();
        }
    }
}
