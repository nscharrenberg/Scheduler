/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.application.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Noah Scharrenberg
 */
public class Account implements Serializable {
    private int id;
    private String username;
    private String email;
    private String password;
    
    private List<PersonalSchedule> personalSchedules;
    private List<GroupSchedule> groupSchedules;
    
    public Account(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String value) {
        this.email = value;
    }
    
    public void setPassword(String value) {
        this.password = value;
    }
    
    public void setPersonalSchedules(List<PersonalSchedule> schedules) {
        this.personalSchedules = schedules;
    }
    
    public void setGroupSchedules(List<GroupSchedule> schedules) {
        this.groupSchedules = schedules;
    }
    
    public List<PersonalSchedule> getPersonalSchedules() {
        return personalSchedules;
    }
    
    public List<GroupSchedule> getGroupSchedules() {
        return groupSchedules;
    }
}
