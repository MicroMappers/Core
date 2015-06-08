package qa.qcri.mm.api.template;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import qa.qcri.mm.api.entity.TaskQueueResponse;

import java.util.List;

/**
 * Created by jlucas on 6/4/15.
 */
public class AerialClickerKMLModel {
    protected static Logger logger = Logger.getLogger("AerialClickerKMLModel");
    private JSONParser parser;

    private StringBuffer kmlText;

    public AerialClickerKMLModel() {
    }

    public StringBuffer getKmlText() {
        return kmlText;
    }

    public void setKmlText(StringBuffer kmlText) {
        this.kmlText = kmlText;
    }

    public void setParser(JSONParser parser) {
        this.parser = parser;
    }

    public void buildHeader(){

        kmlText.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        kmlText.append("<kml xmlns=\"http://www.opengis.net/kml/2.2\">");
        kmlText.append("<Document>");
        kmlText.append("<Folder>");
        
    }

    public void buildFooter(){
        kmlText.append("</Folder>");
        kmlText.append("</Document>");
        kmlText.append("</kml>");
    }

    public void buildKMLBody(List<TaskQueueResponse> responses){
        try{

            if(responses.size() > 0 ){
                if(!responses.get(0).getResponse().equalsIgnoreCase("{}") && !responses.get(0).getResponse().equalsIgnoreCase("[]")){
                    JSONArray features = (JSONArray)parser.parse(responses.get(0).getResponse());

                    for(Object feature : features){
                        JSONObject obj = (JSONObject) feature;
                        JSONObject properties =   (JSONObject)obj.get("properties");
                        JSONObject geometry =   (JSONObject)obj.get("geometry");
                        JSONArray coordinates =   (JSONArray)geometry.get("coordinates");

                        kmlText.append("<Placemark>");
                            kmlText.append("<imageURL>");
                            kmlText.append(properties.get("imgURL"));
                            kmlText.append("</imageURL>");

                            kmlText.append("<Point>");
                            kmlText.append("<coordinates>");
                            kmlText.append(coordinates.get(0) + "," + coordinates.get(1));
                            kmlText.append("</coordinates>");
                            kmlText.append("</Point>");
                        kmlText.append("</Placemark>");
                    }

                }
            }
        }
        catch(Exception e){
            logger.error("getKMLSummeryDataSetForReport ERROR : " + e.toString());
        }

    }

}
