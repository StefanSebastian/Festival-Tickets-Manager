package UI;

import Controller.ControllerArtist;
import Controller.ControllerShow;
import Controller.ControllerTransaction;

/**
 * Created by Sebi on 18-Mar-17.
 */
public class MainWindowViewController {
    //controllers
    private ControllerArtist controllerArtist;
    private ControllerShow controllerShow;
    private ControllerTransaction controllerTransaction;

    public void initialize(ControllerArtist controllerArtist,
                           ControllerShow controllerShow,
                           ControllerTransaction controllerTransaction){
        this.controllerArtist = controllerArtist;
        this.controllerShow = controllerShow;
        this.controllerTransaction = controllerTransaction;
    }
}
