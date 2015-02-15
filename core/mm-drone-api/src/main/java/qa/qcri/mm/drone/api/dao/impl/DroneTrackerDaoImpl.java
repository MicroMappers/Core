package qa.qcri.mm.drone.api.dao.impl;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import qa.qcri.mm.drone.api.dao.DroneTrackerDao;
import qa.qcri.mm.drone.api.entity.DroneTracker;
import qa.qcri.mm.drone.api.store.LookUp;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 6/23/14
 * Time: 4:47 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class DroneTrackerDaoImpl extends AbstractDaoImpl<DroneTracker, String> implements DroneTrackerDao {

    protected DroneTrackerDaoImpl(){
        super(DroneTracker.class);
    }


    @Override
    public List<DroneTracker> getallApprovedData() {
        //findByCriteriaByOrder
        String[] orderby = {};
        Criterion criterion = Restrictions.eq("status", LookUp.DRONE_VIDEO_APPROVED);
        return findByCriteriaByOrder(criterion, orderby, null) ;

    }

    @Override
    public List<DroneTracker> getallApprovedDataAfterID(Long id) {
        //findByCriteriaByOrder
        String[] orderby = {};

        Criterion criterion = Restrictions.conjunction()
                .add(Restrictions.eq("status", LookUp.DRONE_VIDEO_APPROVED))
                .add(Restrictions.gt("id",id))
                ;
        return findByCriteriaByOrder(criterion, orderby, null) ;

    }

    @Override
    public void saveRequest(DroneTracker droneTracker) {
        save(droneTracker);
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<DroneTracker> getallPendingData() {
        //findByCriteriaByOrder
        String[] orderby = {};
        Criterion criterion = Restrictions.eq("status", LookUp.DRONE_VIDEO_PENDING);
        return findByCriteriaByOrder(criterion, orderby, null) ;

    }

    @Override
    public void deleteDroneTracker(Long id) {
        List<DroneTracker> drones =  findDroneTrackerByID(id);
        if(drones.size() > 0){
            DroneTracker drone = drones.get(0);
            drone.setStatus(LookUp.DRONE_VIDEO_REMOVED);
            //delete(drone);
            saveOrUpdate(drone);
        }
    }

    @Override
    public List<DroneTracker> findDroneTrackerByID(Long id) {
        return findByCriteria(Restrictions.eq("id", id))  ;
    }


}
