/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import scheduler.application.dal.mysql.AccountMySQLDao;
import scheduler.application.dal.mysql.ItemMySQLDao;
import scheduler.application.dal.mysql.PersonalScheduleMySQLDao;
import scheduler.application.dal.repository.AccountRepository;
import scheduler.application.dal.repository.ItemRepository;
import scheduler.application.dal.repository.PersonalScheduleRepository;
import scheduler.application.model.Account;
import scheduler.application.model.GroupSchedule;
import scheduler.application.model.PersonalSchedule;
import scheduler.application.model.Reminder;
import scheduler.application.model.Task;
import scheduler.application.rmi.interfaces.IReadSchedule;
import scheduler.application.rmi.interfaces.IUser;
import scheduler.application.rmi.interfaces.IVisitor;
import scheduler.application.rmi.interfaces.IWriteSchedule;

/**
 *
 * @author Noah Scharrenberg
 */
public class SchedulerServer extends UnicastRemoteObject implements IUser, IVisitor, IReadSchedule, IWriteSchedule {
    private List<Account> users;
    
    // Account DAO
    static AccountMySQLDao accountDao;
    AccountRepository ar;
    
    static ItemMySQLDao itemDao;
    ItemRepository ir;
    
    // PersonalSchedule DAO
    static PersonalScheduleMySQLDao personalDao;
    PersonalScheduleRepository pr;
    
    public SchedulerServer() throws RemoteException {
        accountDao = new AccountMySQLDao();
        ar = new AccountRepository(accountDao);
        
        personalDao = new PersonalScheduleMySQLDao();
        pr = new PersonalScheduleRepository(personalDao);
        
        itemDao = new ItemMySQLDao();
        ir = new ItemRepository(itemDao);
    }

    @Override
    public List<PersonalSchedule> getPersonalSchedules(Account user) throws RemoteException, SQLException, Exception {
       return pr.getPersonalSchedules(user);
    }

    @Override
    public List<GroupSchedule> getGroupSchedules(Account user) throws RemoteException, SQLException, Exception {
        return pr.getGroupSchedules(user);
    }

    @Override
    public boolean addPersonalSchedule(Account owner, String name) throws RemoteException, SQLException, Exception {
        return pr.addPersonalSchedule(owner, name);
    }

    @Override
    public boolean addGroupSchedule(Account owner, String name) throws RemoteException, SQLException, Exception {
        return pr.addGroupSchedule(owner, name);
    }

    @Override
    public boolean addPersonalTask(String name, String description, Timestamp deadline, int schedule) throws RemoteException, SQLException, Exception {
        return pr.addPersonalTask(name, description, deadline, schedule);
    }

    @Override
    public boolean addPersonalReminder(String name, String description, Timestamp startTime, Timestamp endTime, int schedule) throws RemoteException, SQLException, Exception {
        return pr.addPersonalReminder(name, description, startTime, endTime, schedule);
    }

    @Override
    public void logout(int userId) throws RemoteException, SQLException, Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletePersonalSchedule(int scheduleId) throws RemoteException, SQLException, Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PersonalSchedule getPersonalSchedule(Account user, int scheduleId) throws RemoteException, SQLException, Exception {
        return pr.getPersonalSchedule(user, scheduleId);
    }

    @Override
    public Account login(String username, String password) throws RemoteException, SQLException, Exception {
        return ar.login(username, password);
    }

    @Override
    public boolean register(String username, String email, String password, String confPassword) throws RemoteException, SQLException, Exception {
        return ar.register(username, email, password, confPassword);
    }

    @Override
    public GroupSchedule getGroupSchedule(Account user, int scheduleId) throws RemoteException, SQLException, Exception {
        return pr.getGroupSchedule(user, scheduleId);
    }

    @Override
    public boolean leaveGroupSchedule(int scheduleId) throws RemoteException, SQLException, Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Task> getTasks(int ScheduleId) throws RemoteException, SQLException, Exception {
        System.out.println("SchedulerServer: " + ScheduleId);
        return ir.getTasks(ScheduleId);
    }

    @Override
    public List<Reminder> getReminders(int scheduleId) throws RemoteException, SQLException, Exception {
        return ir.getReminders(scheduleId);
    }

    @Override
    public boolean addGroupTask(String name, String description, Timestamp deadline) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addGroupReminder(String name, String description, Timestamp startTime, Timestamp endTime) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addMemberByUsername(String username) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addMemberByEmail(String email) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addWriterByUsername(String username) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addWriterByEmail(String email) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeMember(int userId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeWriter(int userId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateSchedule(GroupSchedule schedule) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteSchedule(int scheduleId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Account findUserByUsername(String username) throws SQLException, Exception {
        return ar.findUserByUsername(username);
    }

    @Override
    public boolean addMember(String username, int schedule, boolean writer) throws SQLException, Exception {
        return pr.addMember(username, schedule, writer);
    }

    
    
}
