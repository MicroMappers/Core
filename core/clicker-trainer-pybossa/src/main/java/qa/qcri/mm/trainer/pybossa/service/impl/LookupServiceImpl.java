package qa.qcri.mm.trainer.pybossa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qa.qcri.mm.trainer.pybossa.dao.LookupDao;
import qa.qcri.mm.trainer.pybossa.service.LookupService;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 1/7/15
 * Time: 4:25 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("lookupService")
@Transactional(readOnly = true)
public class LookupServiceImpl implements LookupService {
    @Autowired
    private LookupDao lookupDao;

    @Override
    public String getColmnValue(String tblName, String colName) {
        return lookupDao.getColumnValueByColumnName(tblName, colName);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
