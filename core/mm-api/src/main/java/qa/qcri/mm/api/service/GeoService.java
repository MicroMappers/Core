package qa.qcri.mm.api.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import qa.qcri.mm.api.entity.Crisis;
import qa.qcri.mm.api.template.CrisisGISModel;
import qa.qcri.mm.api.template.GeoJsonOutputModel;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 1/18/14
 * Time: 1:58 PM
 * To change this template use File | Settings | File Templates.
 */
public interface GeoService {
    List<GeoJsonOutputModel> getGeoJsonOutput() throws Exception;
    String getGeoJsonOuputJSONP(Date updated) throws Exception;
    String getGeoJsonOuputJSON(Date updated) throws Exception;

}
