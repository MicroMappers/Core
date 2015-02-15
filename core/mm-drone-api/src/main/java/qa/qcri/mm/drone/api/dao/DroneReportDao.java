package qa.qcri.mm.drone.api.dao;

import qa.qcri.mm.drone.api.entity.DroneReport;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 7/22/14
 * Time: 5:28 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DroneReportDao extends AbstractDao<DroneReport, String>  {

    void saveDroneReport(DroneReport rpt);
    List<DroneReport> getReportByDroneID(Long droneTrackerID);
    int getReportCount(Long droneTrackerID);
}
