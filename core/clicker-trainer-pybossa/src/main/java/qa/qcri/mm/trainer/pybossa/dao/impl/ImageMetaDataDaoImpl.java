package qa.qcri.mm.trainer.pybossa.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import qa.qcri.mm.trainer.pybossa.dao.ImageMetaDataDao;
import qa.qcri.mm.trainer.pybossa.entity.ClientAppSource;
import qa.qcri.mm.trainer.pybossa.entity.ImageMetaData;
import qa.qcri.mm.trainer.pybossa.store.StatusCodeType;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 3/28/15
 * Time: 12:29 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class ImageMetaDataDaoImpl extends AbstractDaoImpl<ImageMetaData, String> implements ImageMetaDataDao {

    protected ImageMetaDataDaoImpl(){
        super(ImageMetaData.class);
    }


    @Override
    public List<ImageMetaData> getMapBoxDataTile(int status, int maxLimit) {
        return findByCriteria(Restrictions.eq("status", status), maxLimit);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateExportedData(ImageMetaData imageMetaData) {
        ImageMetaData iUpdatedSourced = findByCriterionID(Restrictions.eq("id",imageMetaData.getId()));
        iUpdatedSourced.setStatus(StatusCodeType.MAPBOX_TILE_EXPORTED);

        saveOrUpdate(iUpdatedSourced);
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ImageMetaData> findImageMetaDataByImageURL(String fileName) {
        return findByCriteria(Restrictions.eq("fileName", fileName));
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
