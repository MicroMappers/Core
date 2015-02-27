package qa.qcri.mm.api.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import qa.qcri.mm.api.dao.FilteredTaskRunDao;
import qa.qcri.mm.api.entity.FilteredTaskRun;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 10/17/14
 * Time: 5:28 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class FilteredTaskRunDaoImpl extends AbstractDaoImpl<FilteredTaskRun,String> implements FilteredTaskRunDao {

    protected FilteredTaskRunDaoImpl(){
        super(FilteredTaskRun.class);
    }

    @Override
    public List<FilteredTaskRun> getTaskRunByTaskID(Long taskID) {
        return findByCriteria(Restrictions.eq("task_id", taskID)); //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<FilteredTaskRun> getTaskRunByUserID(Long userID) {
        return findByCriteria(Restrictions.eq("user_id", userID));  //To change body of implemented methods use File | Settings | File Templates.
    }
}
