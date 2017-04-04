using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Networking.utils
{
    public abstract class ConcurrentServer : AbstractServer
    {
        //constructor
        public ConcurrentServer(string host, int port) : base(host, port) { }

        //process a client request by creating a new thread
        public override void processRequest(TcpClient client)
        {
            Thread t = createWorker(client);
            t.Start();
        }

        //creates a new thread
        protected abstract Thread createWorker(TcpClient client);
    }
}
