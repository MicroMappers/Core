package qa.qcri.mm.api.service;

import org.json.simple.JSONArray;
import qa.qcri.mm.api.entity.FilteredTaskRun;
import qa.qcri.mm.api.entity.NamibiaReport;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 9/29/14
 * Time: 9:59 AM
 * To change this template use File | Settings | File Templates.
 */
public interface NamibiaReportService {

    List<NamibiaReport> getAllProcessedDataSetForReport();
    List<NamibiaReport> getSummerydDataSetForReport();
    JSONArray getJSONSummerydDataSetForReport();
    JSONArray getJSONDataSetBySource(String imageSource);

    List<FilteredTaskRun> getFilteredTaskRunByTask(long taskID);

}
