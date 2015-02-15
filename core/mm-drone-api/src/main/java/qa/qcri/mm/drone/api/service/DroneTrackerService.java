package qa.qcri.mm.drone.api.service;

import org.json.simple.JSONArray;
import qa.qcri.mm.drone.api.entity.DroneTracker;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 6/23/14
 * Time: 5:04 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DroneTrackerService {
    JSONArray getAllApprovedDroneGeoData();
    void saveUserMappingRequest(String geoJson);
    JSONArray getAllApprovedDroneGeoDataAfterID(Long id);
    List<DroneTracker> getAllPendingDroneGeoData(String token);
    int updateUserMappingRequest(String geoJson);
    int deleteUserMappingRequest(String email, Long id);
}
