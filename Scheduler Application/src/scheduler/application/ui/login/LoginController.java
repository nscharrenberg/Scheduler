/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.application.ui.login;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import scheduler.application.RegistryManager;
import scheduler.application.model.Account;
import scheduler.application.rmi.interfaces.IVisitor;
import scheduler.application.ui.personalProjects.personalProjectsController;

/**
 * FXML Controller class
 *
 * @author Noah Scharrenberg
 */
public class LoginController implements Initializable {
    
    private RegistryManager rm;
    private Account account;
    private IVisitor visitor;
    
    @FXML
    public AnchorPane anchorPane;
    
    @FXML
    private JFXTextField usernameTxt;

    @FXML
    private JFXTextField passwordTxt;

    @FXML
    private JFXButton loginBtn;

    @FXML
    private JFXButton registerBtn;

    @FXML
    private JFXButton forgotBtn;

    @FXML
    void forgotAction(MouseEvent event) {
        // Redirect to Forgot Password Form
    }

    @FXML
    void loginAction(MouseEvent event) {
        try {
            account = visitor.login(usernameTxt.getText(), passwordTxt.getText());
            
            if(account != null) {
                rm.setAccount(account);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/scheduler/application/ui/personalProjects/personalProjects.fxml"));
                Parent root = loader.load();
                personalProjectsController controller = (personalProjectsController) loader.getController();
                controller.setup(rm);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Scheduler - Personal Projects");
                stage.show();
                closeCurrentStageThroughJFXButton(event);
            }
        } catch (Exception ex) {
            openSnackbar(ex.getMessage(), anchorPane, "Close", 10000);
        }
    }

    @FXML
    void registerAction(MouseEvent event) {
        try {
           Parent root = FXMLLoader.load(getClass().getResource("/scheduler/application/ui/login/register.fxml"));
           Scene scene = new Scene(root);
           Stage stage = new Stage();
           stage.setScene(scene);
           stage.show();
           closeCurrentStageThroughJFXButton(event);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Instantiate RegistryManager
        rm = new RegistryManager();
        
        // Get the Visitor Interface
        rm.getIVisitor();
        
        // Get Visitor
        visitor = rm.getVisitor();
    }    
    
    private void closeCurrentStageThroughJFXButton(MouseEvent event) {
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
