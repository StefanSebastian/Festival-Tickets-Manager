using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using Festival.Repository;
using Festival.Model;
using Festival.Repository.Interface;

namespace Festival
{
    static class Program
    {
        static void Main()
        {
            /*IShowRepository repo = new ShowDbRepository();
            List<Show> shows = repo.getAll();

            shows = repo.getShowsForDate("2017-04-15");
            
            foreach(Show s in shows)
            {
                Console.Out.WriteLine(s);
                Console.Out.WriteLine(s.Artist);
            }*/
           

            ITransactionRepository repo = new TransactionDbRepository();
             List<Transaction> trs = repo.getAll();
             foreach(Transaction t in trs)
             {
                 Console.Out.WriteLine(t);
                 Console.Out.WriteLine(t.Show);
                 Console.Out.WriteLine(t.Show.Artist);
             } 

            
        }
    }
}
