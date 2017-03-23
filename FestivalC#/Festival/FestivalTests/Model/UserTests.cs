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
    public class UserTests
    {
        [TestMethod()]
        public void UserTest()
        {
            User user = new User("user", "pass");
            Assert.AreEqual(user.Username, "user");
        }

        [TestMethod()]
        public void EqualsTest()
        {
            User user = new User("user", "pass");
            User user1 = new User("user", "pass");
            Assert.AreEqual(user, user1);
        }

        [TestMethod()]
        public void ToStringTest()
        {
            User user = new User("user", "pass");
            Assert.AreEqual(user.ToString(), "user pass");
        }
    }
}