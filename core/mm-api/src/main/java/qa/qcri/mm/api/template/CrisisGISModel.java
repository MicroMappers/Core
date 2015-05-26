package qa.qcri.mm.api.template;

import org.json.simple.JSONObject;

import java.util.Date;

/**
 * Created by jlucas on 5/13/15.
 */
public class CrisisGISModel {

    private Long clientAppID;
    private String name;
    private String type;
    private Date activationStarted;
    private Date activationEnded;
    private String geoJsonLink;
    private String kmlLink;
    private JSONObject style;

    public CrisisGISModel(Long clientAppID, String name, String type, Date activationStarted, Date activationEnded, String geoJsonLink, String kmlLink, JSONObject style) {
        this.clientAppID = clientAppID;
        this.name = name;
        this.type = type;
        this.activationStarted = activationStarted;
        this.activationEnded = activationEnded;
        this.geoJsonLink = geoJsonLink;
        this.kmlLink = kmlLink;
        this.style = style;
    }

    public Long getClientAppID() {
        return clientAppID;
    }

    public void setClientAppID(Long clientAppID) {
        this.clientAppID = clientAppID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getActivationStarted() {
        return activationStarted;
    }

    public void setActivationStarted(Date activationStarted) {
        this.activationStarted = activationStarted;
    }

    public Date getActivationEnded() {
        return activationEnded;
    }

    public void setActivationEnded(Date activationEnded) {
        this.activationEnded = activationEnded;
    }

    public String getGeoJsonLink() {
        return geoJsonLink;
    }

    public void setGeoJsonLink(String geoJsonLink) {
        this.geoJsonLink = geoJsonLink;
    }

    public String getKmlLink() {
        return kmlLink;
    }

    public void setKmlLink(String kmlLink) {
        this.kmlLink = kmlLink;
    }

    public JSONObject getStyle() {
        return style;
    }

    public void setStyle(JSONObject style) {
        this.style = style;
    }
}
