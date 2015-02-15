package qa.qcri.mm.drone.api.dao;

import qa.qcri.mm.drone.api.entity.DroneTracker;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 6/23/14
 * Time: 4:41 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DroneTrackerDao extends AbstractDao<DroneTracker, String>  {
    List<DroneTracker> getallApprovedData();
    void saveRequest(DroneTracker droneTracker);
    List<DroneTracker> getallApprovedDataAfterID(Long id);
    List<DroneTracker> getallPendingData();
    void deleteDroneTracker(Long id);
    List<DroneTracker> findDroneTrackerByID(Long id);
}
