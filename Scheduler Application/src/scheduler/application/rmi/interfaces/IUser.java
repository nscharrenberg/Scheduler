/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.application.rmi.interfaces;

import java.rmi.Remote;
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
public interface IUser extends Remote {
    List<PersonalSchedule> getPersonalSchedules(Account user) throws RemoteException, SQLException, Exception;
    List<GroupSchedule> getGroupSchedules(Account user) throws RemoteException, SQLException, Exception;
    boolean addPersonalSchedule(Account owner, String name) throws RemoteException, SQLException, Exception;
    boolean addGroupSchedule(Account owner, String name) throws RemoteException, SQLException, Exception;
    boolean addPersonalTask(String name, String description, Timestamp deadline, int schedule) throws RemoteException, SQLException, Exception;
    boolean addPersonalReminder(String name, String description, Timestamp startTime, Timestamp endTime, int schedule) throws RemoteException, SQLException, Exception;
    void logout(int userId) throws RemoteException, SQLException, Exception;
    boolean deletePersonalSchedule(int scheduleId) throws RemoteException, SQLException, Exception;
    PersonalSchedule getPersonalSchedule(Account user, int scheduleId) throws RemoteException, SQLException, Exception;
    Account findUserByUsername(String username) throws SQLException, Exception;
}
