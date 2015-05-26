package qa.qcri.mm.trainer.pybossa.util;

import au.com.bytecode.opencsv.CSVReader;
import qa.qcri.mm.trainer.pybossa.format.impl.MicromapperInput;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jlucas on 5/25/15.
 */
public class CVSFileDataSourceSearch {

    private static String theCurrentURL = null;
    private static CSVReader csvReader = null;
    private static List content = null;



    public static String[] search( String searchTweetID, String urlSource){
        //tweetID,tweet,author,lat,lng,url,created,answer
        try{
            String[] row = null;

            content = getCVSContent(urlSource);

            for (Object object : content) {
                row = (String[]) object;
                if(row.length == 8){

                    String tweetID = row[0];

                    System.out.println("tweetID : " + tweetID);
                    System.out.println("searchTweetID : " + searchTweetID);

                    if(tweetID.equalsIgnoreCase(searchTweetID)){
                        break;

                    }

                }
            }
            csvReader.close();
            return row;

        }
        catch(Exception e){
            System.out.println("exception : " + e);
        }

        return null;

    }


    private static List getCVSContent(String source) throws Exception{
        if(theCurrentURL!= null ){
            if(theCurrentURL.equalsIgnoreCase(source) && content != null){
                return  content;
            }

        }

        csvReader = null;
        content = null;
        theCurrentURL = source;
        if(source.toLowerCase().startsWith("http")){
            URL stockURL = new URL(theCurrentURL);
            BufferedReader in = new BufferedReader(new InputStreamReader(stockURL.openStream()));

            csvReader = new CSVReader(in);

        }
        else{
            csvReader = new CSVReader(new FileReader(theCurrentURL));
        }

        content = csvReader.readAll();

        return content;
    }


}
