package qa.qcri.mm.api.dao.impl;


import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import qa.qcri.mm.api.dao.PamReportDao;
import qa.qcri.mm.api.entity.PamReport;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 4/10/15
 * Time: 2:21 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class PamReportDaoImpl extends AbstractDaoImpl<PamReport,String> implements PamReportDao {

    protected PamReportDaoImpl(){
        super(PamReport.class);
    }

    @Override
    public List<PamReport> getAllProcessedPamReport() {
        return getAll();
    }

    @Override
    public List<PamReport> getPamReportByID(Long id) {
        return findByCriteria(Restrictions.eq("id", id));  //To change body of implemented methods use File | Settings | File Templates.
    }
}
