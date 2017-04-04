using Festival.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Festival.Service
{
    public interface IServiceArtist
    {
        /*
         * Saves an artist 
         */
        void save(Artist artist);

        /*
         * Deletes an artist
         */
        void delete(int idArtist);

        /*
         * Updates an artist
         */
        void update(int id, Artist artist);

        /*
         * Gets an artist by id 
         * if not found return null
         */
        Artist getById(int idArtist);

        /*
         * Returns a list of artists
         */
        List<Artist> getAll();
    }
}
