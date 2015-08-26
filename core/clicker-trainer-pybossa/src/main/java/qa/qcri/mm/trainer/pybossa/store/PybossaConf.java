package qa.qcri.mm.trainer.pybossa.store;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 11/21/13
 * Time: 11:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class PybossaConf {
    public static int DEFAULT_N_ANSWERS = 3;
    public static int DEFAULT_MAP_RAIDUS_CUT_OFF = 1;


    public static String VIDEO_CLICKER_RESPONSE_SEVERE = "severe";
    public static String VIDEO_CLICKER_RESPONSE_MILD = "mild";

    public static String GEOJSON_TYPE_POINT="point";
    public static String GEOJSON_TYPE_FEATURE_COLLECTION="FeatureCollection";
    //for scd1 setting
    public static String DEFAULT_TRAINER_FILE_PATH = "/mnt/data/trainer/";
    public static String DEFAULT_TRAINER_GEO_FILE_PATH = "/mnt/data/trainer/";
   // for testing
    //public static String DEFAULT_TRAINER_FILE_PATH = "/Users/jlucas/Documents/pybossa/";
    //public static String DEFAULT_TRAINER_GEO_FILE_PATH = "/Users/jlucas/Documents/pybossa/";

    public static double ONE_MILE_RADIUS =  1609.34;
    public static double ONE_MILE_DISTANCE =  1;

    public static String TASK_STATUS_COMPLETED = "completed";

    public static String TASK_QUEUE_GEO_INFO_NOT_FOUND = "No Location Information";

    public static int DEFAULT_GEO_N_ANSWERS = 1;

    public static String GEOJSON_HOME = "/mnt/data/micromaps/";

}
