package qa.qcri.mm.trainer.pybossa.format.impl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import qa.qcri.mm.trainer.pybossa.dao.CrisisDao;
import qa.qcri.mm.trainer.pybossa.dao.ImageMetaDataDao;
import qa.qcri.mm.trainer.pybossa.dao.MarkerStyleDao;
import qa.qcri.mm.trainer.pybossa.entity.*;
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

    @Autowired
    ImageMetaDataDao imageMetaDataDao;


    public VanuatuDataOutputProcessor(ClientApp clientApp) {
        super(clientApp);
    }

    public void setImageMetaDataDao(ImageMetaDataDao imageMetaDataDao) {
        this.imageMetaDataDao = imageMetaDataDao;
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
            JSONArray taskQueueResJsonArray = new JSONArray();
            if(array.size() > 0) {
                Iterator itr= array.iterator();



                String tweetID = null;
                String imgURL = (String)this.getStringValueFromInfoJson(array, "imgurl");


                JSONObject finalProperties = new JSONObject();
                finalProperties.put("imgURL", imgURL);
                finalProperties.put("bounds", this.getStringValueFromInfoJson(array, "geo"));
                finalProperties.put("taskid", this.taskQueue.getTaskID());

                JSONObject features = this.getFeature(imgURL);

                JSONArray locations  =  new JSONArray();
                while(itr.hasNext()){
                    JSONObject featureJsonObj = (JSONObject)itr.next();

                    JSONObject info = (JSONObject)featureJsonObj.get("info");
                    JSONArray loc = (JSONArray)info.get("loc");
                    this.getProperties(loc, info, locations) ;
                }

                finalProperties.put("features", locations);

                System.out.println("ans: " + finalProperties.toJSONString() );

                features.put("properties", finalProperties) ;

                System.out.println("ans: " + features.toJSONString() );

                if(locations.size() > 0){
                    taskQueueResJsonArray.add(features)  ;
                }

                taskQueueResponse = new TaskQueueResponse(this.taskQueue.getTaskQueueID(), taskQueueResJsonArray.toJSONString(), tweetID);

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

    private Object getStringValueFromInfoJson(JSONArray array, String propertyName) throws Exception{
        JSONObject response = (JSONObject)array.get(0);
        JSONObject answer = (JSONObject)response.get("info");

        return answer.get(propertyName);
    }

    private JSONObject getFeature(String imgURL){
        List<ImageMetaData> imageMetaDataList = imageMetaDataDao.findImageMetaDataByImageURL(imgURL);
        JSONObject features = new JSONObject();


        if(imageMetaDataList.size() > 0){
            features.put("type", "Feature") ;

            JSONObject geometry = new JSONObject();

            geometry.put("type", "Point") ;
            JSONArray latlng = new JSONArray();

            ImageMetaData aData = imageMetaDataList.get(0);

            latlng.add(Double.valueOf(aData.getLat())) ;
            latlng.add(Double.valueOf(aData.getLng())) ;

            geometry.put("coordinates", latlng) ;

            features.put("geometry", geometry) ;

        }
        System.out.println("***** : " + features.toJSONString());
        return features;

    }

    private JSONArray getProperties(JSONArray loc, JSONObject info, JSONArray locations){

        try{
            if(!loc.isEmpty() && loc.size() > 0){
                Iterator itr= loc.iterator();
                while(itr.hasNext()){
                    JSONObject featureJsonObj = (JSONObject)itr.next();
                    JSONObject layer = (JSONObject)featureJsonObj.get("layer");
                    String layerType = (String)featureJsonObj.get("layerType");


                    JSONObject properties  =  (JSONObject)layer.get("properties");

                    properties.put("layerType",layerType);

                    if(style.size() > 0){
                        JSONObject theStyleTemplate = (JSONObject)parser.parse(style.get(0).getStyle()) ;

                        JSONArray styles = (JSONArray)theStyleTemplate.get("style");
                        for(int i=0; i < styles.size(); i++){
                            JSONObject aStyle  = (JSONObject)styles.get(i);
                            String lable_code = (String)aStyle.get("label_code");
                            if(lable_code.equalsIgnoreCase(layerType)){
                                properties.put("label",layerType);
                                properties.put("style", aStyle);
                            }
                        }

                    }

                    properties.put("userID", info.get("user_id"));

                    layer.remove("bounds");
                    layer.remove("taskid");
                    layer.remove("imgURL");

                    locations.add(layer) ;
                }
            }

        }
        catch (Exception e){
            System.out.println("exception : getProperties - " + e.getMessage());
        }

        return locations;
    }
}
