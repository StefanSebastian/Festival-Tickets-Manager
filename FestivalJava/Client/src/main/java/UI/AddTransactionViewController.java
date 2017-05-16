package UI;

import Controller.ClientController;
import festival.Domain.Artist;
import festival.Domain.Show;
import festival.Domain.ShowArtist;
import festival.Validation.Exceptions.ServiceException;
import festival.Validation.Exceptions.ValidatorException;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Created by Sebi on 19-Mar-17.
 */
public class AddTransactionViewController {
    //controllers
    private ClientController clientController;

    //selected show - for which we buy tickets
    private ShowArtist selectedShow;

    //stage for the current window
    private Stage currentStage;


    /*
    Initialize window
     */
    public void initialize(ClientController clientController,
                           ShowArtist selectedShow,
                           Stage currentStage){
        this.clientController = clientController;

        this.selectedShow = selectedShow;

        this.currentStage = currentStage;


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

            clientController.buyTicketsForShow(selectedShow.getIdShow(), clientName, tickets,
                    clientController.getCurrentUser().getUsername());

            //notify the other windows
            clientController.showUpdated(new Show(selectedShow.getIdShow(),
                    selectedShow.getLocation(),
                    selectedShow.getDate(),
                    selectedShow.getTicketsAvailable() - tickets,
                    selectedShow.getTicketsSold() + tickets,
                    new Artist(selectedShow.getIdArtist(), selectedShow.getArtistName())
                    ));

            //successful transaction
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Operation was successful");
            alert.setContentText("The transaction was registered");
            alert.show();

            //close the current stage
            currentStage.close();
        } catch (ValidatorException | ServiceException exc){
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
