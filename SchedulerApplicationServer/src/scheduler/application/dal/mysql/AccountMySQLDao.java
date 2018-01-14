/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.application.dal.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import scheduler.application.dal.interfaces.IAccountInterface;
import scheduler.application.model.Account;

/**
 *
 * @author Noah Scharrenberg
 */
public class AccountMySQLDao implements IAccountInterface {
    private Connection sqlConn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private ConnectionSetup conn = new ConnectionSetup();
    
    /*
    * SQL Queries
    */
    static final String GET_USER_LOGIN_INFO = "SELECT * FROM account WHERE username = ? AND password = ?";
    static final String GET_USER_FROM_USER_ID = "SELECT * FROM account WHERE id = ?";
    static final String GET_USER_FROM_USERNAME = "SELECT * FROM account WHERE username = ?";
    static final String GET_USER_FROM_EMAIL = "SELECT * FROM account WHERE email = ?";
    static final String GET_USER_FROM_PASSWORD = "SELECT * FROM account WHERE password = ?";
    static final String ADD_NEW_ACCOUNT = "INSERT INTO account(username, email, password) VALUES(?, ?, ?)";

    @Override
    public boolean register(String username, String email, String password, String confPassword) throws SQLException, Exception {
           if(!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confPassword.isEmpty()) {
                if(!password.equals(confPassword)) {
                    System.out.println("Output: No Matching Passwords");
                    throw new Exception("Passwords do not match!");
                } else if(password.length() <= 5) {
                    System.out.println("Password not long enough");
                    throw new Exception("Password must be longer then 5 characters!");
                } else {
                    conn.getConnection();
                    sqlConn = conn.getConn();
                    pstmt = sqlConn.prepareStatement(ADD_NEW_ACCOUNT);
                    pstmt.setString(1, username);
                    pstmt.setString(2, email);
                    pstmt.setString(3, password);

                    if(pstmt.executeUpdate() > 0) {
                        System.out.println("Account has been created. Your username is: " + username);

                        return true;
                    } else {
                        System.out.println("Account could NOT be created! Please try again!");
                        return false;
                    }
                }
            } else {
                System.out.println("Output: Empty field");
                throw new Exception("A field cannot be empty!");
            }
    }
    
    @Override
    public Account findUserByUsername(String username) throws SQLException, Exception {
        Account account = null;
        try {
            conn.getConnection();
            sqlConn = conn.getConn();

            pstmt = sqlConn.prepareStatement(GET_USER_FROM_USERNAME);
            pstmt.setString(1, username);

            rs = pstmt.executeQuery();
            rs.next();
        
            int uid = rs.getInt("id");
            String un = rs.getString("username");
            String ue = rs.getString("email");

            account = new Account(uid, un, ue);
        } catch(SQLException e) {
            throw new Exception("Incorrect username and/or password");
        } finally {
            conn.closeConnection();
        }
        
        
        return account;
    }

    @Override
    public Account login(String username, String password) throws SQLException, Exception {
        Account account = null;
        try {
            conn.getConnection();
            sqlConn = conn.getConn();

            pstmt = sqlConn.prepareStatement(GET_USER_LOGIN_INFO);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            rs = pstmt.executeQuery();
            rs.next();
        
            int uid = rs.getInt("id");
            String un = rs.getString("username");
            String ue = rs.getString("email");
            String up = rs.getString("password");

            account = new Account(uid, un, ue, up);
            System.out.println("User logged in as " + username + " !");
        
        } catch(SQLException e) {
            throw new Exception("Incorrect username and/or password");
        } finally {
            conn.closeConnection();
        }
        
        
        return account;
    }

    @Override
    public void logout(int userId) throws SQLException, Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
