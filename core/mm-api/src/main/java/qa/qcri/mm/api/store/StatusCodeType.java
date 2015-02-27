package qa.qcri.mm.api.store;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 11/1/13
 * Time: 8:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class StatusCodeType {

    public static Integer Task_NOT_PUBLISHED = 0;
    public static Integer TASK_PUBLISHED = 1;
    public static Integer TASK_LIFECYCLE_COMPLETED = 2;
    public static Integer TASK_EXPORTED = 3;
    public static Integer TASK_ABANDONED = 4;


    public static Integer MICROMAPPER_ONLY = 2;
    public static Integer AIDR_ONLY = 1;
    public static Integer CLIENT_APP_INACTIVE = 3;
    public static Integer CLIENT_APP_PENDING = 0;
    public static Integer CLIENT_APP_DISABLED = 4;
    public static Integer AIDR_MICROMAPPER_BOTH = 5;



    public static String POST_COMPLETED = "{\"status\":200}";

    public static Integer EXTERNAL_DATA_SOURCE_USED = 2;
    public static Integer EXTERNAL_DATA_SOURCE_ACTIVE = 1;
    public static Integer EXTERNAL_DATA_SOURCE_UPLOADED = 0;
    public static Integer EXTERNAL_DATA_SOURCE_TO_GEO_READY_REPORT = 3;

    public static Integer DEPLOYMENT_PENDING  = 0;
    public static Integer DEPLOYMENT_ACTIVE = 1;
    public static Integer DEPLOYMENT_RETIRED = 2;
    public static Integer DEPLOYMENT_INACTIVE = 3;
    public static Integer DEPLOYMENT_MOBILE = 4;

    public static String RETURN_SUCCESS = "{\"return\": \"success\"}";
    public static String RETURN_FAIL = "{\"return\": \"failed\", , \"status\":500}";

    public static Integer DRONE_VIDEO_PENDING = 0;
    public static Integer DRONE_VIDEO_APPROVED = 1;
    public static Integer DRONE_VIDEO_REJECTED = 2;

    public static String MOBILE_STATUS_UPDATE_FAIL_WITH_NO_APP = "{\"return\": \"no clientApp found\", \"status\":500}";
    public static String MOBILE_STATUS_UPDATE_FAIL_MULTI_APP = "{\"return\": \"more than 1 active clientApp found\", \"status\":500}";
    public static String MOBILE_STATUS_UPDATE_FAIL_RUNING_APP = "{\"return\": \"running clientApp found\", \"status\":500}";
}
