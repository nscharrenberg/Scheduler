/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.application.ui.addSchedule;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import scheduler.application.RegistryManager;
import scheduler.application.model.GroupSchedule;
import scheduler.application.model.PersonalSchedule;
import scheduler.application.model.Schedule;
import scheduler.application.rmi.interfaces.IUser;
import scheduler.application.ui.personalProjects.personalProjectsController;

/**
 * FXML Controller class
 *
 * @author Noah Scharrenberg
 */
public class AddScheduleController implements Initializable {
    
    private RegistryManager rm;
    private IUser user;
    
    @FXML
    public AnchorPane anchorPane;

    @FXML
    private JFXTextField nameTxt;

    @FXML
    private JFXToggleButton groupToggle;

    @FXML
    private JFXButton submitBtn;

    @FXML
    void saveAction(ActionEvent event) {
        try {
            if(!groupToggle.isSelected()) {
                System.out.println("Account Add: " + rm.getAccount());
                if(user.addPersonalSchedule(rm.getAccount(), nameTxt.getText())) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/scheduler/application/ui/personalProjects/personalProjects.fxml"));
                    Parent root = loader.load();
                    personalProjectsController controller = (personalProjectsController) loader.getController();
                    controller.setup(rm);
                    controller.openSnackbar("Personal Schedule created!", controller.anchorPane, "Close", 10000);
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("Scheduler - Personal Projects");
                    stage.show();
                    closeCurrentStageThroughJFXButton(event);
                }
            }         
        } catch (Exception ex) {
            openSnackbar(ex.getMessage(), anchorPane, "Close", 10000);
        }
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void setup(RegistryManager rm) throws RemoteException {
        this.rm = rm;
        rm.getIUser();
        this.user = rm.getUser();
    }
    
    private void closeCurrentStageThroughJFXButton(Event event) {
        ((Stage)(((JFXButton)event.getSource()).getScene().getWindow())).close();
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
    
}
