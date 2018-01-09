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
import scheduler.application.dal.mysql.PersonalScheduleMySQLDao;
import scheduler.application.dal.repository.AccountRepository;
import scheduler.application.dal.repository.PersonalScheduleRepository;
import scheduler.application.model.Account;
import scheduler.application.model.GroupSchedule;
import scheduler.application.model.PersonalSchedule;
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
    
    // PersonalSchedule DAO
    static PersonalScheduleMySQLDao personalDao;
    PersonalScheduleRepository pr;
    
    public SchedulerServer() throws RemoteException {
        accountDao = new AccountMySQLDao();
        ar = new AccountRepository(accountDao);
        
        personalDao = new PersonalScheduleMySQLDao();
        pr = new PersonalScheduleRepository(personalDao);
    }

    @Override
    public PersonalSchedule getPersonalSchedule(int scheduleId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PersonalSchedule> getPersonalSchedules(int userId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<GroupSchedule> getGroupSchedules(int userId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addPersonalSchedule(Account owner, String name) throws RemoteException, SQLException, Exception {
        return pr.addPersonalSchedule(owner, name);
    }

    @Override
    public boolean addGroupSchedule(Account owner, String name) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addPersonalTask(String name, String description, Timestamp deadline) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addPersonalReminder(String name, String description, Timestamp startTime, Timestamp endTime) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void logout(int userId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public GroupSchedule getGroupSchedule(int scheduleId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean leaveGroupSchedule(int scheduleId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public boolean deletePersonalSchedule(int scheduleId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
