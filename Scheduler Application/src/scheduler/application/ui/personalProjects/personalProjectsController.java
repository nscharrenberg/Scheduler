/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.application.ui.personalProjects;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXToolbar;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import scheduler.application.RegistryManager;
import scheduler.application.model.Account;
import scheduler.application.rmi.interfaces.IUser;
import scheduler.application.rmi.interfaces.IVisitor;
import scheduler.application.ui.addSchedule.AddScheduleController;

/**
 *
 * @author Noah Scharrenberg
 */
public class personalProjectsController implements Initializable {
    
    private RegistryManager rm;
    private IUser user;
    
    @FXML
    public AnchorPane anchorPane;

    @FXML
    private JFXButton testBtn;

    @FXML
    void createScheduleAction(ActionEvent event) {
        try {
            if(rm.getAccount() != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/scheduler/application/ui/addSchedule/addSchedule.fxml"));
                Parent root = loader.load();
                AddScheduleController controller = (AddScheduleController) loader.getController();
                controller.setup(rm);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Scheduler - Create Personal Project");
                stage.show();
                closeCurrentStageThroughJFXButton(event);
            }
        } catch (Exception ex) {
            openSnackbar(ex.getMessage(), anchorPane, "Close", 10000);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    } 
    
    public void setup(RegistryManager rm) throws RemoteException {
        this.rm = rm;
        rm.getIUser();
        this.user = rm.getUser();
        openSnackbar("Welcome " + rm.getAccount().getUsername(), anchorPane, "Close", 3000);
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
