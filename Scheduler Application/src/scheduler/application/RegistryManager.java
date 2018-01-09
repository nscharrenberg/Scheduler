/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.application;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import scheduler.application.model.Account;
import scheduler.application.rmi.interfaces.IUser;
import scheduler.application.rmi.interfaces.IVisitor;

/**
 *
 * @author Noah Scharrenberg
 */
public class RegistryManager {
    private Account account;
    
    private IVisitor visitor;
    private IUser user;
    
    private static final int port = 1099;
    private static final String bindingName = "SchedulerServer";

    private Registry registry = null;
    private InetAddress localhost;
    private String ipAddress = "145.93.165.60";
    
    public RegistryManager() {
        getLocalHostIp();
        setup();
    }
    
    public Account getAccount() {
        return account;
    }
    
    public void setAccount(Account value) {
        account = value;
    }
    
    public IVisitor getVisitor() {
        return visitor;
    }
    
    public IUser getUser() {
        return user;
    }
    
    public void getIVisitor() {
        if(registry != null) {
            System.out.println("Client: Trying to find IVisitor Interface");
            try {
                this.visitor = (IVisitor)registry.lookup(bindingName);
                System.out.println("Client: IVisitor Interface succesfully bounded");
            } catch (RemoteException e) {
                System.out.println("Client: Unable to bind IVisitor Interface");
                System.out.println("Client: Exception Message: " + e.getMessage());
                visitor = null;
            } catch (NotBoundException e) {
                System.out.println("Client: IVisitor Interface reference is NOT bound!");
                System.out.println("Client: Exception Message: " + e.getMessage());
                visitor = null;
            }
        }
    }
    
    public void getIUser() {
        if(registry != null) {
            System.out.println("Client: Trying to find IUser Interface");
            try {
                this.user = (IUser)registry.lookup(bindingName);
                System.out.println("Client: IUser Interface succesfully bounded");
            } catch (RemoteException e) {
                System.out.println("Client: Unable to bind IUser Interface");
                System.out.println("Client: Exception Message: " + e.getMessage());
                visitor = null;
            } catch (NotBoundException e) {
                System.out.println("Client: IUser Interface reference is NOT bound!");
                System.out.println("Client: Exception Message: " + e.getMessage());
                visitor = null;
            }
        }
    }
    
    public void getLocalHostIp() {
        try {
            localhost = InetAddress.getLocalHost();
            ipAddress = localhost.getHostAddress();
        } catch (UnknownHostException ex) {
            System.out.println("Client: Unknown Host");
            System.out.println("Client: Exception Message: " + ex.getMessage());
        }  
    }
    
    public void setup() {
        try {
            System.out.println("Client: Attempting to connect to Server: " + ipAddress + ":" + port);
            registry = LocateRegistry.getRegistry(ipAddress, port);
            
            if(registry != null) {
                System.out.println("Client: Connected and Registry found!");
            }
        } catch(RemoteException e) {
            System.out.println("Client: Unable to locate registry");
            System.out.println("Client: Exception Message: " + e.getMessage());
            registry = null;
        }
    }
    
    
}
