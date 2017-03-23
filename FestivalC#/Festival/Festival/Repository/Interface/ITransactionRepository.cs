using Festival.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Festival.Repository.Interface
{
    public interface ITransactionRepository : IRepository<Transaction, int>
    {
        /*
         * Saves the transaction and generates a valid id 
         */
        void saveWithoutId(Transaction transaction);
    }
}
