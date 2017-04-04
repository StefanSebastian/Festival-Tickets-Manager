using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking.dto
{
    [Serializable]
    public class ShowDTO
    {
        private int id;
        private string location;
        private DateTime date;
        private int ticketsAvailable;
        private int ticketsSold;
        private ArtistDTO artist;

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

        public ArtistDTO Artist
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

        public ShowDTO(int id, string location, DateTime date,
                       int ticketsAvailable, int ticketsSold,
                       ArtistDTO artist)
        {
            this.Id = id;
            this.Location = location;
            this.Date = date;
            this.TicketsAvailable = ticketsAvailable;
            this.TicketsSold = ticketsSold;
            this.Artist = artist;
        }
    }
}
