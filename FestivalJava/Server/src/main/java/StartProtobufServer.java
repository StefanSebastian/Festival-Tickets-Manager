import AppServices.IFestivalServer;
import ModelServices.Interfaces.IServiceArtist;
import ModelServices.Interfaces.IServiceShow;
import ModelServices.Interfaces.IServiceTransaction;
import ModelServices.Interfaces.IServiceUser;
import ModelServices.ServiceArtist;
import ModelServices.ServiceShow;
import ModelServices.ServiceTransaction;
import ModelServices.ServiceUser;
import Repository.Hibernate.RepositoryArtistHibernate;
import Repository.Hibernate.RepositoryShowHibernate;
import Repository.Hibernate.RepositoryTransactionHibernate;
import Repository.Hibernate.RepositoryUserHibernate;
import Repository.Interfaces.IArtistRepository;
import Repository.Interfaces.IShowRepository;
import Repository.Interfaces.ITransactionRepository;
import Repository.Interfaces.IUserRepository;
import Repository.JDBC.RepositoryArtistDB;
import Repository.JDBC.RepositoryShowDB;
import Repository.JDBC.RepositoryTransactionDB;
import Repository.JDBC.RepositoryUserDB;
import Validation.ValidatorArtist;
import Validation.ValidatorShow;
import Validation.ValidatorTransaction;
import Validation.ValidatorUser;
import utils.AbstractServer;
import utils.FestivalProtobufConcurrentServer;
import utils.ServerException;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Sebi on 25-Apr-17.
 */
public class StartProtobufServer {

    private static int defaultPort = 55555;

    public static void main(String[] args) {
        Properties serverProperties = new Properties();
        try{
            serverProperties.load(StartProtobufServer.class.getResourceAsStream("/server.properties"));
        } catch (IOException e) {
            System.err.println("Can't find the properties file");
            return;
        }

        IUserRepository userRepository = new RepositoryUserHibernate();
        //IUserRepository userRepository = new RepositoryUserDB(serverProperties);
        IArtistRepository artistRepository = new RepositoryArtistHibernate();
        //IArtistRepository artistRepository = new RepositoryArtistDB(serverProperties);
        IShowRepository showRepository = new RepositoryShowHibernate();
        //IShowRepository showRepository = new RepositoryShowDB(serverProperties);
        ITransactionRepository transactionRepository = new RepositoryTransactionHibernate();
        //ITransactionRepository transactionRepository = new RepositoryTransactionDB(serverProperties);
        IServiceUser serviceUser = new ServiceUser(userRepository, new ValidatorUser());
        IServiceArtist serviceArtist = new ServiceArtist(artistRepository, new ValidatorArtist());
        IServiceShow serviceShow = new ServiceShow(showRepository, new ValidatorShow());
        IServiceTransaction serviceTransaction = new ServiceTransaction(transactionRepository, new ValidatorTransaction());

        IFestivalServer festivalServer = new FestivalServer(serviceArtist, serviceShow, serviceTransaction, serviceUser);

        int chatServerPort = defaultPort;
        try{
            chatServerPort = Integer.parseInt(serverProperties.getProperty("server.port"));
        } catch (NumberFormatException ex){
            System.out.println("Wrong port format. Using default port instead " + defaultPort);
        }

        AbstractServer server = new FestivalProtobufConcurrentServer(chatServerPort, festivalServer);

        try{
            server.start();
        } catch (ServerException e) {
            System.out.println("Error starting server");
        }
    }
}
