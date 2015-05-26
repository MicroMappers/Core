package qa.qcri.mm.api.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 6/28/14
 * Time: 2:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class GISUtil {
    String urlBase = "http://nominatim.openstreetmap.org/search/";
    //String urlBase = "http://scd1.qcri.org/nominatim/search/";
    String urlTail = "?format=json&addressdetails=1";

    // //http://scd1.qcri.org/nominatim/reverse?format=json&lat=16.63897422279177&lon=122.02808509999991&addressdetails=1
    String reverseURLBase = "http://nominatim.openstreetmap.org/nominatim/reverse?format=json&";
    String reverseURLTail = "&addressdetails=1&accept-language=en";
    JSONParser parser;
    Communicator communicator;

    public GISUtil(){
       this.parser = new JSONParser();
       this.communicator = new Communicator();
    }

    public String getDisplayName(String lat, String lon) throws Exception {
        String disName= null;
        if(!lat.isEmpty() && !lon.isEmpty()){
            String key = lat+"," + lon;
            String returnJson = communicator.sendGet(urlBase + key + urlTail);
            if(returnJson.trim().length() > 10){
                JSONArray jsonArray = (JSONArray) parser.parse(returnJson);

                Iterator itr= jsonArray.iterator();

                while(itr.hasNext()){

                    JSONObject featureJsonObj = (JSONObject)itr.next();
                    JSONObject info = (JSONObject)featureJsonObj.get("address");

                    String state = (String)info.get("state");
                    String city = (String)info.get("city");
                    String county = (String)info.get("county");
                    disName = (String)featureJsonObj.get("display_name");

                }

            }
        }
        return  disName;
    }

    public String getDisplayNameWithReverseLookUp(String key)  {
        String info= null;
        //lat=16.63897422279177&lon=122.0280850999999
        System.out.println("key :" + key);
        if(!key.isEmpty() ){
            try{
                String returnJson = communicator.sendGet(this.reverseURLBase + key + this.reverseURLTail);
                System.out.println("returnJson :" + returnJson);
                if(returnJson.trim().length() > 10){
                    JSONObject featureJsonObj = (JSONObject) parser.parse(returnJson);
                    //info =   (String)featureJsonObj.get("display_name");
                    System.out.println("featureJsonObj : " + featureJsonObj.toJSONString());
                    JSONObject address =  (JSONObject) featureJsonObj.get("address");
                    info = (String) address.get("country");

                    if(address.get("state") != null){
                        info =  (String) address.get("state") + ", " +  info ;
                    }

                    System.out.println("info : " + info);
                }
            }
            catch(Exception e){
                System.out.println("getDisplayNameWithReverseLookUp : " + e.getMessage());
            }

        }
        return  info;
    }

    public Double getTile2long(int x, int z){
        return (x/Math.pow(2,z)*360-180);
    }

    public Double getTile2lat(int y, int z) {
        double n=Math.PI-2*Math.PI*y/Math.pow(2,z);
        return (180/Math.PI*Math.atan(0.5*(Math.exp(n)-Math.exp(-n))));

    }

    public double[] getLatLng(String url){
        //http://a.tiles.mapbox.com/v4/nate.li8c5dff/{z}/{x}/{y}.png
        double[] aCoordinate = new double[2];
        String temp = url.replace("http://","");
        temp = temp.replace(".png","");

        String[] s = temp.split("/");
        String y = s[s.length - 1];
        String x = s[s.length - 2];
        String z = s[s.length - 3];

        int iY = Integer.valueOf(y) ;
        int iX =  Integer.valueOf(x) ;
        int iZ =  Integer.valueOf(z) ;

        double lat = this.getTile2lat(iY, iZ);
        double lng = this.getTile2long(iX, iZ);
        aCoordinate[0] = lat;
        aCoordinate[1] = lng;

        return   aCoordinate;
    }


}
