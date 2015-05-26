package qa.qcri.mm.api.service.impl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qa.qcri.mm.api.dao.TaskQueueResponseDao;
import qa.qcri.mm.api.entity.*;
import qa.qcri.mm.api.service.ClientAppAnswerService;
import qa.qcri.mm.api.service.ClientAppService;
import qa.qcri.mm.api.service.MicroMapsService;
import qa.qcri.mm.api.service.TaskQueueService;
import qa.qcri.mm.api.store.StatusCodeType;
import qa.qcri.mm.api.template.MicroMapsCrisisModel;
import qa.qcri.mm.api.template.CrisisGISModel;
import qa.qcri.mm.api.dao.CrisisDao;
import qa.qcri.mm.api.dao.MarkerStyleDao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 4/22/15
 * Time: 11:31 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("microMapsService")
@Transactional(readOnly = true)
public class MicroMapsServiceImpl implements MicroMapsService {

    @Autowired
    ClientAppService clientAppService;

    @Autowired
    ClientAppAnswerService clientAppAnswerService;

    @Autowired
    TaskQueueResponseDao taskQueueResponseDao;

    @Autowired
    CrisisDao crisisDao;

    @Autowired
    MarkerStyleDao markerStyleDao;

    @Autowired
    TaskQueueService taskQueueService;

    private JSONParser parser = new JSONParser();


    @Override
    public List<MicroMapsCrisisModel> getAllCries() {
        List<MicroMapsCrisisModel> models = new ArrayList<MicroMapsCrisisModel>();
        List<ClientApp> clientAppList = clientAppService.getAllClientApp();

        for(ClientApp c : clientAppList){

            String appType = getClientAppType(c.getAppType());
            int appStatus = getClientAppStatus(c.getStatus());
            JSONArray category = this.getClientAppCategory(c.getClientAppID());
            String geoJsonLink = "";
            String kmlLink="";

            models.add(new MicroMapsCrisisModel(c.getName(),c.getCreated().toString(), appType, geoJsonLink, kmlLink,category, appStatus ));
        }

        return models;
    }

    //public CrisisGISModel(Long clientAppID, String name, String type, String activationStarted, String activationEnded, String geoJsonLink, String kmlLink)
    @Override
    public List<CrisisGISModel> getAllCrisis() throws Exception {

        List<Crisis> crisises = crisisDao.getAllCrisis();
        List<CrisisGISModel> models = new ArrayList<CrisisGISModel>();

        String filePath = "http://aidr-prod.qcri.org/data/trainer/";

        for(Crisis c : crisises){
            String geoJson = filePath + File.separator + c.getClientAppID() + ".json";
            String kml = filePath + File.separator + c.getClientAppID() + ".kml";
            MarkerStyle aStyle = this.getClientAppMarkerStyle(c);
            JSONObject aStyleJson = (JSONObject)parser.parse(aStyle.getStyle());
            CrisisGISModel aModel = new CrisisGISModel(c.getClientAppID(),c.getDisplayName(), c.getClickerType(), c.getActivationStart(),
                    c.getActivationEnd(),geoJson, kml, aStyleJson);

            models.add(aModel);
        }
        return models;
    }


    @Override
    public JSONArray getAllCrisisJSONP() throws Exception {

        List<Crisis> crisises = crisisDao.getAllCrisis();
        JSONArray models = new JSONArray();

        String filePath = "http://aidr-prod.qcri.org/data/trainer/";

        for(Crisis c : crisises){
            String geoJson = filePath + File.separator + c.getClientAppID() + ".json";
            String kml = filePath + File.separator + c.getClientAppID() + ".kml";
            MarkerStyle aStyle = this.getClientAppMarkerStyle(c);
            JSONObject aObject = new JSONObject();
            aObject.put("clientAppID",c.getClientAppID()) ;
            aObject.put("name",c.getDisplayName()) ;
            aObject.put("type",c.getClickerType()) ;
            aObject.put("activationStart",c.getActivationStart().toString()) ;
            aObject.put("activationEnd",c.getActivationEnd().toString()) ;
            aObject.put("geoJsonLink",geoJson) ;
            aObject.put("kmlLink",kml) ;
            JSONObject aStyleJson = (JSONObject)parser.parse(aStyle.getStyle());
            aObject.put("style",aStyleJson) ;

            models.add(aObject);
        }
        return models;
    }


    @Override
    public JSONObject getGeoClickerByClientApp(Long clientAppID) throws Exception{

        System.out.println("clientAppID : " + clientAppID);

        List<Crisis> crisises = crisisDao.findCrisisByClientAppID(clientAppID);
        ClientApp clientApp = clientAppService.findClientAppByID("clientAppID", clientAppID);

        JSONObject geoClickerOutput = new JSONObject();
        JSONArray features = new JSONArray();


        if(crisises.size() > 0)
        {
            Crisis c =  crisises.get(0);
        }

        System.out.println("crisis :" + clientApp.getName());

        List<TaskQueue> taskQueueList = taskQueueService.getTaskQueueByClientAppStatus(clientAppID, StatusCodeType.TASK_LIFECYCLE_COMPLETED);

        System.out.println("taskQueueList :" + taskQueueList.size());

        for(TaskQueue t: taskQueueList){

            List<TaskQueueResponse> responses = taskQueueResponseDao.getTaskQueueResponseByTaskQueueID(t.getTaskQueueID());

            if(responses.size() > 0 ){
                if(!responses.get(0).getResponse().equalsIgnoreCase("{}") && !responses.get(0).getResponse().equalsIgnoreCase("[]")){
                    JSONArray eachFeature = (JSONArray)parser.parse(responses.get(0).getResponse());
                    features.add(eachFeature);
                }
            }

        }

        System.out.print("features : " + features.toJSONString());

        geoClickerOutput.put("type", "FeatureCollection");
        geoClickerOutput.put("features", features);

        return geoClickerOutput;
    }

    private MarkerStyle getClientAppMarkerStyle(Crisis c){
        List<MarkerStyle> styles = markerStyleDao.findByClientAppID(c.getClientAppID().longValue());

        if(styles.isEmpty()){
            styles = markerStyleDao.findByAppType(c.getClickerType());
        }

        if(styles.size() > 0){
            return styles.get(0);
        }

        return null;
    }

    private String getClientAppType (int type){
        String appType = null;
        switch (type) {
            case 1:  appType = "Text";
                break;
            case 2:  appType = "Image";
                break;
            case 3:  appType = "Video";
                break;
            case 4:  appType = "Geo";
                break;
            case 5:  appType = "Aerial";
                break;
            case 6:  appType = "3W";
                break;

        }

        return appType;
    }

    private int getClientAppStatus (int status){
        int appStatus = 0;
        switch (status) {
            case 1:  appStatus = 1;
                break;
            case 2:  appStatus = 1;
                break;
            case 5:  appStatus = 1;
                break;
            default:  appStatus = 0;
                break;
        }

        return appStatus;
    }

    private JSONArray getClientAppCategory(Long clientAppID){
        ClientAppAnswer answer =  clientAppAnswerService.getClientAppAnswer(clientAppID);
        String ans = answer.getAnswerMarkerInfo();

        JSONArray jsonArray = null;

        JSONParser parser = new JSONParser();

        try {

            jsonArray =  (JSONArray)parser.parse(ans);

        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return jsonArray;
    }
}
