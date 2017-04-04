using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking.dto
{
    [Serializable]
    public class TransactionDTO
    {
        private int id;
        private string clientName;
        private int numberOfTickets;
        private ShowDTO show;

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

        public ShowDTO Show
        {
            get
            {
                return show;
            }

            set
            {
                show = value;
            }
        }

        public TransactionDTO(int id, string clientName,
                           int numberOfTickets, ShowDTO show)
        {
            this.Id = id;
            this.ClientName = clientName;
            this.NumberOfTickets = numberOfTickets;
            this.Show = show;
        }
    }
}
