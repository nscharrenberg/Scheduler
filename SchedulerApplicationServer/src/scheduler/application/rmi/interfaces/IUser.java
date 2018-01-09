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
    PersonalSchedule getPersonalSchedule(int scheduleId) throws RemoteException, SQLException, Exception;
    List<PersonalSchedule> getPersonalSchedules(int userId) throws RemoteException, SQLException, Exception;
    List<GroupSchedule> getGroupSchedules(int userId) throws RemoteException, SQLException, Exception;
    boolean addPersonalSchedule(Account owner, String name) throws RemoteException, SQLException, Exception;
    boolean addGroupSchedule(Account owner, String name) throws RemoteException, SQLException, Exception;
    boolean addPersonalTask(String name, String description, Timestamp deadline) throws RemoteException, SQLException, Exception;
    boolean addPersonalReminder(String name, String description, Timestamp startTime, Timestamp endTime) throws RemoteException, SQLException, Exception;
    void logout(int userId) throws RemoteException, SQLException, Exception;
    boolean deletePersonalSchedule(int scheduleId) throws RemoteException, SQLException, Exception;
}
