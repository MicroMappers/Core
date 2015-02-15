package image;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 12/7/14
 * Time: 12:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class InstragramFileParser {

    public static void main(String[] args) {

        BufferedReader br = null;

        try {
            //User-Name,Tweet,Time-stamp,Location,Latitude,Longitude,Image-Link,TweetID
            String sCurrentLine;

            br = new BufferedReader(new FileReader("/Users/jlucas/Downloads/files/hashtag_WalayKlase.txt"));
            int index = -1;
            String url = "";
            String userName = "";
            String location = "";
            String created = "";
            String fileName ="/Users/jlucas/Downloads/files/export/hashtag_WalayKlase" + "export.csv";

            File file = new File(fileName);
            //file.getAbsolutePath();
            CSVWriter writer = new CSVWriter(new FileWriter(fileName, true));

            String[] header = {"User-Name", "Tweet","Time-stamp","Location", "Latitude", "Longitude", "Image-Link", "TweetID"};
            writer.writeNext(header);


            while ((sCurrentLine = br.readLine()) != null) {
                System.out.println(sCurrentLine);

                if(sCurrentLine.startsWith("http")){
                    url =  sCurrentLine.trim();
                    index = 0;
                }
                if(sCurrentLine.startsWith("User")){
                    userName = sCurrentLine.trim();
                    userName = userName.replace("User:","");
                    index = 1;
                }

                if(sCurrentLine.startsWith("Location:")){

                    //Location: 0 (Point: (13.734666667, 100.241666667))
                    location = sCurrentLine.trim();
                    location = location.replace("Location:","");
                    location = location.replace("Point:","");
                    location = location.replace("(","");
                    location = location.replace(")","");
                    index = 2;
                }

                if(sCurrentLine.startsWith("2014")){
                    created = sCurrentLine.trim();
                    index = 3;
                }

                if(index >= 3){
                    //User-Name,Tweet,Time-stamp,Location,Latitude,Longitude,Image-Link,TweetID


                    String[] data = new String[8];
                    data[0] =   userName;
                    data[1] =  "";
                    data[2] = created;
                    data[3] = url;
                    if(location.indexOf(",") > -1){
                        String loc[] = location.split(",") ;
                        data[4] = loc[1];
                        data[5] = loc[0];
                    }
                    else{
                        data[4] ="";
                        data[5] = "";
                    }

                    data[6] = url;
                    data[7] = "1";
                    System.out.println("data : " + data);
                    writer.writeNext(data);
                    index = -1;
                    userName = "";
                    created = "";
                    url = "";
                    location = ""  ;
                    String loc[] = null;
                }
            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }




}
