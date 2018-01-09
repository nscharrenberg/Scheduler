/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.application.model;

import java.io.Serializable;

/**
 *
 * @author Noah Scharrenberg
 */
public abstract class Item implements Serializable {
    private int id;
    private String name;
    private String description;
    
    public Item(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setName(String value) {
        this.name = value;
    }
    
    public void setDescription(String value) {
        this.description = value;
    }
}
