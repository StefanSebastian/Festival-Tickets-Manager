using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking.dto
{
    [Serializable]
    public class BuyTicketsDTO
    {
        private int idShow;
        private string clientName;
        private int numberOfTickets;
        private string username;

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

        public string ClientName
        {
            get
            {
                return clientName;
            }

            set
            {
                clientName = value;
            }
        }

        public int NumberOfTickets
        {
            get
            {
                return numberOfTickets;
            }

            set
            {
                numberOfTickets = value;
            }
        }

        public string Username
        {
            get
            {
                return username;
            }

            set
            {
                username = value;
            }
        }

        public BuyTicketsDTO(int idShow, string clientName, int numberOfTickets, string username)
        {
            this.IdShow = idShow;
            this.ClientName = clientName;
            this.NumberOfTickets = numberOfTickets;
            this.username = username;
        }
    }
}
