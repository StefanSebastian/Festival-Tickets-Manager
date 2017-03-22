using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data;
using System.Reflection;

namespace Festival.ConnectionUtils
{
    public abstract class ConnectionFactory
    {
        //constructor
        protected ConnectionFactory()
        {

        }

        //instance
        private static ConnectionFactory instance;

        //returns the factory
        public static ConnectionFactory getInstance()
        {
            if (instance == null)
            {
                Assembly assem = Assembly.GetExecutingAssembly();
                Type[] types = assem.GetTypes();
                foreach (Type t in types)
                {
                    if (t.IsSubclassOf(typeof(ConnectionFactory)))
                        instance = (ConnectionFactory)Activator.CreateInstance(t);
                }
            }
            return instance;
        }

        //connection create method
        public abstract IDbConnection createConnection();
    }
}
