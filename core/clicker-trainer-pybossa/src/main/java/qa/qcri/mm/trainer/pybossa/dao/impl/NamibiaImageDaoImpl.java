package qa.qcri.mm.trainer.pybossa.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import qa.qcri.mm.trainer.pybossa.dao.NamibiaImageDao;
import qa.qcri.mm.trainer.pybossa.entity.NamibiaImage;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 9/9/14
 * Time: 4:09 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class NamibiaImageDaoImpl extends AbstractDaoImpl<NamibiaImage, String> implements NamibiaImageDao {

    protected NamibiaImageDaoImpl(){
        super(NamibiaImage.class);
    }

    @Override
    public List<NamibiaImage> getClientAppSource() {
        return getAll();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<NamibiaImage> getClientAppSourceByFolderName(String folder) {
        ///Users/jlucas/Documents/aerialClicker/day2_rgb_transect_count_archive
        return findByCriteria(Restrictions.eq("path", folder));  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<NamibiaImage> getClientAppSourceByTag(Integer tag) {
        return findByCriteria(Restrictions.eq("animalFound", tag));
    }


}
