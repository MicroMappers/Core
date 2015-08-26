package qa.qcri.mm.trainer.pybossa.store;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 11/3/13
 * Time: 9:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class StatusCodeType {

    public final static Integer Task_NOT_PUBLISHED = 0;
    public final static Integer TASK_PUBLISHED = 1;
    public final static Integer TASK_LIFECYCLE_COMPLETED = 2;
    public final static Integer TASK_EXPORTED = 3;
    public final static Integer TASK_ABANDONED = 4;

    public final static Integer MAX_PENDING_QUEUE_SIZE = 50;

    public final static String TASK_COMMIT_SUCCESS = "success";


    public final static Integer HTTP_OK = 200;
    public final static Integer HTTP_OK_NO_CONTENT = 204;
    public final static Integer HTTP_OK_DUPLICATE_INFO_FOUND = 415 ;

    public final static Integer RESPONSE_MIN_LENGTH = 10;

    public final static Integer CLIENT_APP_PENDING = 0;
    public final static Integer MICROMAPPER_ONLY = 2;


    public final static Integer EXTERNAL_DATA_SOURCE_USED = 2;
    public final static Integer EXTERNAL_DATA_SOURCE_ACTIVE = 1;
    public final static Integer EXTERNAL_DATA_SOURCE_UPLOADED = 0;
    public final static Integer EXTERNAL_DATA_SOURCE_TO_GEO_READY_REPORT = 3;


    public final static Integer TEMPLATE_IS_READY_FOR_EXPORT = 0;
    public final static Integer TEMPLATE_EXPORTED = 1;

    public final static Integer APP_MULTIPLE_CHOICE = 1;
    public final static Integer APP_IMAGE = 2;
    public final static Integer APP_VIDEO = 3;
    public final static Integer APP_MAP = 4;
    public final static Integer APP_AERIAL = 5;
    public final static Integer APP_3W = 6;


    public final static String EXCEPTION_STRING = "Exception";
    public final static String TABLE_NAME = "clientApp";
    public final static String COLMN_NAME = "importQueueSize";


    public final static Integer CLIENT_ACTIVE = 1;

    public final static Integer MAPBOX_TILE_IMPORTED = 0;
    public final static Integer MAPBOX_TILE_EXPORTED = 1;

    public final static Integer MAX_CSV_ROW_QUEUE_SIZE = 20;

}
