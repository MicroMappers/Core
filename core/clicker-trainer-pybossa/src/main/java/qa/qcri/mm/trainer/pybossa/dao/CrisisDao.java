package qa.qcri.mm.trainer.pybossa.dao;

import qa.qcri.mm.trainer.pybossa.entity.Crisis;

import java.util.List;

/**
 * Created by jlucas on 5/20/15.
 */
public interface CrisisDao extends AbstractDao<Crisis, String> {

    List<Crisis> getClientAppCrisisDetail(long clientAppID);
}
