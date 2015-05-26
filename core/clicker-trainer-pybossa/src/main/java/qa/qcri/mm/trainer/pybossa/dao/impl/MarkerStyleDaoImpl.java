package qa.qcri.mm.trainer.pybossa.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import qa.qcri.mm.trainer.pybossa.dao.MarkerStyleDao;
import qa.qcri.mm.trainer.pybossa.entity.ImageMetaData;
import qa.qcri.mm.trainer.pybossa.entity.MarkerStyle;

import java.util.List;

/**
 * Created by jlucas on 5/19/15.
 */
@Repository
public class MarkerStyleDaoImpl extends AbstractDaoImpl<MarkerStyle, String> implements MarkerStyleDao {

    protected MarkerStyleDaoImpl(){
        super(MarkerStyle.class);
    }


    @Override
    public List<MarkerStyle> findByAppType(String appType) {
        return findByCriteria(Restrictions.eq("appType", appType));
    }

    @Override
    public List<MarkerStyle> findByClientAppID(long clientAppID) {
        return findByCriteria(Restrictions.eq("clientAppID", clientAppID));
    }
}
