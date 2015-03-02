package qa.qcri.mm.media.tool;

import org.geotools.utils.coveragetiler.CoverageTiler;
import java.io.IOException;
import java.util.logging.Level;

import qa.qcri.mm.media.dao.MediaPoolDao;
import qa.qcri.mm.media.store.LookUp;

import java.io.File;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 2/22/15
 * Time: 9:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class GeoTifSlicer {

    private final static Logger LOGGER = Logger.getLogger(GeoTifSlicer.class.toString());


    public GeoTifSlicer(){}

    public void addSlicerQueue(String sourceURL){
        try{
            final CoverageTilerExtended coverageTiler = new CoverageTilerExtended();
            coverageTiler.addProcessingEventListener(coverageTiler);

            File inputLoc = new File(sourceURL);

            coverageTiler.setInputLocation(inputLoc);
            coverageTiler.setOutputLocation(getDestinationLocation(inputLoc));

            coverageTiler.setTileHeight(LookUp.TILE_HEIGHT);
            coverageTiler.setTileWidth(LookUp.TILE_WIDTH);


            final Thread t = new Thread(coverageTiler, inputLoc.getName().replace(".tif",""));
            t.setPriority(coverageTiler.getPriority());
            t.start();

            try {
                t.join();
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
            }
        }
        catch(Exception e2) {
            LOGGER.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }


    }

    private File getDestinationLocation(File inputLoc) throws IOException {

        String fileName = inputLoc.getName();
        fileName = fileName.replace(".tif","");


        String path = inputLoc.getParent() + File.separator + fileName + File.separator + "slice" + File.separator;

        System.out.print("1" + inputLoc.getAbsolutePath());
        System.out.print("2" + inputLoc.getCanonicalPath());
        System.out.print("3" + inputLoc.getName());
        System.out.print("4" + inputLoc.getParent());
        System.out.print("5" + inputLoc.getPath());



        File outputLoc = new File(path) ;
        outputLoc.mkdirs();

        return outputLoc;
    }

}
