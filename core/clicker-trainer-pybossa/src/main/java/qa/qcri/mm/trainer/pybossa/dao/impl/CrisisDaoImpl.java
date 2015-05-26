package qa.qcri.mm.trainer.pybossa.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import qa.qcri.mm.trainer.pybossa.dao.CrisisDao;
import qa.qcri.mm.trainer.pybossa.entity.Crisis;

import java.util.List;

/**
 * Created by jlucas on 5/20/15.
 */
@Repository
public class CrisisDaoImpl extends AbstractDaoImpl<Crisis, String> implements CrisisDao {

    protected CrisisDaoImpl(){
        super(Crisis.class);
    }


    @Override
    public List<Crisis> getClientAppCrisisDetail(long clientAppID) {
        return findByCriteria(Restrictions.eq("clientAppID", clientAppID));
    }
}
