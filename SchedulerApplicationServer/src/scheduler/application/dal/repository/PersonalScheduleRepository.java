/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.application.dal.repository;

import java.sql.SQLException;
import java.sql.Timestamp;
import scheduler.application.model.Account;
import scheduler.application.dal.interfaces.IPersonalScheduleInterface;

/**
 *
 * @author Noah Scharrenberg
 */
public class PersonalScheduleRepository {
    private IPersonalScheduleInterface context;
    
    public PersonalScheduleRepository(IPersonalScheduleInterface context) {
        this.context = context;
    }
    
    public boolean addPersonalSchedule(Account owner, String name) throws SQLException, Exception {
        return context.addPersonalSchedule(owner, name);
    }
    
    public boolean addPersonalTask(String name, String description, Timestamp deadline) throws SQLException, Exception {
        return context.addPersonalTask(name, description, deadline);
    }
    
    public boolean addPersonalReminder(String name, String description, Timestamp startTime, Timestamp endTime) throws SQLException, Exception {
        return context.addPersonalReminder(name, description, startTime, endTime);
    }
    
    public boolean deletePersonalSchedule(int scheduleId) throws SQLException, Exception {
        return context.deletePersonalSchedule(scheduleId);
    }
}
