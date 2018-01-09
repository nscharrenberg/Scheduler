/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.application.dal.interfaces;

import java.sql.SQLException;
import scheduler.application.model.Account;

/**
 *
 * @author Noah Scharrenberg
 */
public interface IAccountInterface {
    boolean register(String username, String email, String password, String confPassword) throws SQLException, Exception;
    Account login(String username, String password) throws SQLException, Exception;
}
