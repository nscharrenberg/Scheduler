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
public class Reminder extends Item {
    private Timestamp startTime;
    private Timestamp endTime;

    public Reminder(int id, String name, String description, Timestamp startTime, Timestamp endTime) {
        super(id, name, description);
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    public Timestamp getStartTime() {
        return this.startTime;
    }
    
    public Timestamp getEndTime() {
        return this.endTime;
    }
    
    public void setStartTime(Timestamp value) {
        this.startTime = value;
    }
    
    public void setEndTime(Timestamp value) {
        this.endTime = value;
    }
}
