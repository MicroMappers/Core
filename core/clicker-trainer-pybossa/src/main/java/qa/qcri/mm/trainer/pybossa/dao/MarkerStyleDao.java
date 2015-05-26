package qa.qcri.mm.trainer.pybossa.dao;

import qa.qcri.mm.trainer.pybossa.entity.MarkerStyle;

import java.util.List;

/**
 * Created by jlucas on 5/19/15.
 */
public interface MarkerStyleDao extends AbstractDao<MarkerStyle, String>  {

    List<MarkerStyle> findByAppType(String appType);
    List<MarkerStyle> findByClientAppID(long clientAppID);

}
