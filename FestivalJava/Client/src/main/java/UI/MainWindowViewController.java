package UI;

import Controller.*;
import festival.Domain.Artist;
import festival.Domain.Show;
import festival.Domain.ShowArtist;
import ObserverPattern.Observer;
import festival.Validation.Exceptions.ServiceException;
import festival.Validation.Exceptions.UIException;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sebi on 18-Mar-17.
 */
public class MainWindowViewController implements Observer<Show> {
    //controllers
    private ClientController clientController;

    //data source
    private ObservableList<Artist> artists;
    private ObservableList<Show> shows;
    private ObservableList<ShowArtist> searchList;

    //main stage
    private Stage currentStage;

    /*
    Initialize window method
     */
    public void initialize(ClientController clientController,
                           Stage primaryStage){
        this.clientController = clientController;
        this.currentStage = primaryStage;

        //observer for server and other UI components notifications
        clientController.addObserver(this);

        //set items for the list of artists
        try {
            artists = FXCollections.observableArrayList(clientController.getArtists());
        } catch (ServiceException e) { //if we cant load the artist list 
            showAlert(e.getMessage());
            logOut();
        }
        listViewArtists.setItems(artists);
        listViewArtists.getSelectionModel().selectedItemProperty().addListener(artistSelectionChanged());

        //init tables
        locationColumn.setCellValueFactory(new PropertyValueFactory<Show, String>("location"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Show, Date>("date"));
        ticketsAvailableColumn.setCellValueFactory(new PropertyValueFactory<Show, Integer>("ticketsAvailable"));
        ticketsSoldColumn.setCellValueFactory(new PropertyValueFactory<Show, Integer>("ticketsSold"));
        //date settings
        dateColumn.setCellFactory(getDateNoColorCellFactory());

        searchTableArtistColumn.setCellValueFactory(new PropertyValueFactory<ShowArtist, String>("artistName"));
        searchTableLocationColumn.setCellValueFactory(new PropertyValueFactory<ShowArtist, String>("location"));
        searchTableDateColumn.setCellValueFactory(new PropertyValueFactory<ShowArtist, Date>("date"));
        searchTableTicketsAvailableColumn.setCellValueFactory(new PropertyValueFactory<ShowArtist, Integer>("ticketsAvailable"));
        searchTableTicketsSoldColumn.setCellValueFactory(new PropertyValueFactory<ShowArtist, Integer>("ticketsSold"));

        datePicker.setConverter(getStringConverter());

        //row color settings
        searchTableTicketsAvailableColumn.setCellFactory(getTicketsAvailableCellFactory());
        searchTableTicketsSoldColumn.setCellFactory(getTicketsSoldCellFactory());
        searchTableLocationColumn.setCellFactory(getLocationCellFactory());
        searchTableDateColumn.setCellFactory(getDateCellFactory());
        searchTableArtistColumn.setCellFactory(getArtistCellFactory());

        //logout
        currentStage.setOnCloseRequest(closeWindowRequest());
      }

      /*
      When the user tries to close the application, he is logged out
       */
      private EventHandler<WindowEvent> closeWindowRequest(){
          return new EventHandler<WindowEvent>() {
              @Override
              public void handle(WindowEvent event) {
                  logOut();
              }
          };
      }

    @FXML
    private ListView<Artist> listViewArtists;  //displays all artists
    @FXML
    private TableView<Show> tableViewShows; //displays shows for the selected artist
    @FXML
    private TableColumn<Show, String> locationColumn;
    @FXML
    private TableColumn<Show, Date> dateColumn;
    @FXML
    private TableColumn<Show, Integer> ticketsAvailableColumn;
    @FXML
    private TableColumn<Show, Integer> ticketsSoldColumn;

    @FXML
    private TableView<ShowArtist> searchTable; // displays the searched shows
    @FXML
    private TableColumn<ShowArtist, String> searchTableLocationColumn;
    @FXML
    private TableColumn<ShowArtist, Date> searchTableDateColumn;
    @FXML
    private TableColumn<ShowArtist, Integer> searchTableTicketsAvailableColumn;
    @FXML
    private TableColumn<ShowArtist, Integer> searchTableTicketsSoldColumn;
    @FXML
    private TableColumn<ShowArtist, String> searchTableArtistColumn;
    @FXML
    private DatePicker datePicker; //date picker for searching by date

    @FXML
    private Button logoutButton; //logout button

    /*
    Cell factory, used to color the ticketsAvailable cell with red when the value is 0
    Also adds the cell clicked event - when a cell in search table is clicked the transaction
    window is opened
     */
    private Callback<TableColumn<ShowArtist, Integer>,
            TableCell<ShowArtist, Integer>> getTicketsAvailableCellFactory(){
        return column -> {
            TableCell<ShowArtist, Integer> cell = new TableCell<ShowArtist, Integer>(){
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) { //If the cell is empty
                        setText(null);
                        setTextFill(null);
                        setStyle("");
                        return;
                    }
                    setText(item.toString()); //Put the String data in the cell

                    //We get here all the info of the show of this row
                    ShowArtist showArtist = getTableView().getItems().get(getIndex());

                    // Color all cells with 0 tickets available
                    if (showArtist.getTicketsAvailable() == 0) {
                        setTextFill(Color.WHITE);
                        setStyle("-fx-background-color: red");
                    } else if (getTableRow().getIndex() % 2 == 0) {
                        setTextFill(Color.BLACK);
                        setStyle("-fx-background-color: white");
                    } else {
                        setTextFill(Color.BLACK);
                        setStyle("-fx-background-color: #F9F9F9");
                    }

                }
            };

            /*
            When a cell is clicked - we open the transaction add view
             */
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()){
                    tableCellClicked();
                }
            });

            return cell;
        };
    }

    /*
    Cell factory, used to color the ticketsSold cell with red when the value of ticketsAvailable is 0
    Also adds the cell clicked event - when a cell in search table is clicked the transaction
    window is opened
     */
    private Callback<TableColumn<ShowArtist, Integer>,
            TableCell<ShowArtist, Integer>> getTicketsSoldCellFactory(){
        return column -> {
            TableCell<ShowArtist, Integer> cell = new TableCell<ShowArtist, Integer>(){
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) { //If the cell is empty
                        setText(null);
                        setTextFill(null);
                        setStyle("");
                        return;
                    }
                    setText(item.toString()); //Put the String data in the cell

                    //We get here all the info of the show of this row
                    ShowArtist showArtist = getTableView().getItems().get(getIndex());

                    // Color all cells with 0 tickets available
                    if (showArtist.getTicketsAvailable() == 0) {
                        setTextFill(Color.WHITE);
                        setStyle("-fx-background-color: red");
                    } else if (getTableRow().getIndex() % 2 == 0) {
                        setTextFill(Color.BLACK);
                        setStyle("-fx-background-color: white");
                    } else {
                        setTextFill(Color.BLACK);
                        setStyle("-fx-background-color: #F9F9F9");
                    }

                }
            };

            /*
            When a cell is clicked - we open the transaction add view
             */
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()){
                    tableCellClicked();
                }
            });

            return cell;
        };
    }

    /*
    Cell factory, used to color the location cell with red when the value of ticketsAvailable is 0
    Also adds the cell clicked event - when a cell in search table is clicked the transaction
    window is opened
     */
    private Callback<TableColumn<ShowArtist, String>,
            TableCell<ShowArtist, String>> getLocationCellFactory(){
        return column -> {
            TableCell<ShowArtist, String> cell = new TableCell<ShowArtist, String>(){
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) { //If the cell is empty
                        setText(null);
                        setTextFill(null);
                        setStyle("");
                        return;
                    }
                    setText(item.toString()); //Put the String data in the cell

                    //We get here all the info of the show of this row
                    ShowArtist showArtist = getTableView().getItems().get(getIndex());

                    // Color all cells with 0 tickets available
                    if (showArtist.getTicketsAvailable() == 0) {
                        setTextFill(Color.WHITE);
                        setStyle("-fx-background-color: red");
                    } else if (getTableRow().getIndex() % 2 == 0) {
                        setTextFill(Color.BLACK);
                        setStyle("-fx-background-color: white");
                    } else {
                        setTextFill(Color.BLACK);
                        setStyle("-fx-background-color: #F9F9F9");
                    }

                }
            };

            /*
            When a cell is clicked - we open the transaction add view
             */
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()){
                    tableCellClicked();
                }
            });

            return cell;
        };
    }

    /*
    Cell factory, used to color the date cell with red when the value of ticketsAvailable is 0
    Also adds the cell clicked event - when a cell in search table is clicked the transaction
    window is opened
     Also formats date value
     */
    private Callback<TableColumn<ShowArtist, Date>,
            TableCell<ShowArtist, Date>> getDateCellFactory(){
        return column -> {
            TableCell<ShowArtist, Date> cell = new TableCell<ShowArtist, Date>(){
                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) { //If the cell is empty
                        setText(null);
                        setTextFill(null);
                        setStyle("");
                        return;
                    }
                    setText(item.toString()); //Put the String data in the cell

                    //We get here all the info of the show of this row
                    ShowArtist showArtist = getTableView().getItems().get(getIndex());

                    // Color all cells with 0 tickets available
                    if (showArtist.getTicketsAvailable() == 0) {
                        setTextFill(Color.WHITE);
                        setStyle("-fx-background-color: red");
                    } else if (getTableRow().getIndex() % 2 == 0) {
                        setTextFill(Color.BLACK);
                        setStyle("-fx-background-color: white");
                    } else {
                        setTextFill(Color.BLACK);
                        setStyle("-fx-background-color: #F9F9F9");
                    }

                    //formats date value
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    setText(df.format(item));
                }
            };

            /*
            When a cell is clicked - we open the transaction add view
             */
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()){
                    tableCellClicked();
                }
            });

            return cell;
        };
    }

    /*
    Cell factory, used to color the artist cell with red when the value of ticketsAvailable is 0
    Also adds the cell clicked event - when a cell in search table is clicked the transaction
    window is opened
     */
    private Callback<TableColumn<ShowArtist, String>,
            TableCell<ShowArtist, String>> getArtistCellFactory(){
        return column -> {
            TableCell<ShowArtist, String> cell = new TableCell<ShowArtist, String>(){
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) { //If the cell is empty
                        setText(null);
                        setTextFill(null);
                        setStyle("");
                        return;
                    }
                    setText(item.toString()); //Put the String data in the cell

                    //We get here all the info of the show of this row
                    ShowArtist showArtist = getTableView().getItems().get(getIndex());

                    // Color all cells with 0 tickets available
                    if (showArtist.getTicketsAvailable() == 0) {
                        setTextFill(Color.WHITE);
                        setStyle("-fx-background-color: red");
                    } else if (getTableRow().getIndex() % 2 == 0) {
                        setTextFill(Color.BLACK);
                        setStyle("-fx-background-color: white");
                    } else {
                        setTextFill(Color.BLACK);
                        setStyle("-fx-background-color: #F9F9F9");
                    }
                }
            };

            /*
            When a cell is clicked - we open the transaction add view
             */
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()){
                    tableCellClicked();
                }
            });

            return cell;
        };
    }

    /*
   Cell factory formats date value
    */
    private Callback<TableColumn<Show, Date>,
            TableCell<Show, Date>> getDateNoColorCellFactory(){
        return column -> {
            TableCell<Show, Date> cell = new TableCell<Show, Date>(){
                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) { //If the cell is empty
                        setText(null);
                        setTextFill(null);
                        setStyle("");
                        return;
                    }

                    //formats date value
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    setText(df.format(item));
                }
            };
            return cell;
        };
    }

    /*
    Listener for list selection, in artists list
    When an artist is selected, loads his shows in shows table
     */
    private ChangeListener<Artist> artistSelectionChanged(){
        return (observable, oldValue, newValue) -> {
            loadArtistShows();
        };
    }

    /*
    Load the shows of the selected artist
     */
    private void loadArtistShows(){
        //get selected artist in listView
        Artist artist = listViewArtists.getSelectionModel().getSelectedItem();
        if (artist == null){
            return;
        }

        //update tableViewShows with the artist's shows
        try {
            shows = FXCollections.observableArrayList(
                    clientController.getShowsForArtist(artist.getIdArtist()));
            tableViewShows.setItems(shows);
            tableViewShows.refresh();
        } catch (ServiceException ex){
            showAlert(ex.getMessage());
        }
    }

    /*
    Get all shows in a given date and display them in search table
     */
    @FXML
    private void getShowsForDate(){
        List<Show> showList  = null;

        //gets all shows for the selected date
        try {
            showList = clientController.getShowsForDate(datePicker.getEditor().getText());
        } catch (ServiceException e) { // we cant load shows
            showAlert(e.getMessage());
            logOut();
        }

        //if the returned value is null
        if (showList == null){
            return;
        }

        //list of showArtists - auxiliary class which contains details of show + it's artist
        //used to map it to a table's columns
        List<ShowArtist> showsArtists = new ArrayList<>();

        //gets all shows for the given date
        //gets all artists for those shows
        for (Show s : showList){
            Artist artist = s.getArtist();

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
    Matches format with the one displayed in tables
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


    /*
    Table cell clicked
    We open a window for adding transactions
     */
    @FXML
    private void tableCellClicked(){
        ShowArtist selectedShow = searchTable.getSelectionModel().getSelectedItem();

        try {
            if (selectedShow == null){
                throw new UIException("You must click on a non empty table row");
            }

            Stage stage = new Stage();

            FXMLLoader loader = new FXMLLoader(MainWindowViewController.class.getResource("AddTransactionView.fxml"));
            AnchorPane addPane = loader.load();
            AddTransactionViewController controller = loader.getController();
            controller.initialize(clientController, selectedShow, stage);

            Scene scene = new Scene(addPane);
            stage.setScene(scene);
            stage.setTitle("Add transaction");
            stage.show();
        } catch (IOException | UIException e){
            showAlert(e.getMessage());
        }
    }



    /*
    Log-out button clicked
     */
    @FXML
    private void logOut(){
        try {
            //windows is no longer observer
            clientController.removeObserver(this);

            //logout request to server
            clientController.logout();

            //close the current stage
            currentStage.close();

            //open a new stage, scene 
            Stage stage = new Stage();

            FXMLLoader loader = new FXMLLoader(MainWindowViewController.class.getResource("LoginView.fxml"));
            AnchorPane loginPane = loader.load();
            LoginViewController controller = loader.getController();
            controller.initialize(clientController, stage);

            Scene scene = new Scene(loginPane);
            stage.setScene(scene);
            stage.show();

        } catch (IOException | ServiceException exc){
            currentStage.close();
        }
    }

    /*
    Displays an alert with the given message
     */
    private void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Application error");
        alert.setContentText(message);
        alert.show();
    }

    /*
    Update all data in UI
     */
    @Override
    public void update() {
    }

    @Override
    public void pushUpdate(Show show) {
        System.out.println("Updating UI");

        //updates shows table
        if (shows != null) {
            if (shows.contains(show)) {
                shows.set(shows.indexOf(show), show); //updates the show
                tableViewShows.setItems(shows);
            }
        }

        //updates search table
        if (searchList != null) {

            for (int i = 0; i < searchList.size(); i++) {
                if (searchList.get(i).getIdShow() == show.getIdShow()) {
                    ShowArtist showArtist = searchList.get(i);
                    showArtist.setTicketsAvailable(show.getTicketsAvailable());
                    showArtist.setTicketsSold(show.getTicketsSold());
                    searchList.set(i, showArtist);
                }
            }
            searchTable.setItems(searchList);
        }

        System.out.println("Finished updating ui");
    }
}
