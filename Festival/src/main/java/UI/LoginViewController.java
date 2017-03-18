package UI;

import Controller.ControllerUser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Created by Sebi on 18-Mar-17.
 */
public class LoginViewController {
    //controller
    private ControllerUser controllerUser;

    //initialize
    public void initialize(ControllerUser controllerUser){
        this.controllerUser = controllerUser;
    }

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button loginButton;

    @FXML
    private void loginButtonClicked(){
        boolean succesful = controllerUser.login(usernameTextField.getText(),
                passwordTextField.getText());
        if (succesful){
            System.out.println("succes");
        } else {
            System.out.println("fail");
        }
    }
}
