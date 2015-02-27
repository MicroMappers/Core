package qa.qcri.mm.api.dao;




import qa.qcri.mm.api.entity.TaskQueue;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jilucas
 * Date: 9/25/13
 * Time: 7:29 AM
 * To change this template use File | Settings | File Templates.
 */
public interface TaskQueueDao extends AbstractDao<TaskQueue, String>  {

    List<TaskQueue> findTaskQueue(Long taskID, Long clientAppID, Long documentID);
    List<TaskQueue> findTaskQueueByDocument(Long clientAppID, Long documentID);
    List<TaskQueue> findTaskQueueByStatus(String column, Integer status);
    List<TaskQueue> findTaskQueueSetByStatus(Long clientAppID, Integer status);
    List<TaskQueue> findTaskQueueSetByclientApp(Long clientAppID);
    List<TaskQueue> findTotalTaskQueueSet(Long clientAppID);
    List<TaskQueue> findLatestTaskQueue(Long clientAppID)  ;

}
