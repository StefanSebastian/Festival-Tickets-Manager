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
    public class ValidatorUserTests
    {
        [TestMethod()]
        public void validateTest()
        {
            ValidatorUser validator = new ValidatorUser();
            User user = new User("user", "pass");
            try
            {
                validator.validate(user);
                Assert.IsTrue(true);
            }
            catch (ValidatorException)
            {
                Assert.IsTrue(false);
            }
        }

        [TestMethod()]
        public void validateInvalidUserTest()
        {
            ValidatorUser validator = new ValidatorUser();
            User user = new User("", "pass");
            try
            {
                validator.validate(user);
                Assert.IsTrue(false);
            }
            catch (ValidatorException)
            {
                Assert.IsTrue(true);
            }
        }
    }
}