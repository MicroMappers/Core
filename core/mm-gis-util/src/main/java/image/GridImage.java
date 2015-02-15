package image;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 9/9/14
 * Time: 9:07 AM
 * To change this template use File | Settings | File Templates.
 */

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GridImage implements Runnable {
    private int rows, columns;

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

        generateFile(sb.toString(), foldername);

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

                        fileName =   fileEntry.getParent()+"/slice/" + fileName;
                        printFileName = printFileName + fileNameExtension;

                        String slicedFileName =   fileName + fileNameExtension ;

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
        }
    }
    public void run() {

    }

    public static void main(String[] args) {
        String parentPath = "/Users/jlucas/Documents/aerialClicker/original/savmap_images_archive/";
        String[] folderName = {"day5_rgb_transect_main_road","day5_rgb_transect_rosinki_ixus","day5_rgb_zebra"};
        //"day2_rgb_transect_count","day3_rgb_kaelber","day5_rgb_transect_main_road","day5_rgb_transect_rosinki_ixus","day5_rgb_zebra"
       // String parentPath = "/Users/jlucas/Documents/aerialClicker/";
        //String[] folderName = {"day5_rgb_zebra_archive"};
        //String path = "/Users/jlucas/Documents/aerialClicker/day5_rgb_zebra_archive";
        for(int i=0; i < folderName.length; i++){
            String path =  parentPath + folderName[i];
            GridImage image = new GridImage(path,2, 2);
        }

    }
}
