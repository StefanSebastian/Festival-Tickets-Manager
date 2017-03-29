package UI;

import Controller.ClientController;
import Validation.Exceptions.ServiceException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Sebi on 18-Mar-17.
 */
public class LoginViewController {
    //controller
    private ClientController clientController;

    //stage
    private Stage currentStage;

    //initialize
    public void initialize(ClientController clientController,
                           Stage primaryStage){
        this.clientController = clientController;
        this.currentStage = primaryStage;
    }

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button loginButton;

    /*
    Handles login
     */
    @FXML
    private void loginButtonClicked(){
        try {
            clientController.login(usernameTextField.getText(),
                    passwordTextField.getText());

            //if successful
            Stage stage = new Stage();
            currentStage.close();

            FXMLLoader loader = new FXMLLoader(LoginViewController.class.getResource("MainWindowView.fxml"));
            AnchorPane mainPane = loader.load();
            MainWindowViewController controller = loader.getController();
            controller.initialize(clientController, stage);

            Scene scene = new Scene(mainPane);
            stage.setScene(scene);
            stage.show();
        } catch (ServiceException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Operation could not be done.");
            alert.setContentText(e.getMessage());
            alert.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Operation could not be done.");
            alert.setContentText("IO exception");
        }

    }
}
