using Festival.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Festival.Service
{
    public interface IServiceShow
    {
        /*
         * Saves a show 
         */
        void save(Show show);

        /*
         * Deletes a show 
         */
        void delete(int idShow);

        /*
         * Updates a show 
         */
        void update(int id, Show show);

        /*
         * Returns a show by id 
         */
        Show getById(int idShow);

        /*
         * Gets all shows 
         */
        List<Show> getAll();

        /*
         * Gets all shows for a given artist
         */
        List<Show> getShowsForArtist(int idArtist);

        /*
         * Gets all shows for a given date 
         */
        List<Show> getShowsForDate(string date);
    }
}
