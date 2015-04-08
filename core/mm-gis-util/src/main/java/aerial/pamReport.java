package aerial;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 4/6/15
 * Time: 1:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class pamReport {

    public static void main(String[] args)  {
        int polycount =  getKMLSummeryDataSetByLayerType("MM_Aerial_PAM", "polygon");
        System.out.println("polycount : " + polycount);
        int polycount2 =  getKMLSummeryDataSetByLayerType("MM_Aerial_PAM", "polygon2");
        System.out.println("polycount2 : " + polycount2);
        int polycount3 = getKMLSummeryDataSetByLayerType("MM_Aerial_PAM", "polygon3");
        System.out.println("polycount3 : " + polycount3);
    }

    public static int getKMLSummeryDataSetByLayerType(String shortName, String layerType) {
        int sumOfLayerCount = 0;
        JSONParser cParser = new JSONParser();
        try{
            String arrayString = sendGet("http://gis.micromappers.org/MMAPI/rest/pam/reports/MM_Aerial_PAM");

            JSONArray reportList = (JSONArray)cParser.parse(arrayString)  ;



            for(int i= 0; i < reportList.size(); i++){
                JSONObject aReport = (JSONObject)reportList.get(i);
                String temp = (String)aReport.get("response");
                JSONObject obj = (JSONObject)cParser.parse(temp);
                JSONArray geo = (JSONArray)obj.get("geo");

                if(geo.size() >= 3){
                     int returnCount = processGeoByType(geo, layerType);
                     sumOfLayerCount = sumOfLayerCount +  returnCount;
                }
            }


        }
        catch(Exception e){
            System.out.println("GetKMLSummeryDataSetForReport ERROR : " + e.toString());
        }

        return sumOfLayerCount;
    }

    private static int processGeoByType(JSONArray geo, String requestedLayerType){
        int requestedLayerTypeCount = 0;

        for(int i=0; i < geo.size() ; i++){
            JSONObject obj = (JSONObject) geo.get(i);
            JSONArray geoInfo = (JSONArray)obj.get("geo");
            for(int j=0; j < geoInfo.size(); j++){
                JSONObject layerObj =(JSONObject) geoInfo.get(j);

                String layterType = (String)layerObj.get("layerType");
                layterType = layterType.trim();
                requestedLayerType = requestedLayerType.trim();

                if(layterType.equals(requestedLayerType)) {
                    requestedLayerTypeCount = requestedLayerTypeCount + 1;
                }

            }

        }
        return requestedLayerTypeCount;
    }

    public static String sendGet(String url) {
        HttpURLConnection con = null;
        StringBuffer response = new StringBuffer();
        // System.out.println("sendGet url : " + url);
        // logger.debug("[sendGet url  for debugger: ]" + url);

        try {
            URL connectionURL = new URL(url);
            con = (HttpURLConnection) connectionURL.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = con.getResponseCode();


            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(),"UTF-8"));
            String inputLine;
            // logger.debug("[response code ]" + responseCode);
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

        }catch (Exception ex) {
            System.out.println("ex Code sendGet: " + ex + " : sendGet url = " + url);
            System.out.println("[errror on sendGet ]" + url);
        }

        return response.toString();
    }
}
