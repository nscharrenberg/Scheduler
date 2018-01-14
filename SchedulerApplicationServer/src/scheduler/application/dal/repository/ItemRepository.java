/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.application.dal.repository;

import java.sql.SQLException;
import java.util.List;
import scheduler.application.dal.interfaces.IItemInterface;
import scheduler.application.dal.interfaces.IPersonalScheduleInterface;
import scheduler.application.model.Reminder;
import scheduler.application.model.Task;

/**
 *
 * @author Noah Scharrenberg
 */
public class ItemRepository {
    private IItemInterface context;
    
    public ItemRepository(IItemInterface context) {
        this.context = context;
    }
    
    public List<Task> getTasks(int scheduleId) throws SQLException, Exception {
        return context.getTasks(scheduleId);
    }
    public List<Reminder> getReminders(int scheduleId) throws SQLException, Exception {
        return context.getReminders(scheduleId);
    }
}
