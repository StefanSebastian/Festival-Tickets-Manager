using Festival.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Festival.Repository.Interface
{
    public interface IShowRepository : IRepository<Show, int>
    {
        /*
         * Gets all shows for a given artist 
         */
        List<Show> getShowsForArtist(Artist artist);

        /*
         * Gets all shows for a given date 
         */
        List<Show> getShowsForDate(String date);
    }
}
