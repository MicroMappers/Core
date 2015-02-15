package qa.qcri.mm.drone.api.util;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 9/4/14
 * Time: 5:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class HTTPResultTemplate {

    public static String buildStatusResult(int returnValue){
        //public static String RETURN_SUCCESS = "{\"return\": \"success\"}";
        StringBuffer sb = new StringBuffer();
        sb.append("{\"status\":");
        sb.append(returnValue);
        sb.append("}");

        return sb.toString();
    }
}
