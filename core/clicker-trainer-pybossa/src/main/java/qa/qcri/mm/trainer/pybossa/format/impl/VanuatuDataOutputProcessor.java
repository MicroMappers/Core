package qa.qcri.mm.trainer.pybossa.format.impl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import qa.qcri.mm.trainer.pybossa.entity.ClientApp;
import qa.qcri.mm.trainer.pybossa.entity.TaskQueue;
import qa.qcri.mm.trainer.pybossa.entity.TaskQueueResponse;
import qa.qcri.mm.trainer.pybossa.service.ClientAppResponseService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 11/10/14
 * Time: 3:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class VanuatuDataOutputProcessor extends DataProcessor {

    public VanuatuDataOutputProcessor(ClientApp clientApp) {
        super(clientApp);
    }


    @Override
    public TaskQueueResponse process(String datasource, TaskQueue taskQueue) throws Exception {
        if(this.clientApp == null)
            return null;

        this.datasource = datasource;
        this.taskQueue = taskQueue;

        TaskQueueResponse taskQueueResponse = null;

        try{

            JSONArray array = (JSONArray) parser.parse(this.datasource) ;

            if(array.size() > 0) {
                Iterator itr= array.iterator();

                JSONArray locations  =  new JSONArray();

                String tweetID = null;

                while(itr.hasNext()){
                    JSONObject featureJsonObj = (JSONObject)itr.next();

                    //String featureJsonObjString = (String)featureJsonObj.get("info");

                    JSONObject info = (JSONObject)featureJsonObj.get("info");
                    JSONArray loc = (JSONArray)info.get("loc");

                    if(!loc.isEmpty() && loc.size() > 0){
                        JSONObject a = new JSONObject();
                        a.put("geo",info.get("loc") )  ;
                        a.put("userID", featureJsonObj.get("user_id"));
                        locations.add(a) ;

                    }
                }

                JSONObject finalAnswer = new JSONObject();
                finalAnswer.put("geo", locations);
                finalAnswer.put("taskid", this.taskQueue.getTaskID());
                finalAnswer.put("imgurl", this.getStringValueFromInfoJson(array, "imgurl"));
                finalAnswer.put("bounds", this.getStringValueFromInfoJson(array, "geo"));

                System.out.println("ans: " + finalAnswer.toJSONString() );

                taskQueueResponse = new TaskQueueResponse(this.taskQueue.getTaskQueueID(), finalAnswer.toJSONString(), tweetID);

            }
        }
        catch(Exception e){
            System.out.println("Exception e : " + e) ;
            taskQueueResponse = null;

        }

        return taskQueueResponse;

    }

    @Override
    public List<TaskQueueResponse> generateMapOuput(List<TaskQueue> taskQueues, ClientAppResponseService clientAppResponseService) throws Exception {
        List<TaskQueueResponse> responses = new ArrayList<TaskQueueResponse>();

        for(TaskQueue taskQ : taskQueues){
            List<TaskQueueResponse> taskQueueResponse = clientAppResponseService.getTaskQueueResponse(taskQ.getTaskQueueID());
            if(taskQueueResponse.size() > 0){
                TaskQueueResponse thisTaskResponse = taskQueueResponse.get(0);
                String infoOutput = thisTaskResponse.getResponse();

                if(infoOutput!= null && !infoOutput.isEmpty()){
                    responses.add(thisTaskResponse) ;
                }
            }
        }
        return responses;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private String getStringValueFromInfoJson(JSONArray array, String propertyName) throws Exception{
        JSONObject response = (JSONObject)array.get(0);
        JSONObject answer = (JSONObject)response.get("info");

        return (String)answer.get(propertyName);
    }

}
