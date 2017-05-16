import festival.AppServices.IFestivalServer;
import Controller.ClientController;
import UI.LoginViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import rpcprotocol.FestivalServerRpcProxy;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Sebi on 29-Mar-17.
 */
public class StartRpcClient extends Application {
    //default port
    private static int defaultPort = 55555;

    //default server
    private static String defaultServer = "localhost";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Properties clientProperties = new Properties();

        try{
            clientProperties.load(StartRpcClient.class.getResourceAsStream("/client.properties"));
        } catch (IOException e) {
            System.err.println("Can't find properties file");
            return;
        }

        String serverIp = clientProperties.getProperty("server.host");
        int serverPort = defaultPort;
        try{
            serverPort = Integer.parseInt(clientProperties.getProperty("server.port"));
        } catch (NumberFormatException ex){
            System.out.println("Invalid port. Using default instead.");
        }

        System.out.println("Using ip " + serverIp);
        System.out.println("Using port " + serverPort);

        IFestivalServer server = new FestivalServerRpcProxy(serverIp, serverPort);
        ClientController clientController = new ClientController(server);

        FXMLLoader loader = new FXMLLoader(StartRpcClient.class.getResource("UI/LoginView.fxml"));
        AnchorPane loginPane = loader.load();
        LoginViewController controller = loader.getController();
        controller.initialize(clientController, primaryStage);

        Scene scene = new Scene(loginPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Festival tickets");
        primaryStage.show();

    }
}
