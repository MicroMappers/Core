package qa.qcri.mm.api.dao.impl;

import org.springframework.stereotype.Repository;
import qa.qcri.mm.api.dao.ImageMetaDataDao;
import qa.qcri.mm.api.entity.ImageMetaData;

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
    public void saveMapBoxDataTile(ImageMetaData imageMetaData) {
        save(imageMetaData);
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
