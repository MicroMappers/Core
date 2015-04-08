package image;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 3/16/15
 * Time: 11:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class geoJsonGenerator {
    /**
     {
     "features": {
     "type": "Feature",
     "geometry": {
     "distance": 1,
     "type": "Point",
     "coordinates": [
     122.27334594726564,
     12.587133279610914
     ]
     }
     },
     "info": {
     "author": "gmanews",
     "updated": "2014-12-07 23:57:39.0",
     "tweetID": "541006762527440896",
     "created": "2014-12-05 23:10:00",
     "answer": "mild ",
     "tweet": "Coast Guard sa Romblon, naghahanda na rin para sa inaasahang pagtama ng Bagyong #RubyPH. | via @akosiJaysent http://t.co/HLi9rU7Obz",
     "url": "https://pbs.twimg.com/media/B4IKR9uCIAA83_W.jpg"
     }
     }
    **/


    public static void main(String[] args) {

        try {
            JSONArray mmData = getDBData();
            //User-Name,Tweet,Time-stamp,Location,Latitude,Longitude,Image-Link,TweetID
            String sCurrentLine;
            //Image URL	Location	Lat (if available)	Long (if available)	Credit

            int index = -1;
            String url = "";
            String userName = "";
            String location = "";
            String created = "";
            String source = "/Users/jlucas/Downloads/esri/imageClickerJson/photo2.csv";

            CSVReader csvReader = new CSVReader(new FileReader(source));

            JSONArray imageGeoJsonArray = new JSONArray();

            List content = csvReader.readAll();
            String[] row = null;
            int indexI = 0;

            for (Object object : content) {
                //ImageURL	Location	Lat (if available)	Long (if available)	Credit
                //Ref Number/file name	Image URL	Location	Lat (if available)	Long (if available)	Credit	Located	Uploaded to map
                if(indexI > 0){

                    row = (String[]) object;

                    if(row!=null){
                        if(row.length > 4){
                            String lat = row[4];
                            String lng = row[5];
                            if(isValideCoordinate(lat, lng) && row[1].indexOf("https://www.facebook.com/") == -1){
                                String imageURL = row[1];
                                String locationName = row[3];
                                if(row[3].length() < 11){
                                    locationName =   "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + locationName;
                                }
                               // String locationName = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + row[3];

                                String text = row[6];

                                JSONArray cors = new JSONArray();


                                cors.add(Double.parseDouble(lng));
                                cors.add(Double.parseDouble(lat));



                                JSONArray geoEle = new JSONArray();

                                JSONObject ele = new JSONObject();
                                ele.put("distance", 1);
                                ele.put("type", "point");

                                ele.put("coordinates", cors);


                                JSONObject features = new JSONObject();
                                features.put("type", "Feature");
                                features.put("geometry", ele);

                                JSONObject info = new JSONObject();
                                info.put("author", text);
                                info.put("updated", locationName);
                                info.put("tweetID", "");
                                info.put("created", locationName);
                                info.put("answer", "mild");
                                info.put("tweet", text);
                                info.put("url",imageURL) ;

                                JSONObject aObject = new JSONObject();
                                aObject.put("features", features);
                                aObject.put("info",info);


                                imageGeoJsonArray.add(aObject);
                            }


                        }
                    }

                }

                indexI++;
            }
            csvReader.close();
            imageGeoJsonArray.addAll(mmData) ;
            System.out.println("jsonp(" + imageGeoJsonArray.toJSONString() + ");");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

    }

    private static JSONArray getDBData(){
        JSONArray imageGeoJsonArray = new JSONArray();

        try {
            JSONParser jsonParser = new JSONParser();
            String sCurrentLine;

            int index = -1;
            String url = "";
            String userName = "";
            String location = "";
            String created = "";
            String source = "/Users/jlucas/Downloads/esri/imageClickerJson/db1.csv";

            CSVReader csvReader = new CSVReader(new FileReader(source));


            List content = csvReader.readAll();
            String[] row = null;
            int indexI = 0;

            for (Object object : content) {
                //r1.taskQueueID, r2.taskID, r1.taskInfo, r1.response, t1.answer , t1.created, t1.updated
                if(indexI > 0){

                    row = (String[]) object;

                    if(row!=null){
                                String imageURL = row[2];
                                String locationName = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + row[2];


                                JSONObject info = new JSONObject();
                                info.put("author", "");
                                info.put("updated", row[5]);
                                info.put("tweetID", "");
                                info.put("created", row[6]);
                                info.put("answer", row[4]);
                                info.put("tweet", "");
                                info.put("url",imageURL) ;

                                JSONObject aObject = new JSONObject();
                                aObject.put("features", jsonParser.parse(row[3]));
                                aObject.put("info",info);

                                imageGeoJsonArray.add(aObject);

                    }

                }

                indexI++;
            }
            csvReader.close();
            System.out.println("jsonp(" + imageGeoJsonArray.toJSONString() + ");");

        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        return imageGeoJsonArray;

    }



    private static boolean isValideCoordinate(String lat, String lng){

        if(lat.isEmpty()){
            return false;
        }

        if(lng.isEmpty()){
            return false;
        }

        if(lat.length() < 2){
            return false;
        }

        if(lng.length() < 2){
            return false;
        }

        return true;
    }
}
