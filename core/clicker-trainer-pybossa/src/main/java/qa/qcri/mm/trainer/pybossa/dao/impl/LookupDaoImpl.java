package qa.qcri.mm.trainer.pybossa.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import qa.qcri.mm.trainer.pybossa.dao.LookupDao;
import qa.qcri.mm.trainer.pybossa.entity.Lookup;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 1/7/15
 * Time: 3:45 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class LookupDaoImpl extends AbstractDaoImpl<Lookup, String> implements LookupDao {

    protected LookupDaoImpl(){
        super(Lookup.class);
    }

    @Override
    public String getColumnValueByColumnName(String tblName, String colName) {

        List<Lookup> rs =  findByCriteria(Restrictions.conjunction()
                .add(Restrictions.eq("columnName",colName))
                .add(Restrictions.eq("tableName", tblName)));

        if(rs.size() > 0) {
            Lookup obj = rs.get(0);
            return obj.getColumnValue();
        }

        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
