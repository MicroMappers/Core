package tile;

import org.geotools.console.CommandLine;
import org.geotools.console.Option;
import org.geotools.gce.imagemosaic.ImageMosaicConfigHandler;
import org.geotools.gce.imagemosaic.ImageMosaicDirectoryWalker;
import org.geotools.gce.imagemosaic.ImageMosaicEventHandlers;
import org.geotools.gce.imagemosaic.Utils;
import org.geotools.gce.imagemosaic.catalogbuilder.CatalogBuilderConfiguration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is in responsible for creating the index for a mosaic of images
 * that we want to tie together as a single coverage.
 *
 * <p>
 * To get instructions on how to run the tool just run it without any argument
 * and a nice and clean help message will be printed to the command line.
 *
 * <p>
 * Anyway an example of a suitable list of argumentBuilder can be seen here
 * below:
 *
 * <p>
 * -s H:\\work\\data\\merano_aime -w *.tif -name merano -abs
 * <p>
 * where:
 * <ol>
 * <li>-s H:\\work\\data\\merano_aime is the source directory</li>
 * <li>-w *.tif is he wildcard for the files to process</li>
 * <li>-name merano sets the name for the output shape</li>
 * <li>-abs asks the tool to use absolute paths instead of relative</li>
 * </ol>
 *
 *
 * <p>
 * It is worth to point out that this tool comes as a command line tool but it
 * has been built with GUI in mind . It has the capability to register
 * {@link org.geotools.utils.progress.ProcessingEventListener} object that receive notifications about what
 * is going on. Moreover it delegates all the computations to an external
 * thread, hence we can stop the tool in the middle of processing with no so
 * many concerns (hopefully :-) ).
 * <p>
 *
 *
 * @author Simone Giannecchini, GeoSolutions
 * @author Alessio Fabiani, GeoSolutions
 * @author Blaz Repnik
 *
 *
 *
 *
 * @source $URL$
 * @version 0.3
 *
 */
public class ImageMosaicTester extends CommandLine {

    private final static Logger LOGGER = org.geotools.util.logging.Logging.getLogger(ImageMosaicTester.class.toString());


    @Option(description = "This index must use absolute or relative path", mandatory = false, name = "absolute")
    private Boolean absolute;

    @Option(description = "This index can use caching or not", mandatory = false, name = "caching")
    private Boolean caching = Utils.DEFAULT_CONFIGURATION_CACHING;

    @Option(description = "Directories where to look for file to index", mandatory = false, name = "indexingDirectories")
    private String indexingDirectoriesString;

    @Option(description = "This index must handle footprint", mandatory = false, name = "footprintManagement")
    private Boolean footprintManagement;

    /**
     * Index file name. Default is index.
     */
    @Option(description = "Name to use for the index of this mosaic", mandatory = false, name = "index")
    private String indexName;

    @Option(description = "Root directory where to place the index file", mandatory = false, name = "rootDirectory")
    private String rootMosaicDirectory;

    @Option(description = "Wildcard to use for building the index of this mosaic", mandatory = false, name = "wildcard")
    private String wildcardString = Utils.DEFAULT_WILCARD;

    @Option(description = "Default location attribute for this index", mandatory = false, name = "locationAttribute")
    private String locationAttribute = Utils.DEFAULT_LOCATION_ATTRIBUTE;

    public ImageMosaicTester(String[] args) {
        //this.indexingDirectoriesString = "";
        super(args);
        this.indexingDirectoriesString = "/Users/jlucas/Documents/ConservationDrones/Test/Calperum_photos";
        this.rootMosaicDirectory = "/Users/jlucas/Documents/ConservationDrones/Test/slice";
        if (this.absolute == null) {
            this.absolute = Utils.DEFAULT_PATH_BEHAVIOR;

        }
        if (this.caching == null) {
            this.caching = Utils.DEFAULT_CONFIGURATION_CACHING;
        }
        if (this.footprintManagement == null) {
            this.footprintManagement = Utils.DEFAULT_FOOTPRINT_MANAGEMENT;
        }
        if (this.indexName == null)
            this.indexName = Utils.DEFAULT_INDEX_NAME;
    }

    public static void main(String args[]) {

        final ImageMosaicTester runner = new ImageMosaicTester(args);
        // prepare the configuration
        /**
        -s
                /Users/jlucas/Documents/ConservationDrones/Drone
        images
                Oct
        2014/Calperum_photos
                -w
                *.JPG
                -name
        testJi
                -abs
         **/
        final CatalogBuilderConfiguration configuration = new CatalogBuilderConfiguration();
        configuration.setParameter(Utils.Prop.ABSOLUTE_PATH, runner.absolute.toString());
        configuration.setParameter(Utils.Prop.INDEX_NAME, runner.indexName);
        configuration.setParameter(Utils.Prop.FOOTPRINT_MANAGEMENT, runner.footprintManagement.toString());
        configuration.setParameter(Utils.Prop.CACHING, runner.caching.toString());
        configuration.setParameter(Utils.Prop.ROOT_MOSAIC_DIR, runner.rootMosaicDirectory);
        configuration.setParameter(Utils.Prop.WILDCARD, runner.wildcardString);
        configuration.setParameter(Utils.Prop.LOCATION_ATTRIBUTE, runner.locationAttribute);

//        configuration.setAbsolute(runner.absolute);
//        configuration.setIndexName(runner.indexName);
//        configuration.setFootprintManagement(runner.footprintManagement);
//        configuration.setCaching(runner.caching);
//        configuration.setRootMosaicDirectory(runner.rootMosaicDirectory);
//        configuration.setWildcard(runner.wildcardString);
//        configuration.setLocationAttribute(runner.locationAttribute);

        final String directories = runner.indexingDirectoriesString;
        final String[] dirs_ = directories.split(",");
        final List<String> dirs = new ArrayList<String>();
        for (String dir : dirs_)
            dirs.add(dir);
//        configuration.setIndexingDirectories(dirs);
        configuration.setParameter(Utils.Prop.INDEXING_DIRECTORIES, directories);


        // prepare and run the index builder
        final ImageMosaicEventHandlers eventHandler=new ImageMosaicEventHandlers();
        final ImageMosaicConfigHandler catalogHandler = new ImageMosaicConfigHandler(configuration, eventHandler);
        final ImageMosaicDirectoryWalker builder = new ImageMosaicDirectoryWalker(catalogHandler, eventHandler);
        // this is going to help us with catching exceptions and logging them
        final Queue<Throwable> exceptions = new LinkedList<Throwable>();
        try {

            final ImageMosaicEventHandlers.ProcessingEventListener listener = new ImageMosaicEventHandlers.ProcessingEventListener() {

                @Override
                public void exceptionOccurred(ImageMosaicEventHandlers.ExceptionEvent event) {
                    final Throwable t = event.getException();
                    exceptions.add(t);
                    if (LOGGER.isLoggable(Level.SEVERE))
                        LOGGER.log(Level.SEVERE, t.getLocalizedMessage(), t);

                }

                @Override
                public void getNotification(ImageMosaicEventHandlers.ProcessingEvent event) {
                    if (LOGGER.isLoggable(Level.FINE))
                        LOGGER.fine(event.getMessage());

                }

            };
            eventHandler.addProcessingEventListener(listener);
            builder.run();
        } catch (Throwable e) {
            LOGGER.log(Level.SEVERE, "Unable to build mosaic", e);
        } finally {
            catalogHandler.dispose();
        }

    }

}
