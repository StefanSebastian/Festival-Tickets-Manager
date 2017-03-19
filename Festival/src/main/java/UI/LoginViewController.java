package UI;

import Controller.ControllerArtist;
import Controller.ControllerShow;
import Controller.ControllerTransaction;
import Controller.ControllerUser;
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
    private ControllerUser controllerUser;
    private ControllerArtist controllerArtist;
    private ControllerTransaction controllerTransaction;
    private ControllerShow controllerShow;

    //stage
    private Stage primaryStage;

    //initialize
    public void initialize(ControllerUser controllerUser,
                           ControllerArtist controllerArtist,
                           ControllerShow controllerShow,
                           ControllerTransaction controllerTransaction,
                           Stage primaryStage){
        this.controllerUser = controllerUser;
        this.controllerArtist = controllerArtist;
        this.controllerShow = controllerShow;
        this.controllerTransaction = controllerTransaction;
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
        boolean succesful = controllerUser.login(usernameTextField.getText(),
                passwordTextField.getText());
        if (succesful){
            //open the main window
            try{
                FXMLLoader loader = new FXMLLoader(LoginViewController.class.getResource("MainWindowView.fxml"));
                AnchorPane loginPane = loader.load();
                MainWindowViewController controller = loader.getController();
                controller.initialize(controllerUser,
                        controllerArtist, controllerShow,
                        controllerTransaction, primaryStage);

                Scene scene = new Scene(loginPane);
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
