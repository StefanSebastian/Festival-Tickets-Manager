using Festival.Model;
using Festival.Service;
using Festival.Service.AppServices;
using Services.Validation.Exceptions;
using System;
using System.Collections.Generic;

namespace Server
{
    public class ServerController : IFestivalServer
    {
        /*
        Model services
         */
        private IServiceArtist serviceArtist;
        private IServiceShow serviceShow;
        private IServiceTransaction serviceTransaction;
        private IServiceUser serviceUser;

        /*
        Logged in clients
         */
        private Dictionary<string, IFestivalClient> loggedClients;

        public ServerController(IServiceArtist serviceArtist,
                                      IServiceShow serviceShow,
                                      IServiceTransaction serviceTransaction,
                                      IServiceUser serviceUser)
        {
            this.serviceArtist = serviceArtist;
            this.serviceShow = serviceShow;
            this.serviceTransaction = serviceTransaction;
            this.serviceUser = serviceUser;

            loggedClients = new Dictionary<string, IFestivalClient>();
        }

     
        public List<Artist> getArtists() 
        {
            return serviceArtist.getAll();
        }


        public List<Show> getShowsForArtist(int idArtist)
        {
             return serviceShow.getShowsForArtist(idArtist);
        }


        public List<Show> getShowsForDate(String date) 
        {
            return serviceShow.getShowsForDate(date);
        }


        public void buyTicketsForShow(int idShow, String clientName, int numberOfTickets, String username)
        {
            Show show = serviceShow.getById(idShow);

            //check if there are enough tickets
            if (numberOfTickets > show.TicketsAvailable)
            {
                throw new ServiceException("Not enough tickets available");
            }

            //update selected show
            show.TicketsAvailable -= numberOfTickets;
            show.TicketsSold += numberOfTickets;
      
            serviceShow.update(idShow, show);

            //register transaction
            serviceTransaction.saveWithoutId(new Transaction(clientName, numberOfTickets, show));

            //notify everyone
            notifyUsersTransaction(username, show);
        }


        /*
        Notifies all logged in users that a transaction has been made for a show
        does not notify the buyer
            */
        private void notifyUsersTransaction(string buyer, Show show) 
        {
            //get all logged users
            List<string> loggedUsers = new List<string>(loggedClients.Keys);

            //notifies all clients
            foreach (string username in loggedUsers)
            {
                if (!username.Equals(buyer))
                {
                    IFestivalClient client = loggedClients[username];
                    client.showUpdated(show);
                }
            } 
        }
    

        public void login(User user, IFestivalClient client) 
        {
            User userR = serviceUser.getByNamePass(user.Username, user.Password);

            //invalid user
            if (userR == null)
            {
                throw new ServiceException("Invalid username/password");
            }

            //already logged in
            if (loggedClients.ContainsKey(user.Username))
            {
                throw new ServiceException("User is already logged in");
            }

            //saves the user
            loggedClients.Add(user.Username, client);
        }


        public void logout(User user, IFestivalClient client) 
        {
            IFestivalClient festivalClient = loggedClients[user.Username];
            loggedClients.Remove(user.Username);

            if (festivalClient == null)
            {
                throw new ServiceException("User " + user.Username + " is not logged in");
             }
        }
    }
}
