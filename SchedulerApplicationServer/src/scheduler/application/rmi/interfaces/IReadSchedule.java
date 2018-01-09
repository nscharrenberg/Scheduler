/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.application.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import scheduler.application.model.GroupSchedule;

/**
 *
 * @author Noah Scharrenberg
 */
public interface IReadSchedule extends Remote {
    GroupSchedule getGroupSchedule(int scheduleId) throws RemoteException;
    boolean leaveGroupSchedule(int scheduleId) throws RemoteException;
}
