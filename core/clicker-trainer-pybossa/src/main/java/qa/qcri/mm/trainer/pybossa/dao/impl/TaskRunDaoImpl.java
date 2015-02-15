package qa.qcri.mm.trainer.pybossa.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import qa.qcri.mm.trainer.pybossa.dao.TaskRunDao;
import qa.qcri.mm.trainer.pybossa.entity.TaskRun;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 9/30/14
 * Time: 8:08 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class TaskRunDaoImpl extends AbstractDaoImpl<TaskRun, String> implements TaskRunDao {

    protected TaskRunDaoImpl(){
        super(TaskRun.class);
    }

    @Override
    public List<TaskRun> getAllUserID() {

        return findAllByKey("user_id", Restrictions.isNull("updateInfo"));
        //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public List<TaskRun> getTaskRunByUserID(Long userID) {
        return findByCriteria(Restrictions.eq("user_id", userID));
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateTaskRun(Long id, String updateInfo, String duplicateInfo) {
        List<TaskRun> r= findByCriteria(Restrictions.eq("id", id));
        if(r.size() > 0){
            TaskRun tr =  r.get(0);
            tr.setUpdateInfo(updateInfo);
            tr.setDuplicateInfo(duplicateInfo);
            saveOrUpdate(tr);
        }
        //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public List<TaskRun> getTaskRunbyTaskID(long task_id) {

        return findByCriteria(Restrictions.eq("task_id", task_id));

    }

    @Override
    public List<TaskRun> getProcessedTaskRunbyTaskID(long task_id) {
        return findByCriteria(Restrictions.conjunction()
                .add(Restrictions.eq("task_id",task_id))
                .add(Restrictions.isNotNull("duplicateInfo")));


    }


}
