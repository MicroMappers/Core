package qa.qcri.mm.trainer.pybossa.format.impl;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import qa.qcri.mm.trainer.pybossa.entity.ClientApp;
import qa.qcri.mm.trainer.pybossa.entity.TaskQueue;
import qa.qcri.mm.trainer.pybossa.entity.TaskQueueResponse;
import qa.qcri.mm.trainer.pybossa.service.ClientAppResponseService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 11/10/14
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class DataProcessor extends Object {
    protected ClientApp clientApp;
    protected TaskQueue taskQueue;
    protected String datasource;
    protected JSONParser parser;

    public DataProcessor(){
        throw new AssertionError();
    }
    public DataProcessor(ClientApp clientApp){
        this.parser = new JSONParser();
        this.clientApp = clientApp;
    }

    public ClientApp getClientApp(){
        return this.clientApp;
    }
    private void toGoogleFusionTable(JSONObject jsonObject){
        StringBuffer kml = new StringBuffer();
        kml.append("<kml xmlns=\"http://www.opengis.net/kml/2.2\">");
        kml.append("<GroundOverlay>");
        kml.append("<name>San Francisco</name>") ;
        kml.append("<Icon>") ;
        kml.append(jsonObject.get("imgurl"));
        kml.append("</Icon>") ;
        kml.append("<LatLonBox>") ;
        kml.append("<north>") ;
        kml.append(jsonObject.get("north"));
        kml.append("</north>") ;
        kml.append("<south>") ;
        kml.append(jsonObject.get("south"));
        kml.append("</south>") ;
        kml.append("<east>") ;
        kml.append(jsonObject.get("east"));
        kml.append("</east>") ;
        kml.append("<west>") ;
        kml.append(jsonObject.get("west"));
        kml.append("</west>") ;
        kml.append("</LatLonBox>") ;

        kml.append("<Placemark>");
        kml.append("<<name>Naamloos Pad</name>>");
        kml.append("<<styleUrl>#msn_ylw-pushpin</styleUrl>>");
        kml.append("<LineString>");
        kml.append("<tessellate>1</tessellate>") ;
        kml.append("<coordinates>");
        kml.append(jsonObject.get("geo"));
        kml.append("</coordinates>");
        kml.append("<</LineString>>");
        kml.append("</Placemark>");


        kml.append("</GroundOverlay>");
        kml.append("</kml>");

    }

    public abstract TaskQueueResponse process(String datasource, TaskQueue taskQueue) throws Exception;

    public abstract List<TaskQueueResponse> generateMapOuput(List<TaskQueue> taskQueues, ClientAppResponseService clientAppResponseService) throws Exception;

}
