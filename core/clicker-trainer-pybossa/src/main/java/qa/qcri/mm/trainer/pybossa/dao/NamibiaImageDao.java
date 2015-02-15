package qa.qcri.mm.trainer.pybossa.dao;

import qa.qcri.mm.trainer.pybossa.entity.NamibiaImage;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 9/9/14
 * Time: 4:09 PM
 * To change this template use File | Settings | File Templates.
 */
public interface NamibiaImageDao extends AbstractDao<NamibiaImage, String>{

    List<NamibiaImage> getClientAppSource();
    List<NamibiaImage> getClientAppSourceByFolderName(String folder);
    List<NamibiaImage> getClientAppSourceByTag(Integer tag);
}
