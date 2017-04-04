using MySql.Data.MySqlClient;
using System;
using System.Data;
using System.Data.SqlClient;

namespace Festival.ConnectionUtils
{
    public class MySQLConnectionFactory : ConnectionFactory
    {
        public override IDbConnection createConnection()
        {
            //MySql Connection
            String connectionString = "Database=festival;" +
                                        "Data Source=localhost;" +
                                        "User id=root;" +
                                        "Password=pass;";
            return new MySqlConnection(connectionString);
        }
    }
}
