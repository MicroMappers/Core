package qa.qcri.mm.trainer.pybossa.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import qa.qcri.mm.trainer.pybossa.util.LatLngUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: jilucas
 * Date: 9/29/13
 * Time: 1:01 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml", "classpath:spring/hibernateContext.xml"})
public class ClientAppRunWorkerTest {

   // @Autowired
   // private ClientAppRunWorker clientAppRunWorker;

    @Test
    public void testProcessTaskPublish() throws Exception {
       // clientAppRunWorker.processTaskRunImport();
        //clientAppRunWorker.processTaskPublish();
        /**
        String url = "http://clickers.micromappers.org/api/task?app_id=44184&id=483900";
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
           // logger.debug("[errror on sendGet ]" + url);
        }
        System.out.println(response.toString());
        //return response.toString();
        **/
    }


      public void sendPostGet() {
          String url = "http://clickers.micromappers.org/api/task?api_key=539dc5a2-f335-4707-a880-30b27fa45878";
          String data="{\"priority_0\":0,\"quorum\":1,\"app_id\":44258,\"calibration\":0,\"n_answers\":1,\"info\":{\"documentID\":14319000,\"userID\":\"1922217578\",\"text\":\"boys with great jawlines are the reason i am alive tbh\",\"aidrID\":6,\"createdAt\":\"Fri Dec 05 05:13:51 +0000 2014\",\"userName\":\"Erin Loftus\",\"question\":\"please tag it.\",\"tweetid\":540735780616802304,\"crisisID\":248,\"n_answers\":1}}";

            HttpClient httpClient = new DefaultHttpClient();
            StringBuffer responseOutput = new StringBuffer();
            try {
                HttpPost request = new HttpPost(url);
                StringEntity params =new StringEntity(data, "UTF-8");
                params.setContentType("application/json");
                request.addHeader("content-type", "application/json");
                request.setEntity(params);
                HttpResponse response = httpClient.execute(request);

                int responseCode = response.getStatusLine().getStatusCode();
                System.out.println("responseCode : " + responseCode);
                //if (responseCode == 200 || responseCode == 204) {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader((response.getEntity().getContent())));

                    String output;
                    // System.out.println("Output from Server ...." + response.getStatusLine().getStatusCode() + "\n");
                    while ((output = br.readLine()) != null) {
                        responseOutput.append(output);
                    }
               // }
               // else{

                //    throw new RuntimeException("Failed : HTTP error code : "
                 //           + response.getStatusLine().getStatusCode());
                //}


            }catch (Exception ex) {
                // System.out.println("Exception Code : " + ex);
                responseOutput.append("Exception Code : " + ex);

            } finally {
                httpClient.getConnectionManager().shutdown();
            }

            //return responseOutput.toString();
    }

    public void testGeoCalculation() throws Exception {

        //121.07551516934, 14.298073550000003
        double lat1 = 14.298073550000003;
        double lon1 = 121.07551516934;
        //120.9370878, 14.327082399999979
        double lat2 = 14.327082399999979;
        double lon2 = 120.9370878;
        //121.07551516934, 14.298073550000003
        double lat3 = 14.298073550000003;
        double lon3 = 121.07551516934;


        // new york
        //double lat1 = 40.7143528;
        //double lon1 = -74.0059731;
        // chicago
        //double lat2 = 41.8781136;
        //double lon2 = -87.6297982;
        // Atlanta
        //double lat3 = 33.7489954;
        //double lon3 = -84.3879824;

        double results[] = new double[2];

        LatLngUtils.geoMidPointFor3Points(lat1, lon1,lat2,  lon2, lat3,  lon3, results);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("coordinates", Arrays.toString(results));
        jsonObject.put("type", "Point");

        JSONObject geoResponse = new JSONObject();
        geoResponse.put("geometry", jsonObject);


        System.out.println(geoResponse.toJSONString());

        double[] distance = new double[3];
        double[] distanceInMile= new double[1];

        double lon = results[0];
        double lat = results[1];


        LatLngUtils.computeDistanceInMile(lat, lon, lat1, lon1, distanceInMile);
        distance[0] = distanceInMile[0];

        LatLngUtils.computeDistanceInMile(lat, lon, lat2, lon2, distanceInMile);
        distance[1] = distanceInMile[0];

        LatLngUtils.computeDistanceInMile(lat, lon, lat3, lon3, distanceInMile);
        distance[2] = distanceInMile[0];

        Arrays.sort(distance) ;

        double maxDistance = distance[2];

        System.out.println("maxDistance : " + maxDistance);
    }
}
