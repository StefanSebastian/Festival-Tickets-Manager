package UI;

import Controller.ControllerArtist;
import Controller.ControllerShow;
import Controller.ControllerTransaction;
import Domain.Artist;
import Domain.Show;
import Domain.ShowArtist;
import Validation.Exceptions.FormatException;
import Validation.Exceptions.UIException;
import Validation.Exceptions.ValidatorException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import javafx.util.converter.DateStringConverter;

import java.io.Console;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebi on 18-Mar-17.
 */
public class MainWindowViewController {
    //controllers
    private ControllerArtist controllerArtist;
    private ControllerShow controllerShow;
    private ControllerTransaction controllerTransaction;

    //data source
    private ObservableList<Artist> artists;
    private ObservableList<Show> shows;
    private ObservableList<ShowArtist> searchList;

    /*
    Init method
     */
    public void initialize(ControllerArtist controllerArtist,
                           ControllerShow controllerShow,
                           ControllerTransaction controllerTransaction){
        this.controllerArtist = controllerArtist;
        this.controllerShow = controllerShow;
        this.controllerTransaction = controllerTransaction;

        //set items for the list of artists
        artists = FXCollections.observableArrayList(controllerArtist.getAll());
        listViewArtists.setItems(artists);
        listViewArtists.getSelectionModel().selectedItemProperty().addListener(artistSelectionChanged());

        //init tables
        locationColumn.setCellValueFactory(new PropertyValueFactory<Show, String>("location"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Show, String>("date"));
        ticketsAvailableColumn.setCellValueFactory(new PropertyValueFactory<Show, Integer>("ticketsAvailable"));
        ticketsSoldColumn.setCellValueFactory(new PropertyValueFactory<Show, Integer>("ticketsSold"));

        searchTableArtistColumn.setCellValueFactory(new PropertyValueFactory<ShowArtist, String>("artistName"));
        searchTableLocationColumn.setCellValueFactory(new PropertyValueFactory<ShowArtist, String>("location"));
        searchTableDateColumn.setCellValueFactory(new PropertyValueFactory<ShowArtist, String>("date"));
        searchTableTicketsAvailableColumn.setCellValueFactory(new PropertyValueFactory<ShowArtist, Integer>("ticketsAvailable"));
        searchTableTicketsSoldColumn.setCellValueFactory(new PropertyValueFactory<ShowArtist, Integer>("ticketsSold"));

        datePicker.setConverter(getStringConverter());

        ticketsSlider.valueProperty().addListener(ticketsSliderValueChanged());
        ticketsSlider.setBlockIncrement(1);
    }

    @FXML
    private ListView<Artist> listViewArtists;
    @FXML
    private TableView<Show> tableViewShows;
    @FXML
    private TableColumn<Show, String> locationColumn;
    @FXML
    private TableColumn<Show, String> dateColumn;
    @FXML
    private TableColumn<Show, Integer> ticketsAvailableColumn;
    @FXML
    private TableColumn<Show, Integer> ticketsSoldColumn;

    @FXML
    private TableView<ShowArtist> searchTable;
    @FXML
    private TableColumn<ShowArtist, String> searchTableLocationColumn;
    @FXML
    private TableColumn<ShowArtist, String> searchTableDateColumn;
    @FXML
    private TableColumn<ShowArtist, Integer> searchTableTicketsAvailableColumn;
    @FXML
    private TableColumn<ShowArtist, Integer> searchTableTicketsSoldColumn;
    @FXML
    private TableColumn<ShowArtist, String> searchTableArtistColumn;
    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField clientNameTextField;
    @FXML
    private TextField ticketsTextField;
    @FXML
    private Slider ticketsSlider;
    @FXML
    private Button addTransactionButton;

    /*
    When selection changes in artist list, the shows table is updated
     */
    private ChangeListener<Artist> artistSelectionChanged(){
        return (observable, oldValue, newValue) -> {
            Artist artist = listViewArtists.getSelectionModel().getSelectedItem();
            if (artist == null){
                return;
            }

            shows = FXCollections.observableArrayList(
                    controllerShow.getShowsForArtist(artist.getIdArtist().toString()));
            tableViewShows.setItems(shows);
        };
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

    /*
    Get all shows in a given date and display them in search table
     */
    @FXML
    private void getShowsForDate(){
        List<Show> showList  =controllerShow.getShowsForDate(datePicker.getEditor().getText());
        List<ShowArtist> showsArtists = new ArrayList<>();

        //gets all shows for the given date
        //gets all artists for those shows
        for (Show s : showList){
            Artist artist = null;
            try {
                artist = controllerArtist.getById(s.getIdArtist().toString());
            } catch (FormatException e) {
                e.printStackTrace();
            }
            showsArtists.add(new ShowArtist(s.getIdShow(),
                    s.getLocation(), s.getDate(), s.getTicketsAvailable(),
                    s.getTicketsSold(), artist.getIdArtist(), artist.getName()));
        }

        //displays info in search table
        searchList = FXCollections.observableList(showsArtists);
        searchTable.setItems(searchList);
    }

    /*
    A string converter used to change date picker format
     */
    StringConverter<LocalDate> getStringConverter(){
        return new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd");
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
    }

    @FXML
    private void addTransactionButtonPressed(){
        try{
            //get the selected show
            ShowArtist selectedShow = searchTable.getSelectionModel().getSelectedItem();
            if (selectedShow == null){
                throw new UIException("You must select a show.");
            }

            //get name and tickets
            String clientName = clientNameTextField.getText();
            String numberOfTickets = ticketsTextField.getText();

            //check availability
            if (selectedShow.getTicketsAvailable() < Integer.parseInt(numberOfTickets)){
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

        } catch (UIException | ValidatorException | FormatException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Operation could not be done.");
            alert.setContentText(exc.getMessage());
            alert.show();
        } catch (NumberFormatException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Operation could not be done.");
            alert.setContentText("Number of tickets must be a number");
            alert.show();
        }
    }
}
