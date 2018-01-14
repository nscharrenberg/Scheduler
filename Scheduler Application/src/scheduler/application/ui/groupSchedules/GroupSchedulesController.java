/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.application.ui.groupSchedules;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.validation.RequiredFieldValidator;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import scheduler.application.RegistryManager;
import scheduler.application.model.GroupSchedule;
import scheduler.application.rmi.interfaces.IUser;
import scheduler.application.ui.groupSchedules.view.ViewGroupScheduleController;
/**
 * FXML Controller class
 *
 * @author Noah Scharrenberg
 */
public class GroupSchedulesController implements Initializable {

    private RegistryManager rm;
    private IUser user;
    private Timer timer = null;
    
    @FXML
    public StackPane anchorPane;

    @FXML
    private JFXButton testBtn;
    
    @FXML
    private TableView<GroupSchedule> scheduleTables;

    @FXML
    void createScheduleAction(ActionEvent event) {
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        
        dialogLayout.setHeading(new Text("Add Group Schedule."));
        
        /*
         * Setting up Node Form to submit Reminder
         */
        
        // Reminder Name
        JFXTextField rNameTxt = new JFXTextField();
        rNameTxt.setPadding(new Insets(10, 10, 0, 10));
        rNameTxt.setPromptText("Enter a Group Schedule Name");
        RequiredFieldValidator validatorName = new RequiredFieldValidator();
        validatorName.setMessage("Input Required");
        rNameTxt.getValidators().add(validatorName);
        rNameTxt.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) rNameTxt.validate();
        });
        
        VBox v = new VBox();
        v.getChildren().add(rNameTxt);
        
        // Implemented Node Form into dialogLayout Body
        dialogLayout.setBody(v);
        JFXButton btn = new JFXButton("Save Reminder");
        btn.setStyle("-fx-background-color: #2980b9; -fx-text-fill: #ecf0f1");
        btn.setRipplerFill(Color.web("#3498db"));
        JFXDialog dialog = new JFXDialog(anchorPane, dialogLayout,JFXDialog.DialogTransition.CENTER);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    user.addGroupSchedule(rm.getAccount(), rNameTxt.getText());
                } catch (SQLException ex) {
                    openSnackbar(ex.getMessage(), anchorPane, "Close", 10000);
                } catch (Exception ex) {
                    openSnackbar(ex.getMessage(), anchorPane, "Close", 10000);
                }
                
                
                dialog.close();
            }
        });
        dialogLayout.setActions(btn);
        
        dialog.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        scheduleTables.setRowFactory(tv -> {
            TableRow<GroupSchedule> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                GroupSchedule rd = row.getItem();
                viewGroupSchedule(event, rd);
            });
            
            return row;
        });
        
        timer = new Timer();
        timer.scheduleAtFixedRate(new SearchGroupSchedules(), 2000, 500);
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
    
    public ObservableList<GroupSchedule> getSchedules() throws RemoteException, SQLException, Exception {
        ObservableList<GroupSchedule> schedules = FXCollections.observableArrayList();
        schedules.addAll(user.getGroupSchedules(rm.getAccount()));

        return schedules;
    }
    
    public void setupScheduleOverview() {
        try {
            // Setup TableView Columns
            TableColumn<GroupSchedule, Integer> idCOlumn = new TableColumn<>("#");
            idCOlumn.setMinWidth(10);
            idCOlumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            
            TableColumn<GroupSchedule, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setMinWidth(200);
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            
            TableColumn<GroupSchedule, Timestamp> createdColumn = new TableColumn<>("createdAt");
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
    
    public void viewGroupSchedule(MouseEvent event, GroupSchedule schedule) {
        try {
            if(rm.getAccount() != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/scheduler/application/ui/groupSchedules/view/viewGroupSchedule.fxml"));
                Parent root = loader.load();
                ViewGroupScheduleController controller = (ViewGroupScheduleController) loader.getController();
                controller.setup(rm, schedule.getId());
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Group Scheduler - " + schedule.getName());
                stage.show();
                closeCurrentStageThroughTableRow(event);
            }
        } catch (Exception ex) {
            openSnackbar(ex.getMessage(), anchorPane, "Close", 10000);
        }
    }
    
    class SearchGroupSchedules extends TimerTask {
    
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
