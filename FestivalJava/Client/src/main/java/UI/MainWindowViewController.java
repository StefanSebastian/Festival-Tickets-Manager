package UI;

import Controller.*;
import Domain.Artist;
import Domain.Show;
import Domain.ShowArtist;
import Validation.Exceptions.ServiceException;
import Validation.Exceptions.UIException;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebi on 18-Mar-17.
 */
public class MainWindowViewController {
    //controllers
    private ClientController clientController;

    //data source
    private ObservableList<Artist> artists;
    private ObservableList<Show> shows;
    private ObservableList<ShowArtist> searchList;

    //main stage
    private Stage currentStage;

    /*
    Init method
     */
    public void initialize(ClientController clientController,
                           Stage primaryStage){
        this.clientController = clientController;
        this.currentStage = primaryStage;

        //set items for the list of artists
        artists = FXCollections.observableArrayList(clientController.getArtists());
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
    private Button logoutButton;

    /*
    Cell factory, used to color the ticketsAvailable cell with red when the value is 0
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
                    } else {
                        setTextFill(Color.BLACK);
                        setStyle("-fx-background-color: white");
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
                    } else {
                        setTextFill(Color.BLACK);
                        setStyle("-fx-background-color: white");
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
                    } else {
                        setTextFill(Color.BLACK);
                        setStyle("-fx-background-color: white");
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
     */
    private Callback<TableColumn<ShowArtist, String>,
            TableCell<ShowArtist, String>> getDateCellFactory(){
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
                    } else {
                        setTextFill(Color.BLACK);
                        setStyle("-fx-background-color: white");
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
    Cell factory, used to color the artist cell with red when the value of ticketsAvailable is 0
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
                    } else {
                        setTextFill(Color.BLACK);
                        setStyle("-fx-background-color: white");
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
    Listener for list selection , in artists list
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
        Artist artist = listViewArtists.getSelectionModel().getSelectedItem();
        if (artist == null){
            return;
        }

        shows = FXCollections.observableArrayList(
                clientController.getShowsForArtist(artist.getIdArtist()));
        tableViewShows.setItems(shows);
    }

    /*
    Get all shows in a given date and display them in search table
     */
    @FXML
    private void getShowsForDate(){
        List<Show> showList  = clientController.getShowsForDate(datePicker.getEditor().getText());
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

            FXMLLoader loader = new FXMLLoader(MainWindowViewController.class.getResource("AddTransactionView.fxml"));
            AnchorPane addPane = loader.load();
            AddTransactionViewController controller = loader.getController();
            controller.initialize(clientController, selectedShow);

            Stage stage = new Stage();
            Scene scene = new Scene(addPane);
            stage.setScene(scene);
            stage.setTitle("Add transaction");
            stage.show();
        } catch (IOException | UIException e){
        }
    }

    /*
    Called when the domain entities update
     */
    public void update() {
        loadArtistShows(); //reloads shows for artist
        getShowsForDate(); //reloads shows in search list
    }

    /*
    Log-out
     */
    @FXML
    private void logOut(){
        try {
            clientController.logout();

            Stage stage = new Stage();
            currentStage.close();

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

}
