package aerial;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 3/25/15
 * Time: 9:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class TileMataData {
    private String url ;
    private double lat;
    private double lng;

    public TileMataData(String url, double lat, double lng){
        this.url = url;
        this.lat = lat;
        this.lng = lng;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
