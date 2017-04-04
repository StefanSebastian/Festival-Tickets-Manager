using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data;
using Festival.ConnectionUtils;

namespace Festival.Repository
{
    public static class DbUtils
    {
        //instance of connection
        private static IDbConnection instance = null;

        //returns connection
        public static IDbConnection getConnection()
        {
            if (instance == null || instance.State == System.Data.ConnectionState.Closed)
            {
                instance = getNewConnection();
                instance.Open();
            }
            return instance;
        }

        //creates a new connection
        private static IDbConnection getNewConnection()
        {
            return MySQLConnectionFactory.getInstance().createConnection();
        }
    }
}
