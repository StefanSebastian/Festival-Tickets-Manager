using Festival.Model;
using Festival.Repository.Interface;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Festival.Repository
{
    public class UserDbRepository : IUserRepository
    {
        public void delete(string id)
        {
            var connection = DbUtils.getConnection();
            using (var command = connection.CreateCommand())
            {
                command.CommandText = "delete from users where username=@id";
                var paramId = command.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                command.Parameters.Add(paramId);

                try
                {
                    command.ExecuteNonQuery();
                }
                catch (MySqlException e)
                {
                    Console.Out.WriteLine(e);
                }
            }
        }

        public List<User> getAll()
        {
            List<User> users = new List<User>();
            var connection = DbUtils.getConnection();
            using (var command = connection.CreateCommand())
            {
                command.CommandText = "select * from users";
                using (var dataR = command.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        String username = dataR.GetString(0);
                        String password = dataR.GetString(1);
                        users.Add(new User(username, password));
                    }
                }
            }
            return users;
        }

        public User getById(string id)
        {
            var connection = DbUtils.getConnection();
            using (var command = connection.CreateCommand())
            {
                command.CommandText = "select * from users where username=@id";
                var paramId = command.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                command.Parameters.Add(paramId);

                using (var dataR = command.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        String username = dataR.GetString(0);
                        String password = dataR.GetString(1);
                        return new User(username, password);
                    }
                }
            }
            return null;
        }

        public User getUserFor(string username, string password)
        {
            var connection = DbUtils.getConnection();
            using (var command = connection.CreateCommand())
            {
                command.CommandText = "select * from users where username=@user and password=@pass";
                var paramUser = command.CreateParameter();
                paramUser.ParameterName = "@user";
                paramUser.Value = username;
                command.Parameters.Add(paramUser);

                var paramPass = command.CreateParameter();
                paramPass.ParameterName = "@pass";
                paramPass.Value = password;
                command.Parameters.Add(paramPass);

                using (var dataR = command.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        String user = dataR.GetString(0);
                        String pass = dataR.GetString(1);
                        return new User(user, pass);
                    }
                }
            }
            return null;
        }

        public void save(User user)
        {
            var connection = DbUtils.getConnection();

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "insert into users values(@uname, @pass)";

                var paramUname = command.CreateParameter();
                paramUname.ParameterName = "@uname";
                paramUname.Value = user.Username;
                command.Parameters.Add(paramUname);

                var paramPass = command.CreateParameter();
                paramPass.ParameterName = "@pass";
                paramPass.Value = user.Password;
                command.Parameters.Add(paramPass);
                try
                {
                    command.ExecuteNonQuery();
                }
                catch (MySqlException e)
                {
                    Console.Out.WriteLine(e);
                }
            }
        }

        public void update(string id, User user)
        {
            var connection = DbUtils.getConnection();

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "update users set username=@uname, password=@pass where username=@id";

                var paramUname = command.CreateParameter();
                paramUname.ParameterName = "@uname";
                paramUname.Value = user.Username;
                command.Parameters.Add(paramUname);

                var paramPass = command.CreateParameter();
                paramPass.ParameterName = "@pass";
                paramPass.Value = user.Password;
                command.Parameters.Add(paramPass);

                var paramId = command.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                command.Parameters.Add(paramId);
                try
                {
                    command.ExecuteNonQuery();
                }
                catch (MySqlException e)
                {
                    Console.Out.WriteLine(e);
                }
            }
        }
    }
}
