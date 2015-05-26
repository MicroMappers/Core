package qa.qcri.mm.api.template;

import org.json.simple.JSONArray;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 4/22/15
 * Time: 11:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class MicroMapsCrisisModel {

    private String name ;
    private String created;
    private String type;
    private String geoJsonLink;
    private String kmlLink;
    private JSONArray category;
    private int status;

    public MicroMapsCrisisModel(String name, String created, String type, String geoJsonLink, String kmlLink, JSONArray category, int status) {
        this.name = name;
        this.created = created;
        this.type = type;
        this.geoJsonLink = geoJsonLink;
        this.kmlLink = kmlLink;
        this.category = category;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public JSONArray getCategory() {
        return category;
    }

    public void setCategory(JSONArray category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
