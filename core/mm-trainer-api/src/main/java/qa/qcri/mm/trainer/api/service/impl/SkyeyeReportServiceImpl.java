package qa.qcri.mm.trainer.api.service.impl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qa.qcri.mm.trainer.api.entity.TaskQueueResponse;
import qa.qcri.mm.trainer.api.service.SkyeyeReportService;
import qa.qcri.mm.trainer.api.service.TaskQueueService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 12/13/14
 * Time: 7:04 AM
 * To change this template use File | Settings | File Templates.
 */
@Service("skyeyeReportService")
@Transactional(readOnly = true)
public class SkyeyeReportServiceImpl implements SkyeyeReportService {

    private JSONParser parser = new JSONParser();

    @Autowired
    TaskQueueService taskQueueService;


    @Override
    public List<TaskQueueResponse> getSummerydDataSetForReport(String shortName) {
        List<TaskQueueResponse> filteredReportList = new ArrayList<TaskQueueResponse>()   ;

        List<TaskQueueResponse> reportList =  taskQueueService.getTaskQueueResponseByClientApp(shortName);
        try{
            for(TaskQueueResponse t : reportList)  {
                JSONObject obj = (JSONObject)parser.parse(t.getResponse());
                JSONArray geo = (JSONArray)obj.get("geo");
                if(geo.size() >= 3){
                    filteredReportList.add(t);
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        System.out.print("original size : " + reportList.size() + "  ====  filtered : " + filteredReportList.size());

        return filteredReportList;
    }

    @Override
    public JSONArray getJSONSummerydDataSetForReport(String shortName) {
        List<TaskQueueResponse> reportList =  taskQueueService.getTaskQueueResponseByClientApp(shortName);
        JSONArray jsonArray = new JSONArray();
        try{
            for(int i= 0; i < reportList.size(); i++){
                String resp = reportList.get(i).getResponse();
                JSONObject obj = (JSONObject)parser.parse(resp);
                JSONArray geo = (JSONArray)obj.get("geo");

                System.out.print("taskid :" + obj.get("taskid") + "  = " + geo.size() );

                if(geo.size() >= 3 && !geo.isEmpty()){
                    jsonArray.add(reportList.get(i).getResponse());
                }

            }
        }
        catch(Exception e){
            System.out.println("GetJSONSummerydDataSetForReport ERROR : " + e.toString());
        }

        return jsonArray;
    }

    public String getKMLSummeryDataSetForReport(String shortName){
        List<TaskQueueResponse> reportList =  taskQueueService.getTaskQueueResponseByClientApp(shortName);

        StringBuffer sb = new StringBuffer();

        try{
            if(reportList.size() > 0){
                sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
                sb.append("<kml xmlns=\"http://www.opengis.net/kml/2.2\">");
                sb.append("<Document>");
            }

            for(int i= 0; i < reportList.size(); i++){
                String resp = reportList.get(i).getResponse();
                JSONObject obj = (JSONObject)parser.parse(resp);
                JSONArray geo = (JSONArray)obj.get("geo");

                if(geo.size() >= 3){
                    sb.append("<Folder>");
                        sb.append("<GroundOverlay>");
                            sb.append("<name>") ;
                                sb.append(obj.get("taskid"));
                            sb.append("</name>") ;
                            sb.append("<Icon>");
                                sb.append("<href>");
                                    sb.append(obj.get("imgurl"));
                                sb.append("</href>");
                            sb.append("</Icon>");
                            String bounds = (String)obj.get("bounds");

                            bounds = bounds.replace("[","");
                            bounds = bounds.replace("]","");
                            bounds = bounds.replace("\"","");
                            System.out.print("bounds : " +  bounds);
                            String[] parts = bounds.split(",");
                            sb.append("<LatLonBox>");
                                sb.append("<east>");
                                    sb.append(Double.parseDouble(parts[0]));
                                sb.append("</east>");
                                sb.append("<north>");
                                    sb.append(Double.parseDouble(parts[1]));
                                sb.append("</north>");
                                sb.append("<west>");
                                    sb.append(Double.parseDouble(parts[2]));
                                sb.append("</west>");
                                sb.append("<south>");
                                    sb.append(Double.parseDouble(parts[3]));
                                sb.append("</south>");
                            sb.append("</LatLonBox>");
                        sb.append("</GroundOverlay>");
                        sb.append(generatePlaceMarkKML(geo));
                    sb.append("</Folder>");
                }
            }

            if(reportList.size() > 0){
                sb.append("</Document>");
                sb.append("</kml>");
            }
        }
        catch(Exception e){
            System.out.println("GetKMLSummeryDataSetForReport ERROR : " + e.toString());
        }

        return sb.toString();
    }

    @Override
    public String getKMLSummeryDataSetByResources(String shortName) {
        List<TaskQueueResponse> reportList =  taskQueueService.getTaskQueueResponseByClientApp(shortName);

        StringBuffer sb = new StringBuffer();

        try{
            if(reportList.size() > 0){
                sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
                sb.append("<kml xmlns=\"http://www.opengis.net/kml/2.2\">");
                sb.append("<Document>");
            }

            for(int i= 0; i < reportList.size(); i++){
                String resp = reportList.get(i).getResponse();
                JSONObject obj = (JSONObject)parser.parse(resp);
                JSONArray geo = (JSONArray)obj.get("geo");

                if(geo.size() >= 3){
                    sb.append("<Folder>");
                    sb.append("<GroundOverlay>");
                    sb.append("<name>") ;
                    sb.append(obj.get("taskid"));
                    sb.append("</name>") ;
                    sb.append("<Icon>");
                    sb.append("<href>");
                    sb.append(obj.get("imgurl"));
                    sb.append("</href>");
                    sb.append("</Icon>");
                    String bounds = (String)obj.get("bounds");

                    bounds = bounds.replace("[","");
                    bounds = bounds.replace("]","");
                    bounds = bounds.replace("\"","");
                    System.out.print("bounds : " +  bounds);
                    String[] parts = bounds.split(",");
                    sb.append("<LatLonBox>");
                    sb.append("<east>");
                    sb.append(Double.parseDouble(parts[0]));
                    sb.append("</east>");
                    sb.append("<north>");
                    sb.append(Double.parseDouble(parts[1]));
                    sb.append("</north>");
                    sb.append("<west>");
                    sb.append(Double.parseDouble(parts[2]));
                    sb.append("</west>");
                    sb.append("<south>");
                    sb.append(Double.parseDouble(parts[3]));
                    sb.append("</south>");
                    sb.append("</LatLonBox>");
                    sb.append("</GroundOverlay>");
                    sb.append("</Folder>");
                }
            }

            if(reportList.size() > 0){
                sb.append("</Document>");
                sb.append("</kml>");
            }
        }
        catch(Exception e){
            System.out.println("GetKMLSummeryDataSetForReport ERROR : " + e.toString());
        }

        return sb.toString();
    }


    @Override
    public String getKMLSummeryDataSetByLayerType(String shortName, String layerType) {
        List<TaskQueueResponse> reportList =  taskQueueService.getTaskQueueResponseByClientApp(shortName);

        StringBuffer sb = new StringBuffer();

        try{
            if(reportList.size() > 0){
                sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
                sb.append("<kml xmlns=\"http://www.opengis.net/kml/2.2\">");
                sb.append("<Document>");
            }

            for(int i= 0; i < reportList.size(); i++){
                String resp = reportList.get(i).getResponse();
                JSONObject obj = (JSONObject)parser.parse(resp);
                JSONArray geo = (JSONArray)obj.get("geo");

                if(geo.size() >= 3){
                    sb.append("<Folder>");
                    sb.append("<GroundOverlay>");
                    sb.append("<name>") ;
                    sb.append(obj.get("taskid"));
                    sb.append("</name>") ;
                    sb.append("<Icon>");
                    sb.append("<href>");
                    sb.append(obj.get("imgurl"));
                    sb.append("</href>");
                    sb.append("</Icon>");
                    String bounds = (String)obj.get("bounds");

                    bounds = bounds.replace("[","");
                    bounds = bounds.replace("]","");
                    bounds = bounds.replace("\"","");
                    System.out.print("bounds : " +  bounds);
                    String[] parts = bounds.split(",");
                    sb.append("<LatLonBox>");
                    sb.append("<east>");
                    sb.append(Double.parseDouble(parts[0]));
                    sb.append("</east>");
                    sb.append("<north>");
                    sb.append(Double.parseDouble(parts[1]));
                    sb.append("</north>");
                    sb.append("<west>");
                    sb.append(Double.parseDouble(parts[2]));
                    sb.append("</west>");
                    sb.append("<south>");
                    sb.append(Double.parseDouble(parts[3]));
                    sb.append("</south>");
                    sb.append("</LatLonBox>");
                    sb.append("</GroundOverlay>");
                    sb.append(this.generatePlaceMarkKMLByLayerType(geo, layerType));
                    sb.append("</Folder>");
                }
            }

            if(reportList.size() > 0){
                sb.append("</Document>");
                sb.append("</kml>");
            }
        }
        catch(Exception e){
            System.out.println("GetKMLSummeryDataSetForReport ERROR : " + e.toString());
        }

        return sb.toString();
    }

    private String generatePlaceMarkKMLByLayerType(JSONArray geo, String requestedLayerType){
        StringBuffer sb = new StringBuffer();

        for(int i=0; i < geo.size() ; i++){
            JSONObject obj = (JSONObject) geo.get(i);
            JSONArray geoInfo = (JSONArray)obj.get("geo");
            for(int j=0; j < geoInfo.size(); j++){
                JSONObject layerObj =(JSONObject) geoInfo.get(j);

                String layterType = (String)layerObj.get("layerType");
                layterType = layterType.trim();
                requestedLayerType = requestedLayerType.trim();

                if(layterType.equals(requestedLayerType)) {
                    sb.append("<Placemark>");

                    if(requestedLayerType.equals("polyline")) {
                        sb.append("<Style><LineStyle><color>50780014</color><width>5</width></LineStyle></Style>") ;
                    }
                    if(requestedLayerType.equals("polyline2")){
                        sb.append("<Style><LineStyle><color>ff0000ff</color><width>5</width></LineStyle></Style>");
                    }

                    JSONObject layer = (JSONObject)layerObj.get("layer");
                    JSONObject geometry = (JSONObject)layer.get("geometry");
                    JSONArray coordinates = (JSONArray)geometry.get("coordinates");
                    JSONArray coordinateStart = (JSONArray)coordinates.get(0);
                    sb.append("<LineString>");
                        sb.append("<coordinates>");
                        sb.append(coordinateStart.get(0));
                        sb.append(",");
                        sb.append(coordinateStart.get(1));

                        sb.append(" ");

                        JSONArray coordinateEnd = (JSONArray)coordinates.get(1);
                        sb.append(coordinateEnd.get(0));
                        sb.append(",");
                        sb.append(coordinateEnd.get(1));

                        sb.append("</coordinates>");
                    sb.append("</LineString>");
                    sb.append("</Placemark>");
                }

            }

        }
        return sb.toString();
    }


    private String generatePlaceMarkKML(JSONArray geo){
        StringBuffer sb = new StringBuffer();

        for(int i=0; i < geo.size() ; i++){
            JSONObject obj = (JSONObject) geo.get(i);
            JSONArray geoInfo = (JSONArray)obj.get("geo");
            for(int j=0; j < geoInfo.size(); j++){
                JSONObject layerObj =(JSONObject) geoInfo.get(j);
                sb.append("<Placemark>");
                String layterType = (String)layerObj.get("layerType");

                if(layterType.equals("polyline")) {
                    sb.append("<Style><LineStyle><color>50780014</color><width>5</width></LineStyle></Style>") ;
                }
                else{
                    sb.append("<Style><LineStyle><color>ff0000ff</color><width>5</width></LineStyle></Style>");
                }

                JSONObject layer = (JSONObject)layerObj.get("layer");
                JSONObject geometry = (JSONObject)layer.get("geometry");
                JSONArray coordinates = (JSONArray)geometry.get("coordinates");
                JSONArray coordinateStart = (JSONArray)coordinates.get(0);
                sb.append("<LineString>");
                sb.append("<coordinates>");
                sb.append(coordinateStart.get(0));
                sb.append(",");
                sb.append(coordinateStart.get(1));

                sb.append(" ");

                JSONArray coordinateEnd = (JSONArray)coordinates.get(1);
                sb.append(coordinateEnd.get(0));
                sb.append(",");
                sb.append(coordinateEnd.get(1));

                sb.append("</coordinates>");
                sb.append("</LineString>");
                sb.append("</Placemark>");

            }

        }
        return sb.toString();
    }

}
