using Festival.Model;
using Festival.Service;
using Festival.Validation.Exceptions;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Festival.Controller
{
    public class ControllerApp
    {
        private IServiceUser serviceUser;
        private IServiceArtist serviceArtist;
        private IServiceShow serviceShow;
        private IServiceTransaction serviceTransaction;
        
        public ControllerApp(
            IServiceUser serviceUser,
            IServiceArtist serviceArtist,
            IServiceShow serviceShow,
            IServiceTransaction serviceTransaction
            )
        {
            this.serviceUser = serviceUser;
            this.serviceArtist = serviceArtist;
            this.serviceShow = serviceShow;
            this.serviceTransaction = serviceTransaction;
        }
        
        /*
         * Tries to login a user 
         * returns null if user is not valid 
         */
        public User tryLogin(string username, string password)
        {
            return serviceUser.getByNamePass(username, password);
        }
        
        /*
         * Gets a list of all the artists
         */
        public List<Artist> getAllArtists()
        {
            return serviceArtist.getAll();
        } 

        /*
         * Gets all shows for a given artist
         */
        public List<Show> getShowsForArtist(int idArtist)
        {
            return serviceShow.getShowsForArtist(idArtist);
        }

        /*
         * Gets all shows for a given date 
         */
        public List<Show> getShowsForDate(string date)
        {
            return serviceShow.getShowsForDate(date);
        }

        /*
         * Buy tickets for show 
         * If not enough tickets -> throw exception
         * Else we update show tickets number 
         * And we save the transaction
         */
         public void buyTicketsForShow(int idShow, string clientName, int nrTickets)
        {
            Show show = serviceShow.getById(idShow);

            if (nrTickets < 0)
            {
                throw new ControllerException("You must buy at least one ticket");
            }

            if (show.TicketsAvailable < nrTickets)
            {
                throw new ControllerException("Not enough tickets available");
            }

            show.TicketsAvailable -= nrTickets;
            show.TicketsSold += nrTickets;

            serviceShow.update(idShow, show);

            serviceTransaction.saveWithoutId(new Transaction(clientName, nrTickets, show));
        }
    }
}
