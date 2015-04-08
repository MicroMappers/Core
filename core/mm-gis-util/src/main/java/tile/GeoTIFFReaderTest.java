package tile;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 2/19/15
 * Time: 9:21 AM
 * To change this template use File | Settings | File Templates.
 */
import java.io.File;
import java.io.IOException;

import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.gce.geotiff.GeoTiffReader;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultEngineeringCRS;
import org.opengis.referencing.crs.CoordinateReferenceSystem;


public class GeoTIFFReaderTest
{

    public static void main(final String[] args)
    {
        try {
           // while(true)
            {
                try {
                    CoordinateReferenceSystem sphericalMercator = CRS.decode("EPSG:3857");
                    // e.g.: ftp://ftp.remotesensing.org/pub/geotiff/samples/usgs/i30dem.tif
                    //GridCoverage2D cov = readGeoTiff("/Users/jlucas/Documents/imagery/test-geo/slice/mosaic_0.tiff");
                    ///Users/jlucas/Documents/ConservationDrones/TEST/Calperum_photos/IMG_5819.JPG
                    String tifURL = "/Users/jlucas/Documents/imagery/29-001_mosaic_group1.tif" ;
                    //String tifURL = "/Users/jlucas/Documents/imagery/test-geo.tif" ;
                    GridCoverage2D cov = readGeoTiff(tifURL);
                    System.out.println("left: " + Double.toString(cov.getEnvelope2D().getMinX()));
                    System.out.println("bottom: " + Double.toString(cov.getEnvelope2D().getMinY()));
                    System.out.println("right: " + Double.toString(cov.getEnvelope2D().getMaxX()));
                    System.out.println("top: " + Double.toString(cov.getEnvelope2D().getMaxY()));
                    System.out.println("bounds: " +  cov.getEnvelope2D().toBounds(sphericalMercator));

                    cov.dispose(true);

                    cov = null;
                } catch (Exception e) {
                    System.out.println("Oh shit!, " + e.getLocalizedMessage());
                }

                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted!");
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