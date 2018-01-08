/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.application.dal.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Noah Scharrenberg
 */
public class ConnectionSetup {
    // Connection String
    private final String connectionString = "jdbc:mysql://nscharrenberg.nl:3306/nschar1q_scheduler?zeroDateTimeBehavior=convertToNull";
    private final String dbu = "nschar1q_scheduler";
    private final String dbp = "hC28H$#rpEUxp#45";
    
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    
    public ConnectionSetup() {
        
    }
    
    public boolean getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(connectionString, dbu, dbp);
            System.out.println("Connection with database established!");
            
            return true;
        } catch(ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public Connection getConn() {
        return this.conn;
    }
    
    public boolean closeConnection() {
        try {
            if(rs != null) {
                rs.close();
            }

            if(conn != null) {
                conn.close();
            }

            if(pstmt != null) {
                pstmt.close();
            }
            
            System.out.println("Database Connection closed!");
            
            return true;
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    
}
