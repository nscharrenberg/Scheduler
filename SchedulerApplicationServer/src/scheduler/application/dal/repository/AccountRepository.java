/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.application.dal.repository;

import java.sql.SQLException;
import scheduler.application.dal.interfaces.IAccountInterface;
import scheduler.application.model.Account;

/**
 *
 * @author Noah Scharrenberg
 */
public class AccountRepository {
    private IAccountInterface context;
    
    public AccountRepository(IAccountInterface context) {
        this.context = context;
    }
    
    public boolean register(String username, String email, String password, String confPassword) throws SQLException, Exception {
        return context.register(username, email, password, confPassword);
    }
    
    public Account login(String username, String password) throws SQLException, Exception {
        return context.login(username, password);
    }
    
    public Account findUserByUsername(String username) throws SQLException, Exception {
        return context.findUserByUsername(username);
    }
}
