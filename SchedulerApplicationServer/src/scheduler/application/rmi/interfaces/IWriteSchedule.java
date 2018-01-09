/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.application.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import scheduler.application.model.GroupSchedule;

/**
 *
 * @author Noah Scharrenberg
 */
public interface IWriteSchedule extends Remote {
    boolean addGroupTask(String name, String description, Timestamp deadline) throws RemoteException;
    boolean addGroupReminder(String name, String description, Timestamp startTime, Timestamp endTime) throws RemoteException;
    boolean addMemberByUsername(String username) throws RemoteException;
    boolean addMemberByEmail(String email) throws RemoteException;
    boolean addWriterByUsername(String username) throws RemoteException;
    boolean addWriterByEmail(String email) throws RemoteException;
    boolean removeMember(int userId) throws RemoteException;
    boolean removeWriter(int userId) throws RemoteException;
    boolean updateSchedule(GroupSchedule schedule) throws RemoteException;
    boolean deleteSchedule(int scheduleId) throws RemoteException;
}
