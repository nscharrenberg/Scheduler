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
import scheduler.application.dal.mysql.AccountMySQLDao;
import scheduler.application.dal.repository.AccountRepository;

/**
 * FXML Controller class
 *
 * @author Noah Scharrenberg
 */
public class LoginController implements Initializable {
    
    static AccountMySQLDao accountDao = new AccountMySQLDao();
    AccountRepository ar = new AccountRepository(accountDao);
    
    @FXML
    private AnchorPane anchorPane;
    
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
            if(ar.login(usernameTxt.getText(), passwordTxt.getText()) != null) {
                Parent root = FXMLLoader.load(getClass().getResource("/scheduler/application/ui/personalProjects/personalProjects.fxml"));
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
        // TODO
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
