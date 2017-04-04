using Festival.Service.AppServices;
using Networking.rpcprotocol;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net.Sockets;
using System.Threading;

namespace Networking.utils
{
    public class SerialFestivalServer : ConcurrentServer
    {
        private IFestivalServer server;
        private FestivalClientRpcWorker worker;

        public SerialFestivalServer(string host, int port, IFestivalServer server) : base(host, port)
        {
            this.server = server;
        }

        protected override Thread createWorker(TcpClient client)
        {
            worker = new FestivalClientRpcWorker(server, client);
            return new Thread(new ThreadStart(worker.run));
        }
    }
}
