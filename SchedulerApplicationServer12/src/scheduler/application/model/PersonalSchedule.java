/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.application.model;

import java.sql.Timestamp;

/**
 *
 * @author Noah Scharrenberg
 */
public class PersonalSchedule extends Schedule {
    
    public PersonalSchedule(int id, Account owner, String name, Timestamp createdAt) {
        super(id, owner, name, createdAt);
    }
    
    public PersonalSchedule(Account owner, String name) {
        super(owner, name);
    }
}
