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
public class Task extends Item {
    private Timestamp deadline;

    public Task(int id, String name, String description, Timestamp deadline) {
        super(id, name, description);
        
        this.deadline = deadline;
    }
}
