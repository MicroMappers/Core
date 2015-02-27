package qa.qcri.mm.api.service;

import org.json.simple.JSONArray;
import qa.qcri.mm.api.entity.TaskQueueResponse;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 12/13/14
 * Time: 7:02 AM
 * To change this template use File | Settings | File Templates.
 */
public interface SkyeyeReportService {

    List<TaskQueueResponse> getSummerydDataSetForReport(String shortName);
    JSONArray getJSONSummerydDataSetForReport(String shortName);
    String getKMLSummeryDataSetForReport(String shortName);
    String getKMLSummeryDataSetByLayerType(String shortName, String layerType);
    String getKMLSummeryDataSetByResources(String shortName);

}
