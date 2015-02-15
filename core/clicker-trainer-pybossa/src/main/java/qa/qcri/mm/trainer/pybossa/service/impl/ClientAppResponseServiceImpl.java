package qa.qcri.mm.trainer.pybossa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qa.qcri.mm.trainer.pybossa.dao.ClientAppAnswerDao;
import qa.qcri.mm.trainer.pybossa.dao.TaskQueueResponseDao;
import qa.qcri.mm.trainer.pybossa.entity.ClientApp;
import qa.qcri.mm.trainer.pybossa.entity.ClientAppAnswer;
import qa.qcri.mm.trainer.pybossa.entity.TaskQueue;
import qa.qcri.mm.trainer.pybossa.entity.TaskQueueResponse;
import qa.qcri.mm.trainer.pybossa.service.ClientAppResponseService;
import qa.qcri.mm.trainer.pybossa.service.ClientAppService;
import qa.qcri.mm.trainer.pybossa.service.TaskQueueService;
import qa.qcri.mm.trainer.pybossa.store.StatusCodeType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 10/20/13
 * Time: 1:58 AM
 * To change this template use File | Settings | File Templates.
 */
@Service("ClientAppResponseService")
@Transactional(readOnly = true)
public class ClientAppResponseServiceImpl implements ClientAppResponseService {

    @Autowired
    private ClientAppAnswerDao clientAppAnswerDao;

    @Autowired
    private TaskQueueResponseDao taskQueueResponseDao;

    @Autowired
    private ClientAppService clientAppService;

    @Autowired
    private TaskQueueService taskQueueService;

    @Override
    public ClientAppAnswer getClientAppAnswer(Long clientAppID) {
        return clientAppAnswerDao.findClientAppAnswerByID(clientAppID);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = false)
    public void processTaskQueueResponse(TaskQueueResponse taskQueueResponse) {

        taskQueueResponseDao.addTaskQueueResponse(taskQueueResponse);
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TaskQueueResponse> getTaskQueueResponse(Long taskQueueID) {
        return taskQueueResponseDao.getTaskQueueResponse(taskQueueID);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TaskQueueResponse> getTaskQueueResponseByContent() {
        return taskQueueResponseDao.getTaskQueueResponseByContent();
    }

    @Override
    public List<TaskQueueResponse> getTaskQueueResponseByClientApp(String shortName) {
        ClientApp app = clientAppService.findClientAppByCriteria("shortName", shortName);
        List<TaskQueue> taskQueues =  taskQueueService.getTaskQueueByClientAppStatus(app.getClientAppID(), StatusCodeType.TASK_LIFECYCLE_COMPLETED);

        List<TaskQueueResponse> responses = new ArrayList<TaskQueueResponse>();

        for(TaskQueue taskQ : taskQueues){
            List<TaskQueueResponse> taskQueueResponse = taskQueueResponseDao.getTaskQueueResponse(taskQ.getTaskQueueID());
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

    @Override
    @Transactional(readOnly = false)
    public void saveClientAppAnswer(Long clientAppID, String answerJson, int cutOffValue) {
        clientAppAnswerDao.addClientAppAnswer(clientAppID, answerJson, cutOffValue);
    }

}
