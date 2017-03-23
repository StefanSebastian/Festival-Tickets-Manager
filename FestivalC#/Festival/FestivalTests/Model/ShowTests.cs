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
    public class ShowTests
    {
        [TestMethod()]
        public void ShowTest()
        {
            Artist artist = new Artist(1, "name");
            Show show = new Show(1, "loc", new DateTime(2017, 4, 3), 12, 0, artist);
            Assert.AreEqual(show.IdShow, 1);
            Assert.AreEqual(show.Artist, artist);
        }

        [TestMethod()]
        public void EqualsTest()
        {
            Artist artist = new Artist(1, "name");
            Show show = new Show(1, "loc", new DateTime(2017, 4, 3), 12, 0, artist);

            Artist artist1 = new Artist(1, "name");
            Show show1 = new Show(1, "loc", new DateTime(2017, 4, 3), 12, 0, artist1);
            Assert.AreEqual(show, show1);
        }

        [TestMethod()]
        public void ToStringTest()
        {
            Artist artist = new Artist(1, "name");
            Show show = new Show(1, "loc", new DateTime(2017, 4, 3), 12, 0, artist);
            Assert.AreEqual(show.ToString(), "1 loc 03-Apr-17 12:00:00 AM 12 0 1");
        }

    }
}