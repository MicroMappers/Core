package qa.qcri.mm.api.dao;

import qa.qcri.mm.api.entity.ReportTemplate;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 1/18/14
 * Time: 11:56 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ReportTemplateDao {
    List<ReportTemplate> getReportTemplateSearchBy(String field, String value);
}
