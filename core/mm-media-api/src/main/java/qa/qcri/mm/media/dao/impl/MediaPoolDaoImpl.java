package qa.qcri.mm.media.dao.impl;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import qa.qcri.mm.media.dao.MediaPoolDao;
import qa.qcri.mm.media.entity.MediaPool;
import qa.qcri.mm.media.store.LookUp;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 2/22/15
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class MediaPoolDaoImpl extends AbstractDaoImpl<MediaPool, String> implements MediaPoolDao{

    protected MediaPoolDaoImpl(){
        super(MediaPool.class);
    }

    @Override
    public void addToPool(MediaPool mediaPool) {
        save(mediaPool);
    }

    @Override
    public List<MediaPool> getMediaPoolBySourceAndStatus(Long sourceID, Integer status) {
        Criterion criterion = Restrictions.conjunction()
                .add(Restrictions.eq("status", LookUp.MEDIA_POOL_AVAILABLE))
                .add(Restrictions.eq("sourceID",sourceID))
                ;

        return findByCriteria(criterion);
    }
}
