package qa.qcri.mm.api.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import qa.qcri.mm.api.dao.ReportTemplateDao;
import qa.qcri.mm.api.entity.ReportTemplate;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 1/18/14
 * Time: 12:00 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class ReportTemplateDaoImpl  extends AbstractDaoImpl<ReportTemplate,String> implements ReportTemplateDao {

    protected ReportTemplateDaoImpl(){
        super(ReportTemplate.class);
    }

    @Override
    public List<ReportTemplate> getReportTemplateSearchBy(String field, String value) {
        return findByCriteria(Restrictions.eq(field, value)) ;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
