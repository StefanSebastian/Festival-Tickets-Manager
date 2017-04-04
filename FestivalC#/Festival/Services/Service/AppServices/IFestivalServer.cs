using Festival.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Festival.Service.AppServices
{
    public interface IFestivalServer
    {
        /*
         Gets all artists
         */
        List<Artist> getArtists();

        /*
        Gets all shows for a given artist
         */
        List<Show> getShowsForArtist(int idArtist);

        /*
        Gets all shows for a given date
         */
        List<Show> getShowsForDate(string date);

        /*
        Attempts to buy tickets for a show
        idShow - for which we buy tickets
        clientName - name of the buyer
        numberOfTickets - number of tickets bought
        username - user that buys the tickets
         */
        void buyTicketsForShow(int idShow, String clientName, int numberOfTickets, String username);

        /*
        Login functionality
         */
        void login(User user, IFestivalClient client);

        /*
        Logout functionality
         */
        void logout(User user, IFestivalClient client);
    }
}
