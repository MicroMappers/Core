package qa.qcri.mm.api.service;

import org.json.simple.JSONArray;
import qa.qcri.mm.api.entity.FilteredTaskRun;
import qa.qcri.mm.api.entity.NamibiaReport;
import qa.qcri.mm.api.entity.PamReport;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 9/29/14
 * Time: 9:59 AM
 * To change this template use File | Settings | File Templates.
 */
public interface VanuatuReportService {

    List<PamReport> getAllProcessedPamReport();
    PamReport getPamRecordByID(long id);

}
