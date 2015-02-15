package qa.qcri.mm.drone.api.service.impl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qa.qcri.mm.drone.api.dao.DroneReportDao;
import qa.qcri.mm.drone.api.dao.DroneTrackerDao;
import qa.qcri.mm.drone.api.entity.DroneTracker;
import qa.qcri.mm.drone.api.service.DroneTrackerService;
import qa.qcri.mm.drone.api.service.UserTokenService;
import qa.qcri.mm.drone.api.store.LookUp;
import qa.qcri.mm.drone.api.util.GISUtil;

import java.text.DateFormat;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 6/23/14
 * Time: 5:09 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("droneTrackerService")
@Transactional(readOnly = true)
public class DroneTrackerServiceImpl implements DroneTrackerService {
    @Autowired
    DroneTrackerDao droneTrackerDao;

    @Autowired
    DroneReportDao droneReportDao;

    @Autowired
    UserTokenService userTokenService;

    @Override
    public JSONArray getAllApprovedDroneGeoData() {
        List<DroneTracker> drones =  droneTrackerDao.getallApprovedData();
        return reformatDroneJson(drones);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public JSONArray getAllApprovedDroneGeoDataAfterID(Long id) {
        //System.out.println("id : " + id);
        List<DroneTracker> drones =  droneTrackerDao.getallApprovedDataAfterID(id);
        //System.out.println("drones : " + drones.size());
        return reformatDroneJson(drones);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<DroneTracker> getAllPendingDroneGeoData(String token) {

        return droneTrackerDao.getallPendingData();  //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    @Transactional(readOnly = false)
    public void saveUserMappingRequest(String geoJson) {
        JSONObject jObj = getJsonObject(geoJson);
        if(jObj != null){
            GISUtil gisUtil = new GISUtil();
            JSONObject info = (JSONObject)jObj.get("info");
            String url = (String) info.get("url");
            String key = getReserverLookupKey((JSONObject) jObj.get("features"));
            String displayName = gisUtil.getDisplayNameWithReverseLookUp(key) ;
            DroneTracker droneTracker =    new DroneTracker(geoJson,url, displayName );
            droneTrackerDao.save(droneTracker);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public int updateUserMappingRequest(String geoJson) {
        JSONObject jObj = getJsonObject(geoJson);
        int returnValue = LookUp.REQUEST_INPUT_DATA_INVALID;
        System.out.println("updateUserMappingRequest : " + geoJson );

        if(jObj != null){
            GISUtil gisUtil = new GISUtil();
            JSONObject info = (JSONObject)jObj.get("info");
            String requestEmail = (String) info.get("email");

            Long id = (Long)jObj.get("id");


            System.out.println("info : " + info.toJSONString() );

            List<DroneTracker> drones = droneTrackerDao.findDroneTrackerByID(id);
            System.out.println("id : " + id );
            System.out.println("size : " + drones.size() );
            if(drones.size() > 0){
                DroneTracker oldDrone = drones.get(0);
                String oldDroneInfo = oldDrone.getGeojson();
                JSONObject oldJobj = getJsonObject(oldDroneInfo);
                JSONObject oldInfo = (JSONObject)oldJobj.get("info");
                String oldEmail =  (String) oldInfo.get("email");
                if(oldEmail.equalsIgnoreCase(requestEmail)){
                    // delete & save
                    droneTrackerDao.deleteDroneTracker(oldDrone.getId());
                    saveUserMappingRequest(geoJson);
                    returnValue = LookUp.REQUEST_SUCCESS;
                }
                else{
                    returnValue = LookUp.REQUEST_INPUT_DATA_MISMATCH;
                }
            }
            else{
                returnValue = LookUp.REQUEST_INPUT_DATA_NOT_FOUND;
            }
        }

        return returnValue;
    }

    @Override
    @Transactional(readOnly = false)
    public int deleteUserMappingRequest(String email, Long id) {
        int returnValue = LookUp.REQUEST_INPUT_DATA_INVALID;
        if(!email.isEmpty() && email != null && id != null){
            List<DroneTracker> drones = droneTrackerDao.findDroneTrackerByID(id);
            if(drones.size() > 0){
                DroneTracker drone = drones.get(0);
                String geoJson = drone.getGeojson();
                JSONObject jObj = getJsonObject(geoJson);
                JSONObject info = (JSONObject)jObj.get("info");
                String oldEmail =  (String) info.get("email");
                if(oldEmail.equalsIgnoreCase(email)){
                    droneTrackerDao.deleteDroneTracker(drone.getId());
                    returnValue = LookUp.REQUEST_SUCCESS;
                }
                else{
                    returnValue = LookUp.REQUEST_INPUT_DATA_MISMATCH;
                }

            }
            else{
                returnValue = LookUp.REQUEST_INPUT_DATA_NOT_FOUND;
            }
        }
        else{
            returnValue = LookUp.REQUEST_INPUT_DATA_INVALID;
        }
        return returnValue;
    }

    private String getReserverLookupKey(JSONObject features) {
        //lat=16.63897422279177&lon=122.0280850999999
        JSONObject geometry = (JSONObject)features.get("geometry");
        JSONArray coordinates = (JSONArray)geometry.get("coordinates");
        String key = "lat="+coordinates.get(1)+"&lon=" + coordinates.get(0);
        return key;
    }

    private String getLatLng(JSONObject features){
        JSONObject geometry = (JSONObject)features.get("geometry");
        JSONArray coordinates = (JSONArray)geometry.get("coordinates");
       // String lng = (String)coordinates.get(0);
       // String lat = (String)coordinates.get(1);
        String key = coordinates.get(1)+"," + coordinates.get(0);
        return key;
    }

    private JSONObject getJsonObject(String geoJson){
        JSONObject obj = null;
        try{
            JSONParser parser = new JSONParser();
            obj = (JSONObject) parser.parse(geoJson);
        }
        catch(Exception e){
            System.out.println("getJsonObject :" + e.getMessage());
        }

        return obj;

    }


    private JSONArray reformatDroneJson(List<DroneTracker> drones ){
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = new JSONArray();
        for(DroneTracker d : drones){
            try{

                JSONObject obj = (JSONObject) parser.parse(d.getGeojson());
                JSONObject temp = new JSONObject();
                temp.put("features", obj.get("features"));
                temp.put("info", getMapinfoWindowJson((JSONObject)obj.get("info"), d));
                temp.put("vote", droneReportDao.getReportCount(d.getId()));
                jsonArray.add(temp) ;

            }
            catch(Exception e){
                System.out.println("reformatDroneJson :" + e.getMessage());
            }
        }

        return jsonArray;
    }


    private JSONObject getMapinfoWindowJson(JSONObject jsonObject, DroneTracker d){
        JSONObject obj = new JSONObject();

        obj.put("url", jsonObject.get("url"));
        obj.put("created",DateFormat.getDateTimeInstance(
                DateFormat.MEDIUM, DateFormat.SHORT).format(d.getCreated()) );
        obj.put("id", d.getId());
        obj.put("displayName", d.getDisplayName()) ;
       // obj.put("title", jsonObject.get("title"));

        return obj;

    }
}
