using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Festival.Model
{
    public class Transaction
    {
        //id
        private int idTransaction;

        //name of the client
        private String clientName;

        //number of tickets sold 
        private int numberOfTickets;

        //id of the show
        private Show show;

        //Constructor
        public Transaction(int idTransaction, String clientName, int numberOfTickets, Show show)
        {
            this.idTransaction = idTransaction;
            this.clientName = clientName;
            this.numberOfTickets = numberOfTickets;
            this.Show = show;
        }

        //Constructor with default id 
        public Transaction(String clientName, int numberOfTickets, Show show)
        {
            this.idTransaction = 1;
            this.clientName = clientName;
            this.numberOfTickets = numberOfTickets;
            this.Show = show;
        }
        

        public int IdTransaction
        {
            get
            {
                return idTransaction;
            }

            set
            {
                idTransaction = value;
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

        public Show Show
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

        public override bool Equals(object obj)
        {
            if (obj == null)
            {
                return false;
            }
            if (!(obj is Transaction))
            {
                return false;
            }
            Transaction t = (Transaction)obj;
            return t.idTransaction == idTransaction && t.clientName.Equals(clientName)
                && t.numberOfTickets == numberOfTickets && t.show.Equals(show);
        }

        public override string ToString()
        {
            return idTransaction + " " + clientName + " " + numberOfTickets + " " + show.IdShow;
        }

        public override int GetHashCode()
        {
            return base.GetHashCode();
        }
    }
}
