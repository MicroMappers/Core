package qa.qcri.mm.trainer.pybossa.format.impl;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.*;
import java.util.*;
/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 10/18/13
 * Time: 2:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class CVSRemoteFileFormatter {

    public List<MicromapperInput> getInputDataForReportTemplate(String url) throws Exception{
        //tweetID,tweet,author,lat,lon,created,answerCode

        String[] row = null;
        List<MicromapperInput> sourceSet = new ArrayList<MicromapperInput>();

        URL stockURL = new URL(url);
        BufferedReader in = new BufferedReader(new InputStreamReader(stockURL.openStream()));

        CSVReader csvReader = new CSVReader(in);
        List content = csvReader.readAll();

        for (Object object : content) {
            row = (String[]) object;
            if(row.length >= 7){
                //  tweetID,tweet,author,lat,lng,url,created
                MicromapperInput source = new MicromapperInput(row[0], row[1], row[2], row[3], row[4], null,row[5], row[6]);
                sourceSet.add(source);
            }
        }

        csvReader.close();

        if(sourceSet.size() > 1){
            sourceSet.remove(0);   // header
        }

        return sourceSet;
    }

    public List<MicromapperInput> getInputData(String url) throws Exception{
        //[Twitter username] // [Tweet message] // [optional: time-stamp] // [optional: location] // [optional: latitude] // [optional: longitude] // [image link]

        String[] row = null;
        List<MicromapperInput> sourceSet = new ArrayList<MicromapperInput>();

        URL stockURL = new URL(url);
        BufferedReader in = new BufferedReader(new InputStreamReader(stockURL.openStream()));

        CSVReader csvReader = new CSVReader(in);
        List content = csvReader.readAll();

        for (Object object : content) {
            row = (String[]) object;
            if(row.length >= 7){
                //  tweetID,tweet,author,lat,lng,url,created
                MicromapperInput source = new MicromapperInput(row[0], row[1], row[2], row[3], row[4], row[5], row[6]);
                sourceSet.add(source);
            }
        }

        csvReader.close();

        if(sourceSet.size() > 1){
            sourceSet.remove(0);   // header
        }

        return sourceSet;
    }

    private CSVReader getCVSContentReader(String source) throws Exception{
        CSVReader csvReader = null;
        if(source.toLowerCase().startsWith("http")){
            URL stockURL = new URL(source);
            BufferedReader in = new BufferedReader(new InputStreamReader(stockURL.openStream()));

            csvReader = new CSVReader(in);

        }
        else{
            csvReader = new CSVReader(new FileReader(source));
        }

        return csvReader;
    }

    private String getAerialClickerJsonData(String source) throws Exception{
        StringBuffer responseOutput = new StringBuffer();
        if(source.toLowerCase().startsWith("http")){
            URL stockURL = new URL(source);
            BufferedReader in = new BufferedReader(new InputStreamReader(stockURL.openStream()));

            String output;

            // System.out.println("Output from Server ...." + response.getStatusLine().getStatusCode() + "\n");
            while ((output = in.readLine()) != null) {
                responseOutput.append(output);
            };

        }
        else{
            BufferedReader br = new BufferedReader(new FileReader(source));
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                responseOutput.append(sCurrentLine);
            }
        }
        return responseOutput.toString();
    }

    public List<MicromapperInput> getClickerInputData(String url) throws Exception{
         //[Twitter username] // [Tweet message] // [optional: time-stamp] // [optional: location] // [optional: latitude] // [optional: longitude] // [image link]
        String[] row = null;
        List<MicromapperInput> sourceSet = new ArrayList<MicromapperInput>();

        CSVReader csvReader = getCVSContentReader(url) ;
        List content = csvReader.readAll();

        for (Object object : content) {
            //User-Name(0)	Tweet(1)	Time-stamp(2)	Location(3)	Latitude(4)	Longitude(5)	Image-link(6)	TweetID(7)
           // public MicromapperInput(String tweetID, String tweet, String author, String lat, String lng , String url, String created){
            row = (String[]) object;
            if(row!=null){
                if(row.length > 7){

                    String tweetID = row[7];
                    String tweet=row[1];
                    String author=row[0];
                    String lat=row[4];
                    String lng=row[5];
                    String imgURL = row[6];
                    String created = row[2];
                    String dataSourceLocation;

                    MicromapperInput source = new MicromapperInput(tweetID, tweet, author, lat, lng, imgURL, created);
                    sourceSet.add(source);
                }
            }
            //  tweetID,tweet,author,lat,lng,url,created



        }
        csvReader.close();
        // REMOVEW HEADER
        if(sourceSet.size() > 1){
            sourceSet.remove(0);
        }

        return sourceSet;
    }

    public List<MicromapperInput> getGeoClickerInputData(String url) throws Exception{
        //"tweetID","tweet","author","lat","lng","url","created","answer"
        String[] row = null;
        List<MicromapperInput> sourceSet = new ArrayList<MicromapperInput>();

        CSVReader csvReader = getCVSContentReader(url) ;
        List content = csvReader.readAll();

        for (Object object : content) {
            row = (String[]) object;
            if(row!=null){
                if(row.length > 7){
                    String tweetID = row[0];
                    String tweet=row[1];
                    String author=row[2];
                    String lat=row[3];
                    String lng=row[4];
                    String imgURL = row[5];
                    String created = row[6];
                    String answer = row[7];

                    MicromapperInput source = new MicromapperInput(tweetID, tweet, author, lat, lng, imgURL, created, answer);
                    sourceSet.add(source);
                }
            }
        }
        csvReader.close();

        if(sourceSet.size() > 1){
            sourceSet.remove(0);
        }

        return sourceSet;
    }

    public List<MicromapperInput> getAerialClickerInputData(String url) throws Exception{
        String[] row = null;
        List<MicromapperInput> sourceSet = new ArrayList<MicromapperInput>();

        String geoJsonString = getAerialClickerJsonData(url);
        JSONParser parser = new JSONParser();

        JSONArray tileJson = (JSONArray)parser.parse(geoJsonString);

        for (Object object : tileJson) {
            JSONObject aJson = (JSONObject) object;
            String jsonBound = null;
            if(aJson.get("bounds").equals("")){
                jsonBound = (String)aJson.get("bounds");
            }
            else{
                JSONArray bounds = (JSONArray) aJson.get("bounds");
                jsonBound = bounds.toJSONString();
            }
            String imgURL = (String) aJson.get("url");
            JSONArray imgSize = (JSONArray) aJson.get("size");

            String mediaSource = (String) aJson.get("source");

            MicromapperInput source = new MicromapperInput( imgURL, jsonBound, imgSize.toJSONString(), mediaSource);
            sourceSet.add(source);

        }

        return sourceSet;
    }

    public List<MicromapperInput> getClickerLocalFileInputData(String csvFilename) throws Exception{
        String[] row = null;
        List<MicromapperInput> sourceSet = new ArrayList<MicromapperInput>();

        CSVReader csvReader = getCVSContentReader(csvFilename) ;
        List content = csvReader.readAll();

        for (Object object : content) {
            //User-Name(0)	Tweet(1)	Time-stamp(2)	Location(3)	Latitude(4)	Longitude(5)	Image-link(6)	TweetID(7)
            // public MicromapperInput(String tweetID, String tweet, String author, String lat, String lng , String url, String created){
            row = (String[]) object;
            if(row!=null){
                if(row.length > 7){

                    String tweetID = row[7];
                    String tweet=row[1];
                    String author=row[0];
                    String lat=row[4];
                    String lng=row[5];
                    String imgURL = row[6];
                    String created = row[2];
                    String dataSourceLocation;

                    MicromapperInput source = new MicromapperInput(tweetID, tweet, author, lat, lng, imgURL, created);
                    sourceSet.add(source);
                }
            }
        }
        csvReader.close();
        // REMOVEW HEADER
        if(sourceSet.size() > 1){
            sourceSet.remove(0);
        }

        return sourceSet;
    }

    public List<MicromapperInput> getFileBaseImageClickerInputData(String csvFilename) throws Exception{
        //[Twitter username] // [Tweet message] // [optional: time-stamp] // [optional: location] // [optional: latitude] // [optional: longitude] // [image link]

        List<MicromapperInput> sourceSet = new ArrayList<MicromapperInput>();

        CSVReader csvReader = new CSVReader(new FileReader(csvFilename));
        String[] row = null;
        while ((row = csvReader.readNext()) != null) {
            if(row!=null){
                if(row.length > 8){
                    MicromapperInput source = new MicromapperInput(row[8], row[1], row[0], row[5], row[6], row[7], row[2]);
                    sourceSet.add(source);
                }
            }
        }

        csvReader.close();

        // REMOVEW HEADER
        if(sourceSet.size() > 1){
            sourceSet.remove(0);
        }

        return sourceSet;
    }

    public CSVWriter instanceToOutput(String fileName) throws Exception{
        File file = new File(fileName);
        //file.getAbsolutePath();
        CSVWriter writer = new CSVWriter(new FileWriter(fileName, true));
        //  public MicromapperOuput(String tweetID, String tweet, String author, String lat, String lng, String url, String created, String answer){

        String[] header = {"tweetID", "tweet","author", "lat", "lng", "url", "created", "answer"};
        writer.writeNext(header);

        return writer;

    }
    public void addToCVSOuputFile(String[] data, CSVWriter writer) throws Exception{

        writer.writeNext(data);

    }

    public void finalizeCVSOutputFile(CSVWriter writer) throws Exception{
        writer.close();

    }

    public boolean doesSourcerExist(String fileLocation){

        boolean found = false;

        try{

            if(fileLocation.toLowerCase().startsWith("http")){
                URL stockURL = new URL(fileLocation);
                BufferedReader in = new BufferedReader(new InputStreamReader(stockURL.openStream()));


            }
            else{
                CSVReader csvReader = new CSVReader(new FileReader(fileLocation));
            }

            found = true;
        }
        catch(Exception e){
            found = false ;
        }

        return found;

    }


}
