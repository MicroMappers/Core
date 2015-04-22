package qa.qcri.mm.trainer.pybossa.dao;


import qa.qcri.mm.trainer.pybossa.entity.ImageMetaData;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 3/28/15
 * Time: 12:23 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ImageMetaDataDao extends AbstractDao<ImageMetaData, String>  {

    List<ImageMetaData> getMapBoxDataTile(int status, int maxLimit);
    void updateExportedData(ImageMetaData imageMetaData);
    List<ImageMetaData> findImageMetaDataByImageURL(String fileName);
}
