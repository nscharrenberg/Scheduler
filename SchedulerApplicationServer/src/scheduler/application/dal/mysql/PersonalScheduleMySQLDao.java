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
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import scheduler.application.model.Account;
import scheduler.application.dal.interfaces.IPersonalScheduleInterface;

/**
 *
 * @author Noah Scharrenberg
 */
public class PersonalScheduleMySQLDao implements IPersonalScheduleInterface {
    private Connection sqlConn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private ConnectionSetup conn = new ConnectionSetup();
    
    static final String GET_MY_PERSONAL_SCHEDULES = "SELECT * FROM schedule WHERE owner = ?";
    static final String ADD_PERSONAL_SCHEDULE = "INSERT INTO schedule(name, isPersonal, owner, created_at) VALUES(?, ?, ?, ?)";
    static final String ADD_PERSONAL_TASK = "INSERT INTO task(name, description, deadline, schedule_id, created_at) VALUES(?, ?, ?, ?, ?)";
    static final String ADD_PERSONAL_REMINDER = "INSERT INTO reminder(name, description, start_time, end_time, schedule_id, created_at) VALUES(?, ?, ?, ?, ?, ?)";
   
    @Override
    public boolean addPersonalSchedule(Account owner, String name) throws SQLException, Exception {
        conn.getConnection();
        
        System.out.println("Owner: " + owner.getUsername());
        System.out.println("Name: " + name);
        
        pstmt = conn.getConn().prepareStatement(ADD_PERSONAL_SCHEDULE);
        pstmt.setString(1, name);
        pstmt.setBoolean(2, true);
        pstmt.setInt(3, owner.getId());
        pstmt.setTimestamp(4, new Timestamp(new Date().getTime()));
        
        if(pstmt.executeUpdate() > 0) {
            System.out.println("Personal Schedule Added!");
            conn.closeConnection();
            
            return true;
        } else {
            System.out.println("Personal Schedule could NOT be added!");
            conn.closeConnection();
            
            throw new Exception("Failed to add schedule. Please try again!");
        }
    }

    @Override
    public boolean addPersonalTask(String name, String description, Timestamp deadline) throws SQLException, Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addPersonalReminder(String name, String description, Timestamp startTime, Timestamp endTime) throws SQLException, Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletePersonalSchedule(int scheduleId) throws SQLException, Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
