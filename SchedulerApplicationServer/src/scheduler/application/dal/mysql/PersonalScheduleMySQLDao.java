/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.application.dal.mysql;

import java.rmi.server.UID;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import scheduler.application.model.Account;
import scheduler.application.dal.interfaces.IPersonalScheduleInterface;
import scheduler.application.model.PersonalSchedule;

/**
 *
 * @author Noah Scharrenberg
 */
public class PersonalScheduleMySQLDao implements IPersonalScheduleInterface {
    private Connection sqlConn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private ConnectionSetup conn = new ConnectionSetup();
    
    static final String GET_MY_PERSONAL_SCHEDULES = "SELECT * FROM schedule WHERE owner = ? AND isPersonal = 1";
    static final String ADD_PERSONAL_SCHEDULE = "INSERT INTO schedule(name, isPersonal, owner, created_at) VALUES(?, ?, ?, ?)";
    static final String ADD_PERSONAL_TASK = "INSERT INTO task(name, description, deadline, schedule_id, created_at) VALUES(?, ?, ?, ?, ?)";
    static final String ADD_PERSONAL_REMINDER = "INSERT INTO reminder(name, description, start_time, end_time, schedule_id, created_at) VALUES(?, ?, ?, ?, ?, ?)";
    static final String DELETE_PERSONAL_SCHEDULE = "DELETE FROM schedule WHERE id = ?";
   
    @Override
    public boolean addPersonalSchedule(Account owner, String name) throws SQLException, Exception {
        conn.getConnection();
        
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
    public boolean addPersonalTask(String name, String description, Timestamp deadline, int schedule) throws SQLException, Exception {
        conn.getConnection();
        
        pstmt = conn.getConn().prepareStatement(ADD_PERSONAL_TASK);
        pstmt.setString(1, name);
        pstmt.setString(2, description);
        pstmt.setTimestamp(3, deadline);
        pstmt.setInt(4, schedule);
        pstmt.setTimestamp(5, new Timestamp(new Date().getTime()));
        
        if(pstmt.executeUpdate() > 0) {
            System.out.println("Task Added to Personal Schedule!");
            conn.closeConnection();
            
            return true;
        } else {
            System.out.println("Task could NOT be added to Personal Schedule!");
            conn.closeConnection();
            
            throw new Exception("Failed to add Task to Personal Schedule. Please try again!");
        }
    }

    @Override
    public boolean addPersonalReminder(String name, String description, Timestamp startTime, Timestamp endTime, int schedule) throws SQLException, Exception {
        conn.getConnection();
        
        pstmt = conn.getConn().prepareStatement(ADD_PERSONAL_REMINDER);
        pstmt.setString(1, name);
        pstmt.setString(2, description);
        pstmt.setTimestamp(3, startTime);
        pstmt.setTimestamp(4, endTime);
        pstmt.setInt(5, schedule);
        pstmt.setTimestamp(6, new Timestamp(new Date().getTime()));
        
        if(pstmt.executeUpdate() > 0) {
            System.out.println("Reminder Added to Personal Schedule!");
            conn.closeConnection();
            
            return true;
        } else {
            System.out.println("Reminder could NOT be added to Personal Schedule!");
            conn.closeConnection();
            
            throw new Exception("Failed to add Reminder to Personal Schedule. Please try again!");
        }
    }

    @Override
    public boolean deletePersonalSchedule(int scheduleId) throws SQLException, Exception {
        conn.getConnection();
        
        pstmt = conn.getConn().prepareStatement(DELETE_PERSONAL_SCHEDULE);
        pstmt.setInt(1, scheduleId);
        
        if(pstmt.executeUpdate() > 0) {
            System.out.println("Schedule Deleted!");
            conn.closeConnection();
            
            return true;
        } else {
            System.out.println("Schedule could NOT be deleted!");
            conn.closeConnection();
            
            throw new Exception("Failed to delete schedule. Please try again!");
        }
    }

    @Override
    public List<PersonalSchedule> getPersonalSchedules(Account user) throws SQLException, Exception {
        System.out.println("Hit PersonalScheduleDao?");
        List<PersonalSchedule> schedules = new ArrayList<>();
        
        conn.getConnection();
        pstmt = conn.getConn().prepareStatement(GET_MY_PERSONAL_SCHEDULES);
        pstmt.setInt(1, user.getId());
        
        try(ResultSet tempRs = pstmt.executeQuery()) {
            rs = tempRs;
            System.out.println("ResultSet Size: " + rs);
            int id = 0;
            
            while(rs.next()) {
                id = rs.getInt("id");
                if(id != 0) {
                    
                    PersonalSchedule schedule = new PersonalSchedule(id, user, rs.getString("name"), rs.getTimestamp("created_at"));
                    System.out.println("Schedule Name: " + schedule.getName());
                    schedules.add(schedule);
                    System.out.println("Personal Schedule loaded: " + schedule);
                }
                
                id = 0;
            }
        }
        
        conn.closeConnection();
        
        return schedules;
    }
    
}
