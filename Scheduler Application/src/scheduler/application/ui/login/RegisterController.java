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
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import scheduler.application.RegistryManager;
import scheduler.application.rmi.interfaces.IVisitor;

/**
 * FXML Controller class
 *
 * @author Noah Scharrenberg
 */
public class RegisterController implements Initializable {
    
    private RegistryManager rm;
    private IVisitor visitor;
    
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private VBox vBox;

    @FXML
    private JFXTextField usernameTxt, emailTxt;

    @FXML
    private JFXPasswordField passwordTxt, confPasswordTxt;

    @FXML
    private JFXButton createBtn, loginBtn;

    @FXML
    void loginAction(MouseEvent event) {
        try {
           Parent root = FXMLLoader.load(getClass().getResource("/scheduler/application/ui/login/login.fxml"));
           Scene scene = new Scene(root);
           Stage stage = new Stage();
           stage.setScene(scene);
           stage.show();
           closeCurrentStageThroughJFXButton(event);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void registerAction(MouseEvent event) {
        try {
            if(visitor.register(usernameTxt.getText(), emailTxt.getText(), passwordTxt.getText(), confPasswordTxt.getText())) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/scheduler/application/ui/login/login.fxml"));
                Parent root = loader.load();
                LoginController controller = (LoginController) loader.getController();
                controller.openSnackbar("Account created! Please Login...", controller.anchorPane, "Close", 10000);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                closeCurrentStageThroughJFXButton(event);
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
    
    private void openSnackbar(String msg, Pane pane, String btnTxt, int longtime) {
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
