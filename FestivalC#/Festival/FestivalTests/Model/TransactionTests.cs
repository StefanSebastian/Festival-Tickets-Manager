using Microsoft.VisualStudio.TestTools.UnitTesting;
using Festival.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Festival.Model.Tests
{
    [TestClass()]
    public class TransactionTests
    {
        [TestMethod()]
        public void TransactionTest()
        {
            Artist artist = new Artist(1, "name");
            Show show = new Show(1, "loc", new DateTime(2017, 4, 3), 12, 0, artist);
            Transaction transaction = new Transaction(1, "client", 2, show);
            Assert.AreEqual(transaction.IdTransaction, 1);
        }

        [TestMethod()]
        public void EqualsTest()
        {
            Artist artist = new Artist(1, "name");
            Show show = new Show(1, "loc", new DateTime(2017, 4, 3), 12, 0, artist);
            Transaction transaction = new Transaction(1, "client", 2, show);

            Artist artist1 = new Artist(1, "name");
            Show show1 = new Show(1, "loc", new DateTime(2017, 4, 3), 12, 0, artist1);
            Transaction transaction1 = new Transaction(1, "client", 2, show1);

            Assert.AreEqual(transaction, transaction1);
        }

        [TestMethod()]
        public void ToStringTest()
        {
            Artist artist = new Artist(1, "name");
            Show show = new Show(1, "loc", new DateTime(2017, 4, 3), 12, 0, artist);
            Transaction transaction = new Transaction(1, "client", 2, show);

            Assert.AreEqual(transaction.ToString(), "1 client 2 1");
        }

    }
}