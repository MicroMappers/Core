package qa.qcri.mm.api.util;

import qa.qcri.mm.api.store.URLReference;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jlucas on 6/13/15.
 */
public class DataFileUtil {


    public static boolean doesFileExist(String fileName){
        File f = new File(fileName);

        System.out.print("fileName : doesFileExist :" + fileName);

        if(f.exists() ) {
            System.out.print("fileName : doesFileExist : true" );
            return true;
        }
        System.out.print("fileName : doesFileExist : false" );
        return false;

    }

    public static boolean isUpdateRequired(String fileName, long refreshInMinute){
        Date currentDate = new Date();
        File file = new File(fileName);

        long diff = currentDate.getTime() - file.lastModified();

        long diffMinutes = diff / (60 * 1000) % 60;

        if(diffMinutes > refreshInMinute){
            return true;
        }

        return false;
    }

    public static boolean createAfile(String fileContent, String geoFileName){
        boolean isSucced = false;

        geoFileName = "a.json";

        try {
            File f = new File(geoFileName);
            PrintWriter writer = new PrintWriter(f, "UTF-8");
            writer.println(fileContent);
            writer.close();
            isSucced = true;
        }
        catch(Exception e){
            System.out.print("createAfile : Exception :" + e);
        }

        return isSucced;

    }

    public static String getDataFileContent(String fileName){
        String content = "";
        try{
            BufferedReader br = null;
            String sCurrentLine;

            br = new BufferedReader(new FileReader(fileName));

            while ((sCurrentLine = br.readLine()) != null) {
                content = content + sCurrentLine;
            }
        }
        catch(Exception e){
            System.out.print(e.getMessage());
        }

        return content;
    }
}
