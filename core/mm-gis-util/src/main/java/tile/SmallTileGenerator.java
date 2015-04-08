package tile;

import org.geotools.utils.coveragetiler.CoverageTiler;


import java.io.File;
import java.util.logging.Level;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 2/17/15
 * Time: 9:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class SmallTileGenerator {


    public static void main(String[] args){
       /**
        File inputLoc = new File("/Users/jlucas/Documents/imagery/29-001_mosaic_group1.tif");
        File outputLoc = new File("/Users/jlucas/Documents/imagery/pam") ;
        final CoverageTiler coverageTiler = new CoverageTiler();
        coverageTiler.addProcessingEventListener(coverageTiler);
        coverageTiler.setInputLocation(inputLoc);
        coverageTiler.setOutputLocation(outputLoc);
        coverageTiler.setTileHeight(1200);
        coverageTiler.setTileWidth(1200);

        coverageTiler.run();

      **/
        try{
            final CoverageTiler coverageTiler = new CoverageTiler();
            coverageTiler.addProcessingEventListener(coverageTiler);

           // File inputLoc = new File("/Users/jlucas/Downloads/vrs_accuracy_2_5cm_transparent_mosaic_group1.tif");
            File inputLoc = new File("/Users/jlucas/Documents/imagery/29-001_mosaic_group1.tif");

            File outputLoc = new File("/Users/jlucas/Documents/imagery/pam") ;

            //File inputLoc = new File(sourceURL);

            coverageTiler.setInputLocation(inputLoc);
            coverageTiler.setOutputLocation(outputLoc);

            coverageTiler.setTileHeight(1200);
            coverageTiler.setTileWidth(1200);

            final Thread t = new Thread(coverageTiler, inputLoc.getName().replace(".tif",""));
            t.setPriority(coverageTiler.getPriority());
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
              //  LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
            }
        }
        catch(Exception e2) {
           // LOGGER.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }


    }


}
