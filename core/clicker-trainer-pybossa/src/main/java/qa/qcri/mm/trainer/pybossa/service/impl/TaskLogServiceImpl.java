package qa.qcri.mm.trainer.pybossa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qa.qcri.mm.trainer.pybossa.dao.TaskLogDao;
import qa.qcri.mm.trainer.pybossa.entity.TaskLog;
import qa.qcri.mm.trainer.pybossa.service.TaskLogService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jilucas
 * Date: 9/28/13
 * Time: 1:36 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("taskLogService")
@Transactional(readOnly = true)
public class TaskLogServiceImpl implements TaskLogService {

    @Autowired
    private TaskLogDao taskLogDao;

    @Override
    @Transactional(readOnly = false)
    public void createTaskLog(TaskLog taskLog) {
        try{
            taskLogDao.save(taskLog);
        }
        catch(Exception ex){
            System.out.println("createTaskLog exception : " + ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void updateTaskLog(TaskLog taskLog) {
        try{
            taskLogDao.saveOrUpdate(taskLog);
        }
        catch(Exception ex){
            System.out.println("updateTaskLog Exception: " + ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public List<TaskLog> getTaskLog(Long taskQueueID) {

        return taskLogDao.getTaskLog(taskQueueID); //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TaskLog> getTaskLogByStatus(Long taskQueueID, int status) {
        return taskLogDao.getTaskLogByStatus(taskQueueID, status);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteAbandonedTaskLog(Long taskQueueID) {
        taskLogDao.deleteTaskLog(taskQueueID);
    }

}
