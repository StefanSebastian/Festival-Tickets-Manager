using Microsoft.VisualStudio.TestTools.UnitTesting;
using Festival.Validation;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Festival.Model;
using Festival.Validation.Exceptions;

namespace Festival.Validation.Tests
{
    [TestClass()]
    public class ValidatorArtistTests
    {
        [TestMethod()]
        public void validateTest()
        {
            ValidatorArtist validator = new ValidatorArtist();
            Artist artist = new Artist(1, "name");
            try
            {
                validator.validate(artist);
                Assert.IsTrue(true);
            }
            catch (ValidatorException) {
                Assert.IsTrue(false);
            }
        }


        [TestMethod()]
        public void validateInvalidArtistTest()
        {
            ValidatorArtist validator = new ValidatorArtist();
            Artist artist = new Artist(1, "");
            try
            {
                validator.validate(artist);
                Assert.IsTrue(false);
            }
            catch (ValidatorException)
            {
                Assert.IsTrue(true);
            }
        }
    }
}