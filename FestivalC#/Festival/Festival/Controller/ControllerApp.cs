using Festival.Model;
using Festival.Service;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Festival.Controller
{
    public class ControllerApp
    {
        private ServiceUser serviceUser;
        private ServiceArtist serviceArtist;
        private ServiceShow serviceShow;
        private ServiceTransaction serviceTransaction;
        
        public ControllerApp(
            ServiceUser serviceUser,
            ServiceArtist serviceArtist,
            ServiceShow serviceShow,
            ServiceTransaction serviceTransaction
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
         * Saves the transaction and generates an id 
         * the given id is ignored 
         */
         public void saveTransactionWithoutId(Transaction transaction)
        {
            serviceTransaction.saveWithoutId(transaction);
        }
    }
}
