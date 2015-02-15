package qa.qcri.mm.drone.api.service.impl;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qa.qcri.mm.drone.api.dao.DroneReportDao;
import qa.qcri.mm.drone.api.entity.DroneReport;
import qa.qcri.mm.drone.api.service.DroneReportService;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 7/22/14
 * Time: 6:12 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("droneReportService")
@Transactional(readOnly = true)
public class DroneReportServiceImpl implements DroneReportService{

    @Autowired
    DroneReportDao droneReportDao;

    @Override
    @Transactional(readOnly = false)
    public void saveUserVote(String geoJson) {
        JSONParser parser = new JSONParser();
        System.out.println("geoJson: " + geoJson);
        try {
                JSONObject obj = (JSONObject) parser.parse(geoJson);
                Long droneTrackerID = (Long)obj.get("id");
                //String strID = (String)obj.get("id");
                //Long droneTrackerID = Long.parseLong(strID);
                String comment = (String)obj.get("comment");
                DroneReport rt = new DroneReport(droneTrackerID,comment) ;
                droneReportDao.saveDroneReport(rt);

        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        finally{
            parser = null;
        }
    }
}
