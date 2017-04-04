using Festival.Service.AppServices;
using Networking.rpcprotocol;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Client
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);

            IFestivalServer server = new FestivalServerRpcProxy("127.0.0.1", 55555);
            ClientController ctrl = new ClientController(server);
            LoginWindow login = new LoginWindow(ctrl);
            Application.Run(login);
        }
    }
}
