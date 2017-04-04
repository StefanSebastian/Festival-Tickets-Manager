using Festival.Model;
using Festival.Service;
using Festival.Service.AppServices;
using System;
using System.Collections.Generic;

namespace Client
{
    public class ClientController : IFestivalClient
    {
        private IFestivalServer server;

        private User user;
        
        public ClientController(IFestivalServer server)
        {
            this.server = server;
        }

        public void login(string username, string password)
        {
            User userL = new User(username, password);
            server.login(userL, this);
            user = userL;
        }

        public void logout() 
        {
            server.logout(user, this);
            user = null;
        }

        public void showUpdated(Show show) 
        {
            //notifyPushObservers(show);
        }

        public List<Artist> getArtists()
        {
            return server.getArtists();
        }

        public List<Show> getShowsForArtist(int idArtist)
        {
            return server.getShowsForArtist(idArtist); 
        }

        public List<Show> getShowsForDate(string date)
        {
            return server.getShowsForDate(date);
        }
    }
}
