package qa.qcri.mm.drone.api.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import qa.qcri.mm.drone.api.dao.DroneReportDao;
import qa.qcri.mm.drone.api.entity.DroneReport;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 7/22/14
 * Time: 5:30 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class DroneReportDaoImpl extends AbstractDaoImpl<DroneReport, String> implements DroneReportDao {

    protected DroneReportDaoImpl(){
        super(DroneReport.class);
    }

    @Override
    public void saveDroneReport(DroneReport rpt) {
        save(rpt);
    }

    @Override
    public List<DroneReport> getReportByDroneID(Long droneTrackerID) {
        return findByCriteria(Restrictions.eq("droneTrackerID", droneTrackerID))  ;
    }

    @Override
    public int getReportCount(Long droneTrackerID) {
        return findByCriteria(Restrictions.eq("droneTrackerID", droneTrackerID)).size();  //To change body of implemented methods use File | Settings | File Templates.
    }

}
