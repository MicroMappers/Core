package qa.qcri.mm.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qa.qcri.mm.api.dao.TaskQueueDao;
import qa.qcri.mm.api.dao.TaskQueueResponseDao;
import qa.qcri.mm.api.entity.ClientApp;
import qa.qcri.mm.api.entity.TaskQueue;
import qa.qcri.mm.api.entity.TaskQueueResponse;
import qa.qcri.mm.api.service.ClientAppService;
import qa.qcri.mm.api.service.TaskQueueService;
import qa.qcri.mm.api.store.StatusCodeType;

import java.util.ArrayList;
import java.util.List;

@Service("taskStatusLookUpService")
@Transactional(readOnly = true)
public class TaskQueueServiceImpl implements TaskQueueService {

    @Autowired
    private TaskQueueDao taskQueueDao;

    @Autowired
    private TaskQueueResponseDao taskQueueResponseDao;

    @Autowired
    private ClientAppService clientAppService;

    @Override
    public List<TaskQueue> getTaskQueueSet(Long taskID, Long clientAppID, Long documentID) {
        return taskQueueDao.findTaskQueue(taskID,clientAppID, documentID );  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TaskQueue> getTaskQueueByDocument(Long clientAppID, Long documentID) {
        return taskQueueDao.findTaskQueueByDocument(clientAppID, documentID );  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TaskQueue> getTaskQueueByStatus(String column, Integer status) {
        return taskQueueDao.findTaskQueueByStatus(column,status);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TaskQueue> getTaskQueueByClientAppStatus(Long clientAppID, Integer status) {
        return taskQueueDao.findTaskQueueSetByStatus(clientAppID, status);  //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public Integer getCountTaskQeueByStatus(String column, Integer status) {
        return taskQueueDao.findTaskQueueByStatus(column,status).size();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Integer getCountTaskQeueByStatusAndClientApp(Long clientAppID, Integer status) {
        List<TaskQueue> taskQueueList = taskQueueDao.findTaskQueueSetByStatus(clientAppID, status);
        if(taskQueueList!=null)
            return taskQueueList.size();  //To change body of implemented methods use File | Settings | File Templates.
        return 0;
    }

    @Override
    public List<TaskQueue> getTaskQueueByClientApp(Long clientAppID) {
        return taskQueueDao.findTaskQueueSetByclientApp(clientAppID);
    }

    @Override
    public List<TaskQueue> getTotalNumberOfTaskQueue(Long clientAppID) {
        return taskQueueDao.findTotalTaskQueueSet(clientAppID);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TaskQueueResponse> getTaskQueueResponseByClientApp(String shortName){
        ClientApp app = clientAppService.findClientAppByCriteria("shortName", shortName);

        List<TaskQueue> taskQueues =  this.getTaskQueueByClientAppStatus(app.getClientAppID(), StatusCodeType.TASK_LIFECYCLE_COMPLETED);

        List<TaskQueueResponse> responses = new ArrayList<TaskQueueResponse>();

        for(TaskQueue taskQ : taskQueues){
            List<TaskQueueResponse> taskQueueResponse = taskQueueResponseDao.getTaskQueueResponseByTaskQueueID(taskQ.getTaskQueueID());
            if(taskQueueResponse.size() > 0){
                TaskQueueResponse thisTaskResponse = taskQueueResponse.get(0);
                String infoOutput = thisTaskResponse.getResponse();

                if(infoOutput!= null && !infoOutput.isEmpty()){
                    responses.add(thisTaskResponse) ;
                }
            }
        }
        return responses;
    }


}
