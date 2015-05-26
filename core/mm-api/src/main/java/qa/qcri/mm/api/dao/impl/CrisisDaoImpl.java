package qa.qcri.mm.api.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import qa.qcri.mm.api.dao.CrisisDao;
import qa.qcri.mm.api.entity.Crisis;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 5/13/15
 * Time: 12:19 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class CrisisDaoImpl extends AbstractDaoImpl<Crisis, String> implements CrisisDao {

    protected CrisisDaoImpl(){
        super(Crisis.class);
    }

    @Override
    public List<Crisis> getAllCrisis() {

        return getAll();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Crisis> findCrisisByClientAppID(Long clientAppID) {
        return findByCriteria(Restrictions.eq("clientAppID", clientAppID));
    }
}
