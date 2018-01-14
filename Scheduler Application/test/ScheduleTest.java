/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Timestamp;
import java.util.Date;
import scheduler.application.model.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Noah Scharrenberg
 */
public class ScheduleTest {
    
    private PersonalSchedule personalSchedule;
    private GroupSchedule groupSchedule;
    private Account user;
    
    public ScheduleTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        user = new Account(1, "Noah", "test@mail.com", "banaan");
        personalSchedule = new PersonalSchedule(1, user, "My Personal Schedule", new Timestamp(new Date().getTime()));
        groupSchedule = new GroupSchedule(5, user, "My Group Schedule", new Timestamp(new Date().getTime()));
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetScheduleId() {
        System.out.println("- Starting testGetScheduleId -");
        int expected = 1;
        int actual = personalSchedule.getId();
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetName() {
        System.out.println("- Starting testGetName -");
        String expected = "My Personal Schedule";
        String actual = personalSchedule.getName();
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetAccount() {
        System.out.println("- Starting testGetAccount -");
        Account expected = user;
        Account actual = personalSchedule.getOwner();
        
        assertEquals(expected, actual);
    }  
    
    @Test
    public void testGetGroupScheduleId() {
        System.out.println("- Starting testGetGroupScheduleId -");
        int expected = 5;
        int actual = groupSchedule.getId();
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetGroupName() {
        System.out.println("- Starting testGetGroupName -");
        String expected = "My Group Schedule";
        String actual = groupSchedule.getName();
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetGroupAccount() {
        System.out.println("- Starting testGetGroupAccount -");
        Account expected = user;
        Account actual = groupSchedule.getOwner();
        
        assertEquals(expected, actual);
    } 
}
