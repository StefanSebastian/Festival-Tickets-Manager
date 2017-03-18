import Controller.ControllerArtist;
import Controller.ControllerShow;
import Controller.ControllerTransaction;
import Controller.ControllerUser;
import Domain.Artist;
import Domain.Show;
import Domain.Transaction;
import Domain.User;
import Repository.Interfaces.IDatabaseRepository;
import Repository.RepositoryArtistDB;
import Repository.RepositoryShowDB;
import Repository.RepositoryTransactionDB;
import Repository.RepositoryUserDB;
import UI.LoginViewController;
import Validation.ValidatorArtist;
import Validation.ValidatorShow;
import Validation.ValidatorTransaction;
import Validation.ValidatorUser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Sebi on 09-Mar-17.
 */
public class Main extends Application{

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Properties serverProperties = new Properties();
        try {
            serverProperties.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Can not find bd.config");
        }

        IDatabaseRepository<Transaction, Integer> repoTransaction = new RepositoryTransactionDB(serverProperties);
        ControllerTransaction controllerTransaction =
                new ControllerTransaction(repoTransaction, new ValidatorTransaction());
        IDatabaseRepository<Artist, Integer> repoArtist = new RepositoryArtistDB(serverProperties);
        ControllerArtist controllerArtist = new ControllerArtist(repoArtist, new ValidatorArtist());
        IDatabaseRepository<Show, Integer> repoShow = new RepositoryShowDB(serverProperties);
        ControllerShow controllerShow = new ControllerShow(repoShow, new ValidatorShow());
        IDatabaseRepository<User, Integer> repoUser = new RepositoryUserDB(serverProperties);
        ControllerUser controllerUser = new ControllerUser(repoUser, new ValidatorUser());

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("UI/LoginView.fxml"));
        AnchorPane loginPane = loader.load();
        LoginViewController controller = loader.getController();
        controller.initialize(controllerUser);

        Scene scene = new Scene(loginPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Festival tickets");
        primaryStage.show();
    }
}
