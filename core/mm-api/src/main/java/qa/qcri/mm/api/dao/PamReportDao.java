package qa.qcri.mm.api.dao;

import qa.qcri.mm.api.entity.PamReport;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 4/10/15
 * Time: 2:20 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PamReportDao extends AbstractDao<PamReport, String>  {

    List<PamReport> getAllProcessedPamReport();
    List<PamReport> getPamReportByID(Long id);
}
