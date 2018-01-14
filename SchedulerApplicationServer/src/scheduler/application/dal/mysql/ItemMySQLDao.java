/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.application.dal.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import scheduler.application.dal.interfaces.IItemInterface;
import static scheduler.application.dal.mysql.PersonalScheduleMySQLDao.GET_MY_PERSONAL_SCHEDULES;
import scheduler.application.model.PersonalSchedule;
import scheduler.application.model.Reminder;
import scheduler.application.model.Task;

/**
 *
 * @author Noah Scharrenberg
 */
public class ItemMySQLDao implements IItemInterface {
    private Connection sqlConn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private ConnectionSetup conn = new ConnectionSetup();
    
    static final String GET_TASKS = "SELECT * FROM task WHERE schedule_id = ?";
    static final String GET_REMINDERS = "SELECT * FROM reminder WHERE schedule_id = ?";

    @Override
    public List<Task> getTasks(int scheduleId) throws SQLException, Exception {
        List<Task> tasks = new ArrayList<>();
        
        conn.getConnection();
        pstmt = conn.getConn().prepareStatement(GET_TASKS);
        pstmt.setInt(1, scheduleId);
        
        try(ResultSet tempRs = pstmt.executeQuery()) {
            rs = tempRs;
            int id = 0;
            
            while(rs.next()) {
                id = rs.getInt("id");
                if(id != 0) {
                    
                    Task task = new Task(id, rs.getString("name"), rs.getString("description"), rs.getTimestamp("deadline"));
                    tasks.add(task);
                }
                
                id = 0;
            }
        }
        
        conn.closeConnection();
        
        return tasks;
    }

    @Override
    public List<Reminder> getReminders(int scheduleId) throws SQLException, Exception {
         List<Reminder> reminders = new ArrayList<>();
        
        conn.getConnection();
        pstmt = conn.getConn().prepareStatement(GET_REMINDERS);
        pstmt.setInt(1, scheduleId);
        
        try(ResultSet tempRs = pstmt.executeQuery()) {
            rs = tempRs;
            int id = 0;
            
            while(rs.next()) {
                id = rs.getInt("id");
                if(id != 0) {
                    
                    Reminder reminder = new Reminder(id, rs.getString("name"), rs.getString("description"), rs.getTimestamp("start_time"), rs.getTimestamp("end_time"));
                    reminders.add(reminder);
                }
                
                id = 0;
            }
        }
        
        conn.closeConnection();
        
        return reminders;
    }
}
