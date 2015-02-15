package mock;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 9/4/14
 * Time: 1:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class DroneTrackerMock {

    public static String getExistingDroneTrackerMockData(){
        //old : 51.29027104637403,25.799896283494462
        //new : 105.85195399999999,29.787701999999978
        StringBuffer sb = new StringBuffer();
        sb.append("{\"features\":");
        sb.append("{\"type\":\"Feature\",\"properties\":{},");
        sb.append("\"geometry\":{\"type\":\"Point\",");
        sb.append("\"coordinates\":[105.85195399999999,29.787701999999978]},");
        sb.append("\"crs\":{\"type\":\"name\",");
        sb.append("\"properties\":{\"name\":\"urn:ogc:def:crs:OGC:1.3:CRS84\"}}},");
        sb.append("\"info\":{");
        sb.append("\"email\":\"jikimlucas@gmail.com\",");
        sb.append("\"id\":19,");
        sb.append("\"url\":\"http://www.youtube.com/watch?v=Kq6UR9vLj38\"");
        sb.append("}}");

       return sb.toString();
    }
}
