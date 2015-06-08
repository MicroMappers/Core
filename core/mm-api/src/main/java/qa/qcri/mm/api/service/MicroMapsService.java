package qa.qcri.mm.api.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import qa.qcri.mm.api.entity.ClientApp;
import qa.qcri.mm.api.template.MicroMapsCrisisModel;
import qa.qcri.mm.api.template.CrisisGISModel;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 4/22/15
 * Time: 11:29 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MicroMapsService {

    List<MicroMapsCrisisModel> getAllCries();
    List<CrisisGISModel> getAllCrisis() throws Exception;
    JSONArray getAllCrisisJSONP() throws Exception;
    JSONObject getGeoClickerByClientApp(Long clientAppID) throws Exception;
    String generateTextClickerKML(Long clientAppID) throws Exception;
    String generateImageClickerKML(Long clientAppID) throws Exception;
    String generateAericalClickerKML(Long clientAppID) throws Exception;
}
