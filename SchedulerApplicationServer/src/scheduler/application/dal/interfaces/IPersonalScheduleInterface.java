/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.application.dal.interfaces;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import scheduler.application.model.Account;
import scheduler.application.model.GroupSchedule;
import scheduler.application.model.PersonalSchedule;

/**
 *
 * @author Noah Scharrenberg
 */
public interface IPersonalScheduleInterface {
    boolean addPersonalSchedule(Account owner, String name) throws SQLException, Exception;
    boolean addPersonalTask(String name, String description, Timestamp deadline, int schedule) throws SQLException, Exception;
    boolean addPersonalReminder(String name, String description, Timestamp startTime, Timestamp endTime, int schedule) throws SQLException, Exception;
    boolean deletePersonalSchedule(int scheduleId) throws SQLException, Exception;
    List<PersonalSchedule> getPersonalSchedules(Account user) throws SQLException, Exception;
    PersonalSchedule getPersonalSchedule(Account user, int scheduleId) throws SQLException, Exception;
    boolean addGroupSchedule(Account owner, String name) throws SQLException, Exception;
    List<GroupSchedule> getGroupSchedules(Account user) throws SQLException, Exception;
    GroupSchedule getGroupSchedule(Account user, int scheduleId) throws SQLException, Exception;
    boolean addMember(String username, int schedule, boolean writer) throws SQLException, Exception;
    boolean isAlreadyAdded(int user, int schedule) throws SQLException, Exception;
}
