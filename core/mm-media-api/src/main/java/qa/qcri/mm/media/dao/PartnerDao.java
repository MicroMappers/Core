package qa.qcri.mm.media.dao;

import qa.qcri.mm.media.entity.Partner;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 2/22/15
 * Time: 11:35 AM
 * To change this template use File | Settings | File Templates.
 */
public interface PartnerDao  extends AbstractDao<Partner, String>  {

    List<Partner> getAllActivePartner();

}
