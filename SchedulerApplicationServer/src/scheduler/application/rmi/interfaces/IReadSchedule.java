/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.application.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;
import scheduler.application.model.GroupSchedule;
import scheduler.application.model.Reminder;
import scheduler.application.model.Task;

/**
 *
 * @author Noah Scharrenberg
 */
public interface IReadSchedule extends Remote {
    GroupSchedule getGroupSchedule(int scheduleId) throws RemoteException, SQLException, Exception;
    boolean leaveGroupSchedule(int scheduleId) throws RemoteException, SQLException, Exception;
    List<Task> getTasks(int ScheduleId) throws RemoteException, SQLException, Exception;
    List<Reminder> getReminders(int scheduleId) throws RemoteException, SQLException, Exception;
}
