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
import UI.MainWindowViewController;
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

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Sebi on 09-Mar-17.
 */
public class Main extends Application{

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        ApplicationContext factory =
                new ClassPathXmlApplicationContext("classpath:festivalApp.xml");

        ControllerArtist controllerArtist = factory.getBean(ControllerArtist.class);
        ControllerShow controllerShow = factory.getBean(ControllerShow.class);
        ControllerTransaction controllerTransaction = factory.getBean(ControllerTransaction.class);
        ControllerUser controllerUser = factory.getBean(ControllerUser.class);


        //window1

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("UI/LoginView.fxml"));
        AnchorPane loginPane = loader.load();
        LoginViewController controller = loader.getController();
        controller.initialize(controllerUser,
                controllerArtist, controllerShow, controllerTransaction, primaryStage);

        Scene scene = new Scene(loginPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Festival tickets");
        primaryStage.show();



        //window 2
       /* Stage stage1 = new Stage();
        FXMLLoader loader1 = new FXMLLoader(Main.class.getResource("UI/LoginView.fxml"));
        AnchorPane loginPane1 = loader1.load();
        LoginViewController controller1 = loader1.getController();
        controller1.initialize(controllerUser,
                controllerArtist, controllerShow, controllerTransaction, stage1);

        Scene scene1 = new Scene(loginPane1);

        stage1.setScene(scene1);
        stage1.show(); */

    }
}
