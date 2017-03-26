package UI;

import Controller.*;
import Domain.User;
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
    private AppController appController;

    //stage
    private Stage primaryStage;

    //initialize
    public void initialize(AppController appController,
                           Stage primaryStage){
        this.appController = appController;
        this.primaryStage = primaryStage;
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
        User succesful = appController.tryLogin(usernameTextField.getText(),
                passwordTextField.getText());
        if (succesful != null){
            //open the main window
            try{
                FXMLLoader loader = new FXMLLoader(LoginViewController.class.getResource("MainWindowView.fxml"));
                AnchorPane mainPane = loader.load();
                MainWindowViewController controller = loader.getController();
                controller.initialize(appController, primaryStage);

                Scene scene = new Scene(mainPane);
                primaryStage.setScene(scene);

            } catch (IOException ex){
                ex.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Operation could not be done.");
            alert.setContentText("Invalid username / password");
            alert.show();
        }
    }
}
