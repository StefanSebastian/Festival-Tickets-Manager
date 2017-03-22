package UI;

import Controller.ControllerShow;
import Controller.ControllerTransaction;
import Domain.ShowArtist;
import Validation.Exceptions.FormatException;
import Validation.Exceptions.UIException;
import Validation.Exceptions.ValidatorException;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Created by Sebi on 19-Mar-17.
 */
public class AddTransactionViewController {
    //controllers
    private ControllerTransaction controllerTransaction;
    private ControllerShow controllerShow;

    //selected show
    private ShowArtist selectedShow;

    public void initialize(ControllerTransaction controllerTransaction,
                           ControllerShow controllerShow,
                           ShowArtist selectedShow){
        this.controllerTransaction = controllerTransaction;
        this.controllerShow = controllerShow;

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

            //check availability
            if (selectedShow.getTicketsAvailable() < Integer.parseInt(numberOfTickets)) {
                throw new UIException("There aren't enough tickets");
            }

            //save transaction
            controllerTransaction.saveWithoutId("1", clientName, numberOfTickets, selectedShow.getIdShow().toString());

            //update available tickets
            Integer newAvailable = selectedShow.getTicketsAvailable() - Integer.parseInt(numberOfTickets);
            Integer newSold = selectedShow.getTicketsSold() + Integer.parseInt(numberOfTickets);
            controllerShow.update(selectedShow.getIdShow().toString(),
                    selectedShow.getIdShow().toString(), selectedShow.getLocation(),
                    selectedShow.getDate(), newAvailable.toString(), newSold.toString(),
                    selectedShow.getIdArtist().toString()
            );

            //successful transaction
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Operation was successful");
            alert.setContentText("The transaction was registered");
            alert.show();
        } catch (ValidatorException | FormatException | UIException exc){
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
