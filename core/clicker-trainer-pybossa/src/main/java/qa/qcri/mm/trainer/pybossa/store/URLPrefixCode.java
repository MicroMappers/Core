package qa.qcri.mm.trainer.pybossa.store;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 11/20/13
 * Time: 1:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class URLPrefixCode {
    public static String ASSINGN_TASK = "/document/getbatchtaskbuffer/";
    public static String TASK_PUBLISH = "/task?api_key=";
    public static String TASK_ANSWER_SAVE = "/taskanswer/save";
    public static String TASK_INFO = "/task?app_id=";
    public static String TASKRUN_INFO = "/taskrun?app_id=";
    public static String ASSIGN_TASK_CLEANUP= "/taskassignment/revert/timeout";

    public static String AIDR_ACTIVE_NOMINAL_ATTRIBUTE ="/crisis/getnominalAttribute";
    public static String AIDR_CRISIS_INFO="/crisis/id/";
    public static String AIDR_NOMINAL_ATTRIBUTE_LABEL = "/crisis/getnominalLabels/";
    public static String AIDR_TASKASSIGNMENT_REVERT= "/taskassignment/revert/name/";

    public static String PYBOSAA_APP = "/app/";
    public static String PYBOSSA_APP_KEY ="/app?api_key=";
    public static String PYBOSSA_SHORT_NAME="/app?short_name=";
    public static String PYBOSSA_APP_UPDATE_KEY = "?api_key=";
    public static String PYBOSSA_CATEGORY = "/category";
    public static String PYBOSSA_CATEGORY_SHORT_NAME = "?short_name=";

    public static String PYBOSSA_TASK_DELETE = "/task/";

}
