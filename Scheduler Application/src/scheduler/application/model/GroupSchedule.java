/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.application.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Noah Scharrenberg
 */
public class GroupSchedule extends Schedule implements Serializable {
    private List<Account> members;
    private List<Account> writers;
    
    public GroupSchedule(int id, Account owner, String name, Timestamp createdAt) {
        super(id, owner, name, createdAt);
    }
    
    public GroupSchedule(Account owner, String name) {
        super(owner, name);
    }
    
    public List<Account> getMembers() {
        return this.members;
    }
    
    public List<Account> getWriters() {
        return this.writers;
    }
    
    public void addMember(Account value) {
        this.members.add(value);
    }
    
    public void addWriter(Account value) {
        this.writers.add(value);
    }
    
    public void setMemberList(List<Account> value) {
        this.members = value;
    }
    
    public void setWriterList(List<Account> value) {
        this.writers = value;
    }
}
