package aerial;

import au.com.bytecode.opencsv.CSVWriter;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.drew.metadata.exif.GpsDirectory.TAG_LATITUDE;
import static com.drew.metadata.exif.GpsDirectory.TAG_LONGITUDE;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 3/25/15
 * Time: 11:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class FileGenerator {

    public static void main(String[] args)  {
        try {

           // test();

           // String directoryName = "/Users/jlucas/Documents/ConservationDrones/TEST/Calperum_photos" ;

            String directoryName = "/Users/jlucas/Documents/imagery/59KKA2265_4_oblique_02042015_1639" ;
            File directory = new File(directoryName);

            List<ImageMataData> imgList = new ArrayList<ImageMataData>();

            File[] fList = directory.listFiles();
            for (File file : fList){

                if(file.getName().indexOf(".JPG") > -1){
                    System.out.println("-------------------------------------");
                    System.out.println(file.getName());
                    Metadata metadata = ImageMetadataReader.readMetadata(file);

                    print(metadata, imgList, file.getName());

                    System.out.println("-------------------------------------");
                }


            }

            if(imgList.size() > 0){
                // generate file
                generateFile(imgList);
            }

        //} catch (ImageProcessingException e) {
            // handle exception
        } catch (Exception e) {
            // handle exception
        }

    }

    public static void test(){



        try {
            List<ImageMataData> imgList = new ArrayList<ImageMataData>();
            // String directoryName = "/Users/jlucas/Documents/ConservationDrones/TEST/Calperum_photos" ;
            String fileName = "/Users/jlucas/Documents/imagery/59KKA2265_4_oblique_02042015_1639/59KKA2265_4_oblique_02042015_1639 (1).JPG" ;
            //String fileName = "/Users/jlucas/Documents/ConservationDrones/TEST/Calperum_photos/IMG_5819.JPG";
            File file = new File(fileName);

            //List<ImageMataData> imgList = new ArrayList<ImageMataData>();

           // File[] fList = directory.listFiles();
           // for (File file : fList){

                // if(file.getName().indexOf(".JPG") > -1){
                System.out.println("-------------------------------------");
                System.out.println(file.getName());
                Metadata metadata = ImageMetadataReader.readMetadata(file);

                print(metadata, imgList, file.getName());

                System.out.println("-------------------------------------");
                // }


           // }

           // if(imgList.size() > 0){
                // generate file
            //    generateFile(imgList);
           // }

        } catch (ImageProcessingException e) {
            // handle exception
        } catch (IOException e) {
            // handle exception
        }
    }
    public void  processBytes() throws IOException
    {
        /**
         Metadata metadata = new Metadata();
         byte[] bytes = FileUtil.readBytes("/Users/jlucas/Documents/ConservationDrones/TEST/Calperum_photos/IMG_5819.JPG");
         new ExifReader().extract(new ByteArrayReader(bytes), metadata, ExifReader.JPEG_SEGMENT_PREAMBLE.length());
         //return metadata;
         System.out.print(metadata);
         **/

        try {

            String directoryName = "/Users/jlucas/Documents/ConservationDrones/TEST/Calperum_photos" ;
            File directory = new File(directoryName);

            // File file = new File("/Users/jlucas/Documents/ConservationDrones/TEST/Calperum_photos/IMG_5819.JPG");

            // There are multiple ways to get a Metadata object for a file
            List<ImageMataData> imgList = new ArrayList<ImageMataData>();

            File[] fList = directory.listFiles();
            for (File file : fList){

                if(file.getName().indexOf(".JPG") > -1){
                    System.out.println("-------------------------------------");
                    System.out.println(file.getName());
                    Metadata metadata = ImageMetadataReader.readMetadata(file);

                    print(metadata, imgList, file.getName());

                    System.out.println("-------------------------------------");
                }


            }

            if(imgList.size() > 0){
                // generate file
                generateFile(imgList);
            }

        } catch (ImageProcessingException e) {
            // handle exception
        } catch (IOException e) {
            // handle exception
        }



    }



    private static void print(Metadata metadata, List imgList, String fileName)
    {


        // Iterate over the data and print to System.out

        //
        // A Metadata object contains multiple Directory objects
        //
        ImageMataData imageMataData = new ImageMataData();

        imageMataData.setFileName(fileName);

        for (Directory directory : metadata.getDirectories()) {

            //
            // Each Directory stores values in Tag objects
            //
            for (Tag tag : directory.getTags()) {
                //System.out.println(tag);
                 System.out.println(tag);

                if(tag.getTagType() == TAG_LATITUDE || tag.getTagType() == TAG_LONGITUDE) {
                    System.out.println(tag);
                    if(tag.getTagName().equalsIgnoreCase("GPS Latitude")){
                        // System.out.println("lat : " +tag.getDescription());
                        imageMataData.setLat(tag.getDescription());

                    }

                    if(tag.getTagName().equalsIgnoreCase("GPS Longitude")){
                        //System.out.println("lng : " +tag.getDescription());
                        imageMataData.setLng(tag.getDescription());
                    }
                }


            }

            //
            // Each Directory may also contain error messages
            //
            if (directory.hasErrors()) {
                for (String error : directory.getErrors()) {
                    System.err.println("ERROR: " + error);
                }
            }
        }

        imgList.add(imageMataData);
    }


    public static void generateFile(List imgList) {

        /**
         *
         private String path = "";
         private String fileName;
         private String thumbnail = "";
         private String lat;
         private String lng;
         private String bounds = "";
         */
        try {

            String fileName ="/Users/jlucas/Documents/imagery/59KKA2265_4_oblique_02042015_1639/export.csv";
            String fileJsonName ="/Users/jlucas/Documents/imagery/59KKA2265_4_oblique_02042015_1639/export.json";


            JSONArray bounds = new JSONArray();
            bounds.add(125.36810376844326) ;
            bounds.add(12.264353201920015) ;
            bounds.add(125.36774531324834) ;
            bounds.add(12.264006821392263) ;

            JSONArray size = new JSONArray();

            size.add(1024) ;
            size.add(1024) ;


            File file = new File(fileName);
            File fileJson = new File(fileJsonName);
            //file.getAbsolutePath();
            CSVWriter writer = new CSVWriter(new FileWriter(fileName, true));

            JSONArray imageGeoJsonArray = new JSONArray();
            for(int i=0; i < imgList.size(); i++){
                ImageMataData m = (ImageMataData)imgList.get(i);
                String[] data = new String[6];
                data[0] =   m.getPath();
                data[1] =   m.getFileName();
                data[2] =   m.getThumbnail();
                data[3] =  m.getLat();
                data[4] = m.getLng();
                data[5] = m.getBounds();

                JSONObject info = new JSONObject();
                info.put("bounds", bounds);
                info.put("size", size);
                info.put("url", m.getFileName());

                imageGeoJsonArray.add(info);

                writer.writeNext(data);
            }

            writer.close();

            FileWriter geoWriter = new FileWriter(fileJson);
            // Writes the content to the file
            geoWriter.write(imageGeoJsonArray.toJSONString());
            geoWriter.flush();
            geoWriter.close();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

    }

}
