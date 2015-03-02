package qa.qcri.mm.media.dao.impl;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import qa.qcri.mm.media.dao.MediaSourceDao;
import qa.qcri.mm.media.entity.MediaPool;
import qa.qcri.mm.media.entity.MediaSource;
import qa.qcri.mm.media.store.LookUp;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 2/22/15
 * Time: 3:17 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class MediaSourceDaoImpl extends AbstractDaoImpl<MediaSource, String> implements MediaSourceDao {

    protected MediaSourceDaoImpl(){
        super(MediaSource.class);
    }


    @Override
    public List<MediaSource> getSourceByStatusAndPartner(Long partnerID, Integer status, Integer mediaType) {
        Criterion criterion = Restrictions.conjunction()
                .add(Restrictions.eq("status", LookUp.MEDIA_SOURCE_AVAILABLE))
                .add(Restrictions.eq("type", LookUp.MEDIA_TYPE_SKYBOX_SATELLITE_IMAGERY))
                .add(Restrictions.eq("partnerID",partnerID))
                ;

        return findByCriteria(criterion);
    }
}
