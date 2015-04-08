package gdelt;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 3/16/15
 * Time: 4:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class DataPull {

    public static void main(String[] args)  {
        String threadName = Thread.currentThread().getName();
        //logger.debug("   " + threadName + " has began working.(SyncWorker - run ClientApps)");
        while(true){
            System.out.println("Data Pull Scheduler is starting");
            try {
                String s =  sendGet("http://data.gdeltproject.org/micromappers/lastupdate.txt");
                System.out.println(s);
                System.out.println("starts at" + new Date());

                if(s.indexOf(".mmic.txt") > -1){
                    JSONArray jsonArray = new JSONArray();
                    JSONObject obj= new JSONObject();
                    obj.put("fileURL",s);
                    obj.put("appID",253);

                    jsonArray.add(obj);

                    String returnValue = sendPostGet(jsonArray.toJSONString(), "http://gis.micromappers.org/MMAPI/rest/source/save");



                }
                Thread.sleep(900000);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            System.out.println(" DataPull Scheduler is going sleep");
        }


    }

    public static String sendPostGet(String data, String url) {

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

            if (responseCode == 200 || responseCode == 204) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader((response.getEntity().getContent())));

                String output;
                // System.out.println("Output from Server ...." + response.getStatusLine().getStatusCode() + "\n");
                while ((output = br.readLine()) != null) {
                    responseOutput.append(output);
                }
            }
            else{

                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }


        }catch (Exception ex) {
            // System.out.println("Exception Code : " + ex);
            responseOutput.append("Exception Code : " + ex);

        } finally {
            httpClient.getConnectionManager().shutdown();
        }

        return responseOutput.toString();
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
                System.out.println(inputLine);
                if(inputLine.indexOf(".mmic.txt") > -1){
                    response.append(inputLine);
                }

            }
            in.close();

        }catch (Exception ex) {
            System.out.println("ex Code sendGet: " + ex + " : sendGet url = " + url);
            System.out.println("[errror on sendGet ]" + url);
        }

        return response.toString();
    }
}
