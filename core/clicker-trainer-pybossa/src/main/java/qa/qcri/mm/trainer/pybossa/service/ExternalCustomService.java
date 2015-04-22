package qa.qcri.mm.trainer.pybossa.service;

import org.json.simple.parser.JSONParser;
import qa.qcri.mm.trainer.pybossa.entity.ClientApp;
import qa.qcri.mm.trainer.pybossa.entity.TaskQueue;
import qa.qcri.mm.trainer.pybossa.entity.TaskQueueResponse;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 9/9/14
 * Time: 4:28 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ExternalCustomService {

    String NamibiaImage();
    TaskQueueResponse getAnswerResponseForAerial(String pybossaResult, JSONParser parser, Long taskQueueID, Long taskID) throws Exception;
    String NamibiaImageWithTag(int tagValue);
    TaskQueueResponse getAnswerResponseForSkyEyes( ClientApp clientApp, String datasource, TaskQueue taskQueue) throws Exception;
    TaskQueueResponse testAerialClick(String pybossaResult) throws Exception;
    TaskQueueResponse getAnswerResponse( ClientApp clientApp, String datasource, TaskQueue taskQueue) throws Exception;

}
