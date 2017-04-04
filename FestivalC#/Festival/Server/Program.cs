using Festival.Repository;
using Festival.Repository.Interface;
using Festival.Service;
using Festival.Service.AppServices;
using Festival.Validation;
using Networking.utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Server
{
    class Program
    {
        static void Main(string[] args)
        {
            IUserRepository repoUser = new UserDbRepository();
            IArtistRepository repoArtist = new ArtistDbRepository();
            IShowRepository repoShow = new ShowDbRepository();
            ITransactionRepository repoTransactin = new TransactionDbRepository();

            IServiceUser serviceUser = new ServiceUser(repoUser, new ValidatorUser());
            IServiceArtist serviceArtist = new ServiceArtist(repoArtist, new ValidatorArtist());
            IServiceShow serviceShow = new ServiceShow(repoShow, new ValidatorShow());
            IServiceTransaction serviceTransaction = new ServiceTransaction(repoTransactin, new ValidatorTransaction());

            IFestivalServer serverController = new ServerController(serviceArtist, serviceShow, 
                serviceTransaction, serviceUser);

            SerialFestivalServer serialServer = new SerialFestivalServer("127.0.0.1", 55555, serverController);
            serialServer.Start();
        }
    }
}
