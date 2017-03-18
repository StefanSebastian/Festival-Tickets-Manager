package UI;

import Controller.ControllerArtist;
import Controller.ControllerShow;
import Controller.ControllerTransaction;
import Domain.Artist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

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

    public void initialize(ControllerArtist controllerArtist,
                           ControllerShow controllerShow,
                           ControllerTransaction controllerTransaction){
        this.controllerArtist = controllerArtist;
        this.controllerShow = controllerShow;
        this.controllerTransaction = controllerTransaction;

        artists = FXCollections.observableArrayList(controllerArtist.getAll());
        listViewArtists.setItems(artists);
    }

    @FXML
    private ListView<Artist> listViewArtists;


}
