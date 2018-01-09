/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.application.model;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Noah Scharrenberg
 */
public abstract class Schedule {
    private int id;
    private Account owner;
    private String name;
    private Timestamp createdAt = null;
    
    // List of Items
    private List<Reminder> reminders;
    private List<Task> tasks;
    
    public Schedule(int id, Account owner, String name, Timestamp createdAt) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.createdAt = createdAt;
    }
    
    public Schedule(Account owner, String name) {
        this.owner = owner;
        this.name = name;
    }
    
    public int getId() {
        return this.id;
    }
    
    public Account getAccount() {
       return this.owner;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Timestamp getCreatedAt() {
        return this.createdAt;
    }
    
    public void setName(String value) {
        this.name = value;
    }
}
