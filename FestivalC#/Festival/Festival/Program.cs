using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using Festival.Repository;
using Festival.Model;
using Festival.Repository.Interface;
using Festival.Service;
using Festival.Validation;
using Festival.Controller;
using Festival.UI;

namespace Festival
{
    static class Program
    {
        static void Main()
        {
            IUserRepository repoUser = new UserDbRepository();
            IArtistRepository repoArtist = new ArtistDbRepository();
            IShowRepository repoShow = new ShowDbRepository();
            ITransactionRepository repoTransaction = new TransactionDbRepository();

            IServiceUser serviceUser = new ServiceUser(repoUser, new ValidatorUser());
            IServiceArtist serviceArtist = new ServiceArtist(repoArtist, new ValidatorArtist());
            IServiceShow serviceShow = new ServiceShow(repoShow, new ValidatorShow());
            IServiceTransaction serviceTransaction = new ServiceTransaction(repoTransaction, new ValidatorTransaction());

            ControllerApp controllerApp = new ControllerApp(serviceUser, serviceArtist, serviceShow, serviceTransaction);

            Application.Run(new Login(controllerApp));
        }
    }
}
