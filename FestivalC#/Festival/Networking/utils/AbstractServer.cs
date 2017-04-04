using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace Networking.utils
{
    public abstract class AbstractServer
    {
        private TcpListener server;
        private string host;
        private int port;

        public AbstractServer(string host, int port)
        {
            this.host = host;
            this.port = port;
        }

        public void Start()
        {
            IPAddress ip = IPAddress.Parse(host);
            IPEndPoint ep = new IPEndPoint(ip, port);
            server = new TcpListener(ep);
            server.Start();

            while (true)
            {
                Console.WriteLine("Waiting for clients");
                TcpClient client = server.AcceptTcpClient();
                Console.WriteLine("Client connected");
                processRequest(client);
            }
        }

        public abstract void processRequest(TcpClient client);
    }
}
