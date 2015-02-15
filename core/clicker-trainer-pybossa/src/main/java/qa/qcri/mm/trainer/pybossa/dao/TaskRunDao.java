package qa.qcri.mm.trainer.pybossa.dao;

import qa.qcri.mm.trainer.pybossa.entity.TaskRun;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 9/30/14
 * Time: 8:04 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TaskRunDao extends AbstractDao<TaskRun, String>{

    List<TaskRun> getTaskRunByUserID(Long userID);
    void updateTaskRun(Long id, String updateInfo, String duplicateInfo);
    List<TaskRun> getAllUserID();
    List<TaskRun> getTaskRunbyTaskID(long task_id);
    List<TaskRun> getProcessedTaskRunbyTaskID(long task_id);
}
