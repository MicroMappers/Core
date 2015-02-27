package qa.qcri.mm.api.service;


import qa.qcri.mm.api.entity.TaskQueue;
import qa.qcri.mm.api.entity.TaskQueueResponse;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jilucas
 * Date: 9/25/13
 * Time: 9:26 AM
 * To change this template use File | Settings | File Templates.
 */
public interface TaskQueueService {

    List<TaskQueue> getTaskQueueSet(Long taskID, Long clientAppID, Long documentID);
    List<TaskQueue> getTaskQueueByDocument(Long clientAppID, Long documentID);
    List<TaskQueue> getTaskQueueByStatus(String column, Integer status);
    List<TaskQueue> getTaskQueueByClientAppStatus(Long clientAppID, Integer status);
    Integer getCountTaskQeueByStatus(String column, Integer status);
    Integer getCountTaskQeueByStatusAndClientApp(Long clientAppID, Integer status);
    List<TaskQueue> getTaskQueueByClientApp(Long clientAppID);
    List<TaskQueue> getTotalNumberOfTaskQueue(Long clientAppID);
    List<TaskQueueResponse> getTaskQueueResponseByClientApp(String shortName);

}
