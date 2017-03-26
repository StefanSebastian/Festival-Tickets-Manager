package UI;

import Controller.AppController;
import Domain.ShowArtist;
import Validation.Exceptions.ControllerException;
import Validation.Exceptions.ValidatorException;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Created by Sebi on 19-Mar-17.
 */
public class AddTransactionViewController {
    //controllers
    private AppController appController;

    //selected show
    private ShowArtist selectedShow;

    public void initialize(AppController appController,
                           ShowArtist selectedShow){
        this.appController = appController;

        this.selectedShow = selectedShow;

        //slider settings
        ticketsSlider.valueProperty().addListener(ticketsSliderValueChanged());
        ticketsSlider.setBlockIncrement(1);

        //label
        showLabel.setText("Get tickets for: " +
                selectedShow.getArtistName() + " | " + selectedShow.getLocation()
                + " | " + selectedShow.getDate());
    }

    /*
    When the tickets slider value changes
    Number is mapped to tickets number text fields
    Converts value to integer first
     */
    private ChangeListener<Number> ticketsSliderValueChanged(){
        return (observable, oldValue, newValue) -> {
            Integer value = newValue.intValue();
            ticketsSlider.setValue(value);
            ticketsTextField.setText(value.toString());
        };
    }

    @FXML
    private Label showLabel;
    @FXML
    private Button addTransactionButton;
    @FXML
    private TextField clientNameTextField;
    @FXML
    private TextField ticketsTextField;
    @FXML
    private Slider ticketsSlider;

    /*
    Registers a transaction
     */
    @FXML
    private void addTransactionButtonClicked() {
        try {

            //get name and tickets
            String clientName = clientNameTextField.getText();
            String numberOfTickets = ticketsTextField.getText();
            Integer tickets = Integer.parseInt(numberOfTickets);

            appController.buyTicketsForShow(selectedShow.getIdShow(), clientName, tickets);

            //successful transaction
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Operation was successful");
            alert.setContentText("The transaction was registered");
            alert.show();
        } catch (ValidatorException | ControllerException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Operation could not be done.");
            alert.setContentText(exc.getMessage());
            alert.show();
        } catch (NumberFormatException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Operation could not be done.");
            alert.setContentText("Number of tickets must be integer");
            alert.show();
        }
    }
}
