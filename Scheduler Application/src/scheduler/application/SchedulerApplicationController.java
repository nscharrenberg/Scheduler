/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.application;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXToolbar;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import scheduler.application.ui.personalProjects.personalProjectsController;

/**
 * FXML Controller class
 *
 * @author Noah Scharrenberg
 */
public class SchedulerApplicationController implements Initializable {
    
    @FXML
    private BorderPane borderPane;

    @FXML
    private JFXToolbar toolbarMenu;

    @FXML
    private MenuItem newProjectBtn, personalProjectsBtn, groupProjectsBtn, settingsBtn;

    @FXML
    private JFXDrawer drawerFrame;
    
    @FXML
    void newProjectAction(ActionEvent event) {
        openNewWindowsWithController("/scheduler/application/ui/addSchedule/addSchedule.fxml");
    }
    
    @FXML
    void personalProjectsAction(ActionEvent event) {
        openNewWindowsWithController("/scheduler/application/ui/personalProjects/personalProjects.fxml");
    }

    @FXML
    void groupProjectsAction(ActionEvent event) {
        openNewWindowsWithController("/scheduler/application/ui/groupProjects/groupProjects.fxml");
    }

    @FXML
    void settingsAction(ActionEvent event) {
        openNewWindowsWithController("/scheduler/application/ui/settings/settings.fxml");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        openNewWindowsWithController("/scheduler/application/ui/personalProjects/personalProjects.fxml");
    }  
    
    private void openNewWindowsWithController(String path) {
         try {
            Parent defaultWindow = FXMLLoader.load(getClass().getResource(path));
            drawerFrame.setContent(defaultWindow);
        } catch (IOException ex) {
            Logger.getLogger(SchedulerApplicationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
