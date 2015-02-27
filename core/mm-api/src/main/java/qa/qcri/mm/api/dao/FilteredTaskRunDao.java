package qa.qcri.mm.api.dao;

import qa.qcri.mm.api.entity.FilteredTaskRun;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 10/17/14
 * Time: 5:21 PM
 * To change this template use File | Settings | File Templates.
 */
public interface FilteredTaskRunDao extends AbstractDao<FilteredTaskRun, String>  {

    List<FilteredTaskRun> getTaskRunByTaskID(Long taskID);
    List<FilteredTaskRun> getTaskRunByUserID(Long userID);
}
