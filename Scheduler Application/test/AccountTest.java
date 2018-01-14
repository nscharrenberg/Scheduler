/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
public class AccountTest {
    
    private Account user;
    private List<PersonalSchedule> personalSchedules;
    private List<GroupSchedule> groupSchedules;
    
    public AccountTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        // Init User
        user = new Account(1, "Noah", "test@mail.com", "banaan");
        
        personalSchedules = new ArrayList<>();
        groupSchedules = new ArrayList<>();
        
        // Init Personal Schedules
        PersonalSchedule ps1 = new PersonalSchedule(1, user, "My Personal Schedule", new Timestamp(new Date().getTime()));
        PersonalSchedule ps2 = new PersonalSchedule(6, user, "My Other Schedule", new Timestamp(new Date().getTime()));
        PersonalSchedule ps3 = new PersonalSchedule(3, user, "My Third Personal Schedule", new Timestamp(new Date().getTime()));
        personalSchedules.add(ps1);
        personalSchedules.add(ps2);
        personalSchedules.add(ps3);
        
        // init Group Schedules
        GroupSchedule gs1 = new GroupSchedule(1, user, "My Group Schedule", new Timestamp(new Date().getTime()));
        GroupSchedule gs2 = new GroupSchedule(2, user, "My Second Group Schedule", new Timestamp(new Date().getTime()));
        groupSchedules.add(gs1);
        groupSchedules.add(gs2);
        
        // Connect Schedules to User
        user.setPersonalSchedules(personalSchedules);
        user.setGroupSchedules(groupSchedules);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetUserName() {
        System.out.println("- Starting testGetUserName -");
        String expected = "Noah";
        String actual = user.getUsername();
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetEmail() {
        System.out.println("- Starting testGetEmail -");
        String expected = "test@mail.com";
        String actual = user.getEmail();
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetId() {
        System.out.println("- Starting testGetId -");
        int expected = 1;
        int actual = user.getId();
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void testSetEmail() {
        System.out.println("- Starting testSetEmail -");
        String expected = "noah@mail.com";
        user.setEmail("noah@mail.com");
        String actual = user.getEmail();
        
        assertEquals(expected, actual);
    }

    @Test
    public void testGetPersonalSchedules() {
        System.out.println("- Starting testGetPersonalSchedules -");
        List<PersonalSchedule> expected = personalSchedules;
        List<PersonalSchedule> actual = user.getPersonalSchedules();
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetGroupSchedules() {
        System.out.println("- Starting testGetGroupSchedules -");
        List<GroupSchedule> expected = groupSchedules;
        List<GroupSchedule> actual = user.getGroupSchedules();
        
        assertEquals(expected, actual);
    }
}
