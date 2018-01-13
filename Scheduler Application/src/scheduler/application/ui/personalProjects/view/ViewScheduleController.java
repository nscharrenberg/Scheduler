/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.application.ui.personalProjects.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import scheduler.application.RegistryManager;
import scheduler.application.rmi.interfaces.IUser;

/**
 * FXML Controller class
 *
 * @author Noah Scharrenberg
 */
public class ViewScheduleController implements Initializable {
    private RegistryManager rm;
    private IUser user;
    
     @FXML
    public AnchorPane anchorPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    public void setup(RegistryManager rm, int Schedule) throws RemoteException {
        this.rm = rm;
        rm.getIUser();
        this.user = rm.getUser();
    }
    
    public void openSnackbar(String msg, Pane pane, String btnTxt, int longtime) {
        JFXSnackbar notification = new JFXSnackbar(pane);
        EventHandler eh = new EventHandler() {
            @Override
            public void handle(Event event) {
                notification.unregisterSnackbarContainer(pane);
            }
        };
        notification.show(msg, btnTxt, longtime, eh);
    }
    
    private void closeCurrentStageThroughJFXButton(Event event) {
        ((Stage)(((JFXButton)event.getSource()).getScene().getWindow())).close();
    }
    
}
