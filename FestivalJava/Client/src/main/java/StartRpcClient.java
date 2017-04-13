import AppServices.IFestivalServer;
import Controller.ClientController;
import Domain.Artist;
import UI.LoginViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Sebi on 29-Mar-17.
 */
public class StartRpcClient extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        try{
            ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:client-spring.xml");
            IFestivalServer server = (IFestivalServer)factory.getBean("festivalServer");
            ClientController clientController = new ClientController(server);


            FXMLLoader loader = new FXMLLoader(StartRpcClient.class.getResource("UI/LoginView.fxml"));
            AnchorPane loginPane = loader.load();
            LoginViewController controller = loader.getController();
            controller.initialize(clientController, primaryStage);

            Scene scene = new Scene(loginPane);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Festival tickets");
            primaryStage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
