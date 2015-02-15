package qa.qcri.mm.trainer.pybossa.dao;

import qa.qcri.mm.trainer.pybossa.entity.Lookup;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 1/7/15
 * Time: 3:42 PM
 * To change this template use File | Settings | File Templates.
 */
public interface LookupDao extends AbstractDao<Lookup, String>  {

    String getColumnValueByColumnName(String tblName, String colName);

}
