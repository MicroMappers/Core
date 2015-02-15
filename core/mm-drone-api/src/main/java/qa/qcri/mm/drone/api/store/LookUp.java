package qa.qcri.mm.drone.api.store;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 11/1/13
 * Time: 8:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class LookUp {

    public static String RETURN_SUCCESS = "{\"return\": \"success\"}";
    public static String RETURN_FAIL = "{\"return\": \"failed\"}";

    public static Integer DRONE_VIDEO_PENDING = 0;
    public static Integer DRONE_VIDEO_APPROVED = 1;
    public static Integer DRONE_VIDEO_REJECTED = 2;
    public static Integer DRONE_VIDEO_REMOVED = 3;


    public static int REQUEST_INPUT_DATA_INVALID =501;
    public static int REQUEST_INPUT_DATA_MISSING=502;
    public static int REQUEST_INPUT_DATA_MISMATCH=503;
    public static int REQUEST_INPUT_DATA_NOT_FOUND = 504;
    public static int REQUEST_SUCCESS = 200;

}
