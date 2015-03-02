package qa.qcri.mm.media.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qa.qcri.mm.media.dao.MediaPoolDao;
import qa.qcri.mm.media.dao.MediaSourceDao;
import qa.qcri.mm.media.dao.PartnerDao;
import qa.qcri.mm.media.entity.MediaSource;
import qa.qcri.mm.media.entity.Partner;
import qa.qcri.mm.media.service.PoolService;
import qa.qcri.mm.media.store.LookUp;
import qa.qcri.mm.media.tool.GeoTifSlicer;

import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 2/22/15
 * Time: 1:53 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("poolService")
@Transactional(readOnly = false)
public class PoolServiceImpl implements PoolService{

    @Autowired
    MediaPoolDao mediaPoolDao;

    @Autowired
    MediaSourceDao mediaSourceDao;

    @Autowired
    PartnerDao partnerDao;

    GeoTifSlicer slicer = new GeoTifSlicer();

    @Override
    public void processSourceQueue() {
        List<Partner> partners = partnerDao.getAllActivePartner();

        if(partners.size() > 0)
        {
            for(Partner p : partners) {
                List<MediaSource> unprocessedMedia =  mediaSourceDao.getSourceByStatusAndPartner(p.getPartnerID(), LookUp.MEDIA_SOURCE_AVAILABLE, LookUp.MEDIA_TYPE_SKYBOX_SATELLITE_IMAGERY);
                for(MediaSource source : unprocessedMedia) {
                    slicer.addSlicerQueue(source.getSourceURL());
                }
            }
        }
    }

}
