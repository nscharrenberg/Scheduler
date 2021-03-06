/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.application.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import scheduler.application.model.Account;

/**
 *
 * @author Noah Scharrenberg
 */
public interface IVisitor extends Remote {
    Account login(String username, String password) throws RemoteException, SQLException, Exception;
    boolean register(String username, String email, String password, String confPassword) throws RemoteException, SQLException, Exception;
}
