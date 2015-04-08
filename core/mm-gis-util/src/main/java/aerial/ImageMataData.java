package aerial;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 3/25/15
 * Time: 9:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImageMataData {
    private String path = "";
    private String fileName;
    private String thumbnail = "";
    private String lat;
    private String lng;
    private String bounds = "";

    public ImageMataData(){}

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getBounds() {
        return bounds;
    }

    public void setBounds(String bounds) {
        this.bounds = bounds;
    }
}
