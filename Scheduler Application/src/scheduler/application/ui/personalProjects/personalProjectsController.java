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
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.sun.org.apache.regexp.internal.REUtil;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import scheduler.application.RegistryManager;
import scheduler.application.model.Account;
import scheduler.application.model.PersonalSchedule;
import scheduler.application.model.Schedule;
import scheduler.application.rmi.interfaces.IUser;
import scheduler.application.rmi.interfaces.IVisitor;
import scheduler.application.ui.addSchedule.AddScheduleController;
import scheduler.application.ui.personalProjects.view.ViewScheduleController;
import sun.security.x509.RDN;

/**
 *
 * @author Noah Scharrenberg
 */
public class personalProjectsController implements Initializable {
    
    private RegistryManager rm;
    private IUser user;
    private Timer timer = null;
    
    @FXML
    public AnchorPane anchorPane;

    @FXML
    private JFXButton testBtn;
    
    @FXML
    private TableView<PersonalSchedule> scheduleTables;

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
        scheduleTables.setRowFactory(tv -> {
            TableRow<PersonalSchedule> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                PersonalSchedule rd = row.getItem();
                viewPersonalSchedule(event, rd);
            });
            
            return row;
        });
        
        timer = new Timer();
        timer.scheduleAtFixedRate(new SearchPersonalSchedules(), 2000, 15000);

    } 
    
    public void setup(RegistryManager rm) throws RemoteException {
        this.rm = rm;
        rm.getIUser();
        this.user = rm.getUser();
        
        setupScheduleOverview();
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
        if(timer != null) {
            timer.cancel();
        }
        
        ((Stage)(((JFXButton)event.getSource()).getScene().getWindow())).close();
    }
    
    private void closeCurrentStageThroughTableRow(Event event) {
        if(timer != null) {
            timer.cancel();
        }
        
        ((Stage)(((TableRow)event.getSource()).getScene().getWindow())).close();
    }
    
    public ObservableList<PersonalSchedule> getSchedules() throws RemoteException, SQLException, Exception {
        ObservableList<PersonalSchedule> schedules = FXCollections.observableArrayList();
        schedules.addAll(user.getPersonalSchedules(rm.getAccount()));

        return schedules;
    }
    
    public void setupScheduleOverview() {
        try {
            // Setup TableView Columns
            TableColumn<PersonalSchedule, Integer> idCOlumn = new TableColumn<>("#");
            idCOlumn.setMinWidth(10);
            idCOlumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            
            TableColumn<PersonalSchedule, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setMinWidth(200);
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            
            TableColumn<PersonalSchedule, Timestamp> createdColumn = new TableColumn<>("createdAt");
            createdColumn.setMinWidth(200);
            createdColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
            
            scheduleTables.setItems(getSchedules());
            scheduleTables.getColumns().clear();
            scheduleTables.getColumns().addAll(idCOlumn, nameColumn, createdColumn);
        } catch (RemoteException ex) {
            openSnackbar(ex.getMessage(), anchorPane, "Close", 10000);
        } catch (SQLException ex) {
            openSnackbar(ex.getMessage(), anchorPane, "Close", 10000);
        } catch (Exception ex) {
            openSnackbar(ex.getMessage(), anchorPane, "Close", 10000);
        }
    }
    
    public void viewPersonalSchedule(MouseEvent event, PersonalSchedule schedule) {
        try {
            if(rm.getAccount() != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/scheduler/application/ui/personalProjects/view/viewSchedule.fxml"));
                Parent root = loader.load();
                ViewScheduleController controller = (ViewScheduleController) loader.getController();
                controller.setup(rm, schedule.getId());
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Scheduler - ");
                stage.show();
                closeCurrentStageThroughTableRow(event);
            }
        } catch (Exception ex) {
            openSnackbar(ex.getMessage(), anchorPane, "Close", 10000);
            ex.printStackTrace();
        }
    }
    
    class SearchPersonalSchedules extends TimerTask {
    
        @Override
        public void run() {
            try {
                scheduleTables.setItems(getSchedules());;
            } catch (RemoteException ex) {
                openSnackbar(ex.getMessage(), anchorPane, "Close", 10000);
            } catch (SQLException ex) {
                openSnackbar(ex.getMessage(), anchorPane, "Close", 10000);
            } catch (Exception ex) {
                openSnackbar(ex.getMessage(), anchorPane, "Close", 10000);
            }
        }
    }
}


