/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.application.dal.interfaces;

import java.sql.SQLException;
import java.util.List;
import scheduler.application.model.Account;
import scheduler.application.model.PersonalSchedule;
import scheduler.application.model.Reminder;
import scheduler.application.model.Task;

/**
 *
 * @author Noah Scharrenberg
 */
public interface IItemInterface {
    List<Task> getTasks(int scheduleId) throws SQLException, Exception;
    List<Reminder> getReminders(int scheduleId) throws SQLException, Exception;
}
