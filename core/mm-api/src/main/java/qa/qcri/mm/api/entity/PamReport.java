package qa.qcri.mm.api.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 4/10/15
 * Time: 2:15 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "pamReport")
public class PamReport implements Serializable {

    private static final long serialVersionUID = -5527566248002296042L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    //taskID,lng,lat,imgurl, userID,damageType,geo

    @Column(name = "taskID", nullable = false)
    private String taskID;

    @Column(name = "lng", nullable = false)
    private String lng;

    @Column(name = "lat", nullable = false)
    private String lat;

    @Column(name = "imgurl", nullable = false)
    private String imgurl;

    @Column(name = "userID", nullable = false)
    private String userID;

    @Column(name = "damageType", nullable = false)
    private String damageType;

    @Column(name = "geo", nullable = false)
    private String geo;

    public PamReport() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDamageType() {
        return damageType;
    }

    public void setDamageType(String damageType) {
        this.damageType = damageType;
    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }
}
