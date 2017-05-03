import AppServices.IFestivalServer;
import Controller.ClientController;
import UI.LoginViewController;

import protobuf.ProtoFestivalProxy;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Created by Sebi on 25-Apr-17.
 */
public class StartProtobufClient extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        IFestivalServer server = new ProtoFestivalProxy("localhost", 55556);
        ClientController clientController = new ClientController(server);

        FXMLLoader loader = new FXMLLoader(StartProtobufClient.class.getResource("UI/LoginView.fxml"));
        AnchorPane loginPane = loader.load();
        LoginViewController controller = loader.getController();
        controller.initialize(clientController, primaryStage);

        Scene scene = new Scene(loginPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Festival tickets");
        primaryStage.show();
    }
}
