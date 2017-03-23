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
    public class ArtistTests
    {
        [TestMethod()]
        public void ArtistTest()
        {
            Artist artist = new Artist(1, "name");
            Assert.AreEqual(artist.IdArtist, 1);
            Assert.AreEqual(artist.Name, "name");
        }

        [TestMethod()]
        public void EqualsTest()
        {
            Artist artist = new Artist(1, "name");
            Artist artist2 = new Artist(1, "name");
            Assert.AreEqual(artist, artist2);
        }

        [TestMethod()]
        public void ToStringTest()
        {
            Artist artist = new Artist(1, "name");
            Assert.AreEqual(artist.ToString(), "1 name");
        }

    }
}