package qa.qcri.mm.trainer.pybossa.format.impl;

import org.json.simple.JSONArray;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 9/17/14
 * Time: 10:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class NanimiaOutputFormat {

    private JSONArray geoFeatures;
    private String sourceImage;
    private String slicedImage;
    private Integer foundCount;
    private Integer noFoundCount;

    public NanimiaOutputFormat(){}

    public NanimiaOutputFormat(JSONArray geoFeatures, String sourceImage, String slicedImage, Integer foundCount, Integer noFoundCount)
    {
        this.geoFeatures = geoFeatures;
        this.sourceImage = sourceImage;
        this.slicedImage = slicedImage;
        this.foundCount = foundCount;
        this.noFoundCount = noFoundCount;
    }

    public JSONArray getGeoFeatures() {
        return geoFeatures;
    }

    public void setGeoFeatures(JSONArray geoFeatures) {
        this.geoFeatures = geoFeatures;
    }

    public String getSourceImage() {
        return sourceImage;
    }

    public void setSourceImage(String sourceImage) {
        this.sourceImage = sourceImage;
    }

    public String getSlicedImage() {
        return slicedImage;
    }

    public void setSlicedImage(String slicedImage) {
        this.slicedImage = slicedImage;
    }

    public Integer getFoundCount() {
        return foundCount;
    }

    public void setFoundCount(Integer foundCount) {
        this.foundCount = foundCount;
    }

    public Integer getNoFoundCount() {
        return noFoundCount;
    }

    public void setNoFoundCount(Integer noFoundCount) {
        this.noFoundCount = noFoundCount;
    }

}
