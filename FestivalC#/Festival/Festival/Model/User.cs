using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Festival.Model
{
    public class User
    {
        private String username;
        private String password;

        //constructor
        public User(String username, String password)
        {
            this.username = username;
            this.password = password;
        }

        public string Username
        {
            get
            {
                return username;
            }

            set
            {
                username = value;
            }
        }

        public string Password
        {
            get
            {
                return password;
            }

            set
            {
                password = value;
            }
        }

        public override bool Equals(object obj)
        {
            if (obj == null){
                return false;
            }
            if (!(obj is User))
            {
                return false;
            }
            User u = (User)obj;
            return u.Username.Equals(Username) && u.Password.Equals(Password);
        }

        public override string ToString()
        {
            return username + " " + password;
        }

        public override int GetHashCode()
        {
            return base.GetHashCode();
        }
    }
}
