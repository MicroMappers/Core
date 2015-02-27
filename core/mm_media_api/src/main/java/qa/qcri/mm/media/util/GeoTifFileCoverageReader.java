package qa.qcri.mm.media.util;

import java.io.File;
import java.io.IOException;

import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.gce.geotiff.GeoTiffReader;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 2/19/15
 * Time: 9:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class GeoTifFileCoverageReader {

    public static void initFileReader(String fileLocation)
    {
        try {
            try {
                GridCoverage2D cov = readGeoTiff(fileLocation);

                System.out.println("left: " + Double.toString(cov.getEnvelope2D().getMinX()));
                System.out.println("bottom: " + Double.toString(cov.getEnvelope2D().getMinY()));
                System.out.println("right: " + Double.toString(cov.getEnvelope2D().getMaxX()));
                System.out.println("top: " + Double.toString(cov.getEnvelope2D().getMaxY()));

                cov.dispose(true);

            } catch (IOException e) {
                System.out.println("Oh shit!, " + e.getLocalizedMessage());
            }

        } catch (Exception e) {
            System.out.println("Interrupted! : " + e.getStackTrace());
        }
    }

    public static GridCoverage2D readGeoTiff(final String geotiffFileNamePath) throws IOException
    {
        File file = new File(geotiffFileNamePath);
        System.out.println("file found: " + file.getAbsolutePath());
        GeoTiffReader reader = new GeoTiffReader(file);
        GridCoverage2D cov = reader.read(null);
        reader.dispose();
        return cov;
    }
}
