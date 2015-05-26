package qa.qcri.mm.api.dao;

import qa.qcri.mm.api.entity.Crisis;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 5/13/15
 * Time: 12:10 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CrisisDao extends AbstractDao<Crisis, String>  {

    List<Crisis> getAllCrisis();
    List<Crisis> findCrisisByClientAppID(Long clientAppID);
}
