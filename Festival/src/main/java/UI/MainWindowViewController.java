package UI;

import Controller.ControllerArtist;
import Controller.ControllerShow;
import Controller.ControllerTransaction;
import Domain.Artist;
import Domain.Show;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

    public void initialize(ControllerArtist controllerArtist,
                           ControllerShow controllerShow,
                           ControllerTransaction controllerTransaction){
        this.controllerArtist = controllerArtist;
        this.controllerShow = controllerShow;
        this.controllerTransaction = controllerTransaction;

        artists = FXCollections.observableArrayList(controllerArtist.getAll());
        listViewArtists.setItems(artists);
        listViewArtists.getSelectionModel().selectedItemProperty().addListener(artistSelectionChanged());

        locationColumn.setCellValueFactory(new PropertyValueFactory<Show, String>("location"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Show, String>("date"));
        ticketsAvailableColumn.setCellValueFactory(new PropertyValueFactory<Show, Integer>("ticketsAvailable"));
        ticketsSoldColumn.setCellValueFactory(new PropertyValueFactory<Show, Integer>("ticketsSold"));
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


    /*
    When selection changes
     */
    ChangeListener<Artist> artistSelectionChanged(){
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


}
