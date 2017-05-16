import festival.Interfaces.IArtistRepository;
import festival.Interfaces.IShowRepository;
import festival.Interfaces.ITransactionRepository;
import festival.Interfaces.IUserRepository;
import festival.JDBC.RepositoryArtistDB;
import festival.JDBC.RepositoryShowDB;
import festival.JDBC.RepositoryTransactionDB;
import festival.JDBC.RepositoryUserDB;
import festival.AppServices.IFestivalServer;
import festival.ModelServices.Interfaces.IServiceArtist;
import festival.ModelServices.Interfaces.IServiceShow;
import festival.ModelServices.Interfaces.IServiceTransaction;
import festival.ModelServices.Interfaces.IServiceUser;
import festival.ModelServices.ServiceArtist;
import festival.ModelServices.ServiceShow;
import festival.ModelServices.ServiceTransaction;
import festival.ModelServices.ServiceUser;
import festival.Validation.ValidatorArtist;
import festival.Validation.ValidatorShow;
import festival.Validation.ValidatorTransaction;
import festival.Validation.ValidatorUser;
import utils.AbstractServer;
import utils.FestivalRpcConcurrentServer;
import utils.ServerException;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Sebi on 29-Mar-17.
 */
public class StartRpcServer {

    //default port
    private static int defaultPort = 55555;

    public static void main(String[] args){
        Properties serverProperties = new Properties();
        try{
            serverProperties.load(StartRpcServer.class.getResourceAsStream("/server.properties"));
        } catch (IOException e) {
            System.err.println("Can't find the properties file");
            return;
        }

        IUserRepository userRepository = new RepositoryUserDB(serverProperties);
        IArtistRepository artistRepository = new RepositoryArtistDB(serverProperties);
        IShowRepository showRepository = new RepositoryShowDB(serverProperties);
        ITransactionRepository transactionRepository = new RepositoryTransactionDB(serverProperties);
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

        AbstractServer server = new FestivalRpcConcurrentServer(chatServerPort, festivalServer);
        try{
            server.start();
        } catch (ServerException e) {
            System.out.println("Error starting server");
        }
    }
}
