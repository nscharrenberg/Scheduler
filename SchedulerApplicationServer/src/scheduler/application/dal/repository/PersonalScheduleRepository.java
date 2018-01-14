/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.application.dal.repository;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import scheduler.application.model.Account;
import scheduler.application.dal.interfaces.IPersonalScheduleInterface;
import scheduler.application.model.GroupSchedule;
import scheduler.application.model.PersonalSchedule;

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
    
    public boolean addPersonalTask(String name, String description, Timestamp deadline, int schedule) throws SQLException, Exception {
        return context.addPersonalTask(name, description, deadline, schedule);
    }
    
    public boolean addPersonalReminder(String name, String description, Timestamp startTime, Timestamp endTime, int schedule) throws SQLException, Exception {
        return context.addPersonalReminder(name, description, startTime, endTime, schedule);
    }
    
    public boolean deletePersonalSchedule(int scheduleId) throws SQLException, Exception {
        return context.deletePersonalSchedule(scheduleId);
    }
    
    public List<PersonalSchedule> getPersonalSchedules(Account user) throws SQLException, Exception {
        return context.getPersonalSchedules(user);
    }
    
    public PersonalSchedule getPersonalSchedule(Account user, int scheduleId) throws SQLException, Exception {
        return context.getPersonalSchedule(user, scheduleId);
    }
    
    public boolean addGroupSchedule(Account owner, String name) throws SQLException, Exception {
        return context.addGroupSchedule(owner, name);
    }
    
    public List<GroupSchedule> getGroupSchedules(Account user) throws SQLException, Exception {
        return context.getGroupSchedules(user);
    }
    
    public GroupSchedule getGroupSchedule(Account user, int scheduleId) throws SQLException, Exception {
        return context.getGroupSchedule(user, scheduleId);
    }
    
    public boolean addMember(String username, int schedule, boolean writer) throws SQLException, Exception {
        return context.addMember(username, schedule, writer);
    }
    
    public boolean isAlreadyAdded(int user, int schedule) throws SQLException, Exception {
        return context.isAlreadyAdded(user, schedule);
    }
}
