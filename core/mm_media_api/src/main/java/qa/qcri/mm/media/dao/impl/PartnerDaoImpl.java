package qa.qcri.mm.media.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import qa.qcri.mm.media.dao.PartnerDao;
import qa.qcri.mm.media.entity.Partner;
import qa.qcri.mm.media.store.LookUp;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 2/22/15
 * Time: 4:37 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class PartnerDaoImpl extends AbstractDaoImpl<Partner, String> implements PartnerDao {

    protected PartnerDaoImpl(){
        super(Partner.class);
    }

    @Override
    public List<Partner> getAllActivePartner() {
        return findByCriteria(Restrictions.eq("status", LookUp.PARTNER_ACTIVE))  ;
    }
}
