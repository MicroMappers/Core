package qa.qcri.mm.api.template;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 4/23/15
 * Time: 4:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class MicroMapsCategoryModel {

    private String category;
    private String markerColor;

    public MicroMapsCategoryModel(String category, String markerColor) {
        this.category = category;
        this.markerColor = markerColor;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMarkerColor() {
        return markerColor;
    }

    public void setMarkerColor(String markerColor) {
        this.markerColor = markerColor;
    }
}
