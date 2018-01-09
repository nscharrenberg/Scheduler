/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.server;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.net.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Noah Scharrenberg
 */
public class SchedulerApplicationServer extends Application {
    
    // RMI Port
    private static final int port = 1099;
    
    // RMI Binding Name
    private static final String bindingName = "SchedulerServer";
    
    // RMI Registry
    private Registry registry = null;
    private SchedulerServer scheduler = null;
     
    
    public SchedulerApplicationServer() {
        System.out.println("Server: Attempting to start Server on port: " + port);
        
        
        // Instantiate the SchedulerServer
        try {
            scheduler = new SchedulerServer();
            System.out.println("Server: Scheduler succesfully created!");
        } catch (RemoteException e) {
            System.out.println("Server: Unable to create Schedule.");
            System.out.println("Server: Exception Message: " + e.getMessage());
            scheduler = null;
        }
        
        // Create a Registry with the default port
        try {
            registry = LocateRegistry.createRegistry(port);
            System.out.println("Server: Registry succesfully created on port " + port + " !");
        } catch(RemoteException e) {
            System.out.println("Server: Unable to create Registry");
            System.out.println("Server: Exception Message: " + e.getMessage());
            registry = null;
        }
        
        // Bind the Scheduler using the Registry
        try {
            registry.rebind(bindingName, scheduler);
            System.out.println("Server: Scheduler succesfully binded!");
        } catch(RemoteException e) {
            System.out.println("Server: Unable to bind Scheduler");
            System.out.println("Server: Exception Message: " + e.getMessage());
        }
        
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            System.out.println("Server: IP: " + localhost.getHostAddress());
            InetAddress[] allMyIps;
            
            allMyIps = InetAddress.getAllByName(localhost.getCanonicalHostName());
            
            if(allMyIps != null && allMyIps.length > 1) {
                System.out.println("Server: Complete list of IP's: ");
                for(InetAddress ip : allMyIps) {
                    System.out.println("- " + ip);
                }
            }
        } catch(UnknownHostException e) {
            System.out.println("Server: Unknown Host!");
            System.out.println("Server: Exception Message: " + e.getMessage());
        }
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SchedulerApplicationServer server = new SchedulerApplicationServer();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
