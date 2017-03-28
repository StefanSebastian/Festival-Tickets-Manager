using Festival.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Festival.Service
{
    public interface IServiceTransaction
    {
        /*
         * Saves a transaction
         */
        void save(Transaction transaction);

        /*
         * Deletes a transaction
         */
        void delete(int idTransaction);

        /*
         * Updates a transaction
         */
        void update(int id, Transaction transaction);

        /*
         * Gets a transaction
         */
        Transaction getById(int idArtist);

        /*
         * Returns all transactions
         */
        List<Transaction> getAll();

        /*
         * Saves a transaction without id
         */
        void saveWithoutId(Transaction transaction);
    }
}
