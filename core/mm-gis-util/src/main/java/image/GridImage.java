package image;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 9/9/14
 * Time: 9:07 AM
 * To change this template use File | Settings | File Templates.
 */

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.UserFeed;
import com.google.gdata.util.ServiceException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class GridImage implements Runnable {
    private int rows, columns;
    private ArrayList<String> stringList = new ArrayList<String>();
    public GridImage(){}

    public GridImage(String foldername, int rows, int columns) {

        this.rows = rows;
        this.columns = columns;

        final File folder = new File(foldername);
        File[] listOfFiles = folder.listFiles();

        StringBuffer sb = new StringBuffer();

        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().contains(".JPG")) {
                String dataEntery = this.processGridImage(file);
                if(dataEntery!=null && !dataEntery.isEmpty())
                {
                    sb.append(dataEntery);

                }
            }
        }

        //generateFile(sb.toString(), foldername);
        generateJson()  ;

    }

    public void generateJson(){
        JSONArray imageGeoJsonArray = new JSONArray();
        JSONArray size = new JSONArray();
        size.add(1024);
        size.add(1024);

        for(String f : stringList){
            JSONObject ele = new JSONObject();
            String sf = f.replace("/Users/jlucas/Downloads/documents-export-2015-03-20/slice2","/static/qatar/slice");
            ele.put("bounds","");
            ele.put("source",sf);
            ele.put("url",sf);
            ele.put("size",size);
            imageGeoJsonArray.add(ele)  ;
        }

        System.out.println(imageGeoJsonArray.toJSONString());
    }


    public String processGridImage(File fileEntry){
        StringBuffer sb = new StringBuffer();
        try {
            BufferedImage image = ImageIO.read(fileEntry);
            int smallWidth = image.getWidth() / this.columns;
            int smallHeight = image.getHeight() / this.rows;
            BufferedImage[][] smallImages = new BufferedImage[this.columns][this.rows];



            int count = 0;
            for (int x = 0; x < columns; x++) {
                for (int y = 0; y < rows; y++) {
                    smallImages[x][y] = image.getSubimage(x * smallWidth, y
                            * smallHeight, smallWidth, smallHeight);
                    try {

                        String fileName = fileEntry.getName().replace(".JPG","_");
                        String printFileName =   fileName;
                        String fileNameExtension =   (count++) + ".jpg";

                        fileName =   fileEntry.getParent()+"/slice2/" + fileName;
                        printFileName = printFileName + fileNameExtension;

                        String slicedFileName =   fileName + fileNameExtension ;
                        stringList.add(slicedFileName);
                        System.out.println(fileEntry.getParent() +"," + fileEntry.getName() + "," + printFileName + System.lineSeparator());

                        ImageIO.write(smallImages[x][y], "jpg", new File( slicedFileName));

                        sb.append(fileEntry.getParent() +"," + fileEntry.getName() + "," + printFileName + System.lineSeparator());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }


    private void generateFile(String output, String filePath){


        String fileName = filePath + "manifest.csv";

        File file = new File(fileName);

        FileWriter fw ;
        BufferedWriter bw;
        try{
            if (!file.exists()) {
                file.createNewFile();
            }
            else{
                fw = new FileWriter(file.getAbsoluteFile());
                bw = new BufferedWriter(fw);
                bw.write("");
                bw.close();
            }

            fw = new FileWriter(file.getAbsoluteFile());
            bw = new BufferedWriter(fw);
            bw.write(output);
            bw.close();
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.print(e.getMessage());
        }
    }
    public void run() {

    }

    public static void main(String[] args) {
        String parentPath = "/Users/jlucas/Downloads/";
        String[] folderName = {"documents-export-2015-03-20"};
        //"day2_rgb_transect_count","day3_rgb_kaelber","day5_rgb_transect_main_road","day5_rgb_transect_rosinki_ixus","day5_rgb_zebra"
       // String parentPath = "/Users/jlucas/Documents/aerialClicker/";
        //String[] folderName = {"day5_rgb_zebra_archive"};
        //String path = "/Users/jlucas/Documents/aerialClicker/day5_rgb_zebra_archive";
        for(int i=0; i < folderName.length; i++){
            String path =  parentPath + folderName[i];
            GridImage image = new GridImage(path,2, 2);
        }
    }


    public  void picasaTest(){
        try {
            PicasawebService picasawebService = new PicasawebService("exampleCo-exampleApp-2");

            PicasawebService myService = new PicasawebService("exampleCo-exampleApp-2");
            myService.setUserCredentials("micromappers2@gmail.com", "micromapperspwd");

            URL feedUrl = new URL("https://picasaweb.google.com/data/feed/api/user/micromappers2?kind=album");

            UserFeed myUserFeed = myService.getFeed(feedUrl, UserFeed.class);

            for (AlbumEntry myAlbum : myUserFeed.getAlbumEntries()) {
                System.out.println(myAlbum.getTitle().getPlainText());
            }
            /**
            AlbumEntry myAlbum = new AlbumEntry();

            myAlbum.setTitle(new PlainTextConstruct("Trip to France"));
            myAlbum.setDescription(new PlainTextConstruct("My recent trip to France was delightful!"));

            picasa.insertAlbum(myAlbum) ;
             **/
        } catch (IOException e) {
            System.out.print(e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ServiceException e) {
            System.out.print(e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catch(Exception e){
            System.out.print(e.getMessage());
            e.printStackTrace();
        }
    }
}
