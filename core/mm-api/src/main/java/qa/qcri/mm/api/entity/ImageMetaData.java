package qa.qcri.mm.api.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 3/28/15
 * Time: 12:13 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "imageMetaData")
public class ImageMetaData implements Serializable {
    private static final long serialVersionUID = -5527566248002296042L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column (name = "path", nullable = true)
    private String path;

    @Column (name = "fileName", nullable = true)
    private String fileName;

    @Column (name = "thumbnail", nullable = true)
    private String thumbnail;

    @Column (name = "lat", nullable = true)
    private String lat;

    @Column (name = "lng", nullable = true)
    private String lng;

    @Column (name = "bounds", nullable = true)
    private String bounds;

    public ImageMetaData() {
    }

    public ImageMetaData(String fileName, String lat, String lng){
        this.fileName = fileName;
        this.lat = lat;
        this.lng = lng;
    }

    public ImageMetaData(String fileName, String lat, String lng, String bounds){
        this.fileName = fileName;
        this.lat = lat;
        this.lng = lng;
        this.bounds = bounds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
