package qa.qcri.mm.trainer.pybossa.dao;

import qa.qcri.mm.trainer.pybossa.entity.ReportTemplate;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 11/22/13
 * Time: 12:47 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ReportTemplateDao  extends AbstractDao<ReportTemplate, String>{
    void saveReportItem(ReportTemplate reportTemplate);
    void updateReportItem(ReportTemplate reportTemplate);
    List<ReportTemplate> getReportTemplateByClientApp(Long clientAppID, Integer status);
    List<ReportTemplate> getReportTemplateSearchBy(String field, String value);
}
