/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.application.ui.personalProjects.view;

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
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import scheduler.application.RegistryManager;
import scheduler.application.model.PersonalSchedule;
import scheduler.application.model.Reminder;
import scheduler.application.model.Task;
import scheduler.application.rmi.interfaces.IReadSchedule;
import scheduler.application.rmi.interfaces.IUser;
import scheduler.application.ui.personalProjects.personalProjectsController;

/**
 * FXML Controller class
 *
 * @author Noah Scharrenberg
 */
public class ViewScheduleController implements Initializable {
    private RegistryManager rm;
    private IUser user;
    private IReadSchedule read;
    private PersonalSchedule schedule;
    
    private Timer timer = null;
    
     @FXML
    public StackPane anchorPane;
     
    @FXML
    private ImageView homeBtn;

    @FXML
    private ImageView taskBtn;

    @FXML
    private ImageView reminderBtn;
    
    @FXML
    private TableView<Task> taskTable;

    @FXML
    private TableView<Reminder> reminderTable;
    
    @FXML
    void homeClickAction(MouseEvent event) {
        try {
            if(rm.getAccount() != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/scheduler/application/ui/personalProjects/personalProjects.fxml"));
                Parent root = loader.load();
                personalProjectsController controller = (personalProjectsController) loader.getController();
                controller.setup(rm);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Scheduler - Personal Projects");
                stage.show();
                closeCurrentStageThroughImageView(event);
            }
        } catch (Exception ex) {
            openSnackbar(ex.getMessage(), anchorPane, "Close", 10000);
        }
    }
     
    @FXML
    void homeHoverAction(MouseEvent event) {
        homeBtn.setImage(new Image("/scheduler/application/assets/images/home_blue.png"));
    }

    @FXML
    void homeUnHoverAction(MouseEvent event) {
        homeBtn.setImage(new Image("/scheduler/application/assets/images/home_light.png"));
    }

    @FXML
    void reminderClickAction(MouseEvent event) {
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        
        dialogLayout.setHeading(new Text("Add Reminder."));
        
        /*
         * Setting up Node Form to submit Reminder
         */
        
        // Reminder Name
        JFXTextField rNameTxt = new JFXTextField();
        rNameTxt.setPadding(new Insets(10, 10, 0, 10));
        rNameTxt.setPromptText("Enter a Reminder Name");
        RequiredFieldValidator validatorName = new RequiredFieldValidator();
        validatorName.setMessage("Input Required");
        rNameTxt.getValidators().add(validatorName);
        rNameTxt.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) rNameTxt.validate();
        });
        
        // Reminder Description
        JFXTextField rDescriptionTxt = new JFXTextField();
        rDescriptionTxt.setPadding(new Insets(40, 10, 0, 10));
        rDescriptionTxt.setPromptText("Enter your reminder description here.");
        RequiredFieldValidator validatorDescription = new RequiredFieldValidator();
        validatorDescription.setMessage("Input Required");
        rDescriptionTxt.getValidators().add(validatorDescription);
        rDescriptionTxt.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) rDescriptionTxt.validate();
        });
        
        // Reminder Date Selector
        Label rDateLbl = new Label("Date: ");
        rDateLbl.setFont(new Font("Arial", 12));
        rDateLbl.setTextFill(Color.web("#3498db"));
        rDateLbl.setPadding(new Insets(40, 10, 0, 10));
        JFXDatePicker rDatePicker = new JFXDatePicker();
        rDatePicker.setPadding(new Insets(30, 10, 0, 10));
        rDatePicker.setDefaultColor(Color.valueOf("#3498db"));
        
        // Reminder Start Time Selector
        Label rStartLbl = new Label("Start Time: ");
        rStartLbl.setFont(new Font("Arial", 12));
        rStartLbl.setTextFill(Color.web("#3498db"));
        rStartLbl.setPadding(new Insets(30, 10, 0, 10));
        JFXTimePicker rStartTimePicker = new JFXTimePicker();
        rStartTimePicker.setPadding(new Insets(20, 10, 0, 10));
        rStartTimePicker.setDefaultColor(Color.valueOf("#3498db"));
        rStartTimePicker.setIs24HourView(true);
        
        // Reminder End Time Selector
        Label rEndLbl = new Label("End Time: ");
        rEndLbl.setFont(new Font("Arial", 12));
        rEndLbl.setTextFill(Color.web("#3498db"));
        rEndLbl.setPadding(new Insets(30, 10, 0, 10));
        JFXTimePicker rEndTimePicker = new JFXTimePicker();
        rEndTimePicker.setPadding(new Insets(20, 10, 0, 10));
        rEndTimePicker.setDefaultColor(Color.valueOf("#3498db"));
        rEndTimePicker.setIs24HourView(true);
        
        HBox dateLayout = new HBox();
        dateLayout.getChildren().addAll(rDateLbl, rDatePicker);
        
        HBox startLayout = new HBox();
        startLayout.getChildren().addAll(rStartLbl, rStartTimePicker);
        
        HBox endLayout = new HBox();
        endLayout.getChildren().addAll(rEndLbl, rEndTimePicker);
        
        VBox v = new VBox();
        v.getChildren().add(rNameTxt);
        v.getChildren().add(rDescriptionTxt);
        v.getChildren().add(dateLayout);
        v.getChildren().add(startLayout);
        v.getChildren().add(endLayout);
        
        // Implemented Node Form into dialogLayout Body
        dialogLayout.setBody(v);
        JFXButton btn = new JFXButton("Save Reminder");
        btn.setStyle("-fx-background-color: #2980b9; -fx-text-fill: #ecf0f1");
        btn.setRipplerFill(Color.web("#3498db"));
        JFXDialog dialog = new JFXDialog(anchorPane, dialogLayout,JFXDialog.DialogTransition.CENTER);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DateFormat startDf = new SimpleDateFormat("yyyy-mm-dd HH:mm");
                DateFormat endDf = new SimpleDateFormat("yyyy-mm-dd HH:mm");
                try {
                    Date startDate = startDf.parse(rDatePicker.getValue() + " " + rStartTimePicker.getValue());
                    Date endDate = endDf.parse(rDatePicker.getValue() + " " + rEndTimePicker.getValue());
                    System.out.println("Timestamp: " + new Timestamp(startDate.getTime()));
                    user.addPersonalReminder(rNameTxt.getText(), rDescriptionTxt.getText(), new Timestamp(startDate.getTime()), new Timestamp(endDate.getTime()), schedule.getId());
                } catch (ParseException ex) {
                    openSnackbar(ex.getMessage(), anchorPane, "Close", 10000);
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
 

    @FXML
    void reminderHoverAction(MouseEvent event) {
        reminderBtn.setImage(new Image("/scheduler/application/assets/images/reminder_blue.png"));
    }

    @FXML
    void reminderUnHoverAction(MouseEvent event) {
        reminderBtn.setImage(new Image("/scheduler/application/assets/images/reminder_light.png"));
    }

    @FXML
    void taskClickAction(MouseEvent event) {
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        
        dialogLayout.setHeading(new Text("Add Task."));
        
        /*
         * Setting up Node Form to submit Reminder
         */
        
        // Reminder Name
        JFXTextField rNameTxt = new JFXTextField();
        rNameTxt.setPadding(new Insets(10, 10, 0, 10));
        rNameTxt.setPromptText("Enter a Task Name");
        RequiredFieldValidator validatorName = new RequiredFieldValidator();
        validatorName.setMessage("Input Required");
        rNameTxt.getValidators().add(validatorName);
        rNameTxt.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) rNameTxt.validate();
        });
        
        // Reminder Description
        JFXTextField rDescriptionTxt = new JFXTextField();
        rDescriptionTxt.setPadding(new Insets(40, 10, 0, 10));
        rDescriptionTxt.setPromptText("Enter your Task description here.");
        RequiredFieldValidator validatorDescription = new RequiredFieldValidator();
        validatorDescription.setMessage("Input Required");
        rDescriptionTxt.getValidators().add(validatorDescription);
        rDescriptionTxt.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) rDescriptionTxt.validate();
        });
        
        // Reminder Date Selector
        Label rDateLbl = new Label("Deadline Date: ");
        rDateLbl.setFont(new Font("Arial", 12));
        rDateLbl.setTextFill(Color.web("#3498db"));
        rDateLbl.setPadding(new Insets(40, 10, 0, 10));
        JFXDatePicker rDatePicker = new JFXDatePicker();
        rDatePicker.setPadding(new Insets(30, 10, 0, 10));
        rDatePicker.setDefaultColor(Color.valueOf("#3498db"));
        
        // Reminder Start Time Selector
        Label rStartLbl = new Label("Deadline Time: ");
        rStartLbl.setFont(new Font("Arial", 12));
        rStartLbl.setTextFill(Color.web("#3498db"));
        rStartLbl.setPadding(new Insets(30, 10, 0, 10));
        JFXTimePicker rStartTimePicker = new JFXTimePicker();
        rStartTimePicker.setPadding(new Insets(20, 10, 0, 10));
        rStartTimePicker.setDefaultColor(Color.valueOf("#3498db"));
        rStartTimePicker.setIs24HourView(true);

        
        HBox dateLayout = new HBox();
        dateLayout.getChildren().addAll(rDateLbl, rDatePicker);
        
        HBox startLayout = new HBox();
        startLayout.getChildren().addAll(rStartLbl, rStartTimePicker);
        
        VBox v = new VBox();
        v.getChildren().add(rNameTxt);
        v.getChildren().add(rDescriptionTxt);
        v.getChildren().add(dateLayout);
        v.getChildren().add(startLayout);
        
        // Implemented Node Form into dialogLayout Body
        dialogLayout.setBody(v);
        JFXButton btn = new JFXButton("Save Task".toUpperCase());
        btn.setStyle("-fx-background-color: #2980b9; -fx-text-fill: #ecf0f1");
        btn.setRipplerFill(Color.web("#3498db"));
        
        
        JFXDialog dialog = new JFXDialog(anchorPane, dialogLayout,JFXDialog.DialogTransition.CENTER);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DateFormat deadlineDf = new SimpleDateFormat("yyyy-mm-dd HH:mm");
                try {
                    Date deadline = deadlineDf.parse(rDatePicker.getValue() + " " + rStartTimePicker.getValue());
                    System.out.println("Timestamp: " + new Timestamp(deadline.getTime()));
                    user.addPersonalTask(rNameTxt.getText(), rDescriptionTxt.getText(), new Timestamp(deadline.getTime()), schedule.getId());
                } catch (ParseException ex) {
                    openSnackbar(ex.getMessage(), anchorPane, "Close", 10000);
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

    @FXML
    void taskHoverAction(MouseEvent event) {
        taskBtn.setImage(new Image("/scheduler/application/assets/images/task_blue.png"));
    }

    @FXML
    void taskUnHoverAction(MouseEvent event) {
        taskBtn.setImage(new Image("/scheduler/application/assets/images/task_light.png"));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new SearchItems(), 2000, 2000);
    }   
    
    public void setup(RegistryManager rm, int schedule) throws RemoteException, SQLException, Exception {
        this.rm = rm;
        rm.getIUser();
        this.user = rm.getUser();
        rm.getIReadSchedule();
        this.read = rm.getRead();
        
        this.schedule = user.getPersonalSchedule(rm.getAccount(), schedule);
        setupTaskOverview();
        setupReminderOverview();
        openSnackbar(this.schedule.getName(), anchorPane, "Close", 10000);
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
    
    private void closeCurrentStageThroughImageView(Event event) {
        ((Stage)(((ImageView)event.getSource()).getScene().getWindow())).close();
    }
    
    public ObservableList<Task> getTasks() throws RemoteException, SQLException, Exception {
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        tasks.addAll(read.getTasks(schedule.getId()));
        
        return tasks;
    }
    
    public void setupTaskOverview() {
        try {
            // Setup TableView Columns
            TableColumn<Task, Integer> idCOlumn = new TableColumn<>("#");
            idCOlumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            
            TableColumn<Task, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            
            TableColumn<Task, Timestamp> deadlineColumn = new TableColumn<>("Deadline");
            deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
            
            taskTable.setItems(getTasks());
            taskTable.getColumns().clear();
            taskTable.getColumns().addAll(idCOlumn, nameColumn, deadlineColumn);
        } catch (Exception ex) {
            openSnackbar(ex.getMessage(), anchorPane, "Close", 10000);
        }
    }
    
    public ObservableList<Reminder> getReminders() throws RemoteException, SQLException, Exception {
        ObservableList<Reminder> reminders = FXCollections.observableArrayList();
        reminders.addAll(read.getReminders(schedule.getId()));
        
        return reminders;
    }
    
    public void setupReminderOverview() {
        try {
            // Setup TableView Columns
            TableColumn<Reminder, Integer> idCOlumn = new TableColumn<>("#");
            idCOlumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            
            TableColumn<Reminder, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            
            TableColumn<Reminder, Timestamp> startColumn = new TableColumn<>("Start Time");
            startColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            
            TableColumn<Reminder, Timestamp> endColumn = new TableColumn<>("End Time");
            endColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            
            reminderTable.setItems(getReminders());
            reminderTable.getColumns().clear();
            reminderTable.getColumns().addAll(idCOlumn, nameColumn, startColumn, endColumn);
        } catch (Exception ex) {
            openSnackbar(ex.getMessage(), anchorPane, "Close", 10000);
        }
    }
    
    class SearchItems extends TimerTask {
    
        @Override
        public void run() {
            try {
                taskTable.setItems(getTasks());
                reminderTable.setItems(getReminders());
            } catch (RemoteException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
