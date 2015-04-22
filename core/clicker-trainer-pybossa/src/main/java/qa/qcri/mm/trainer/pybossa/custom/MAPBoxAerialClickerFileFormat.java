package qa.qcri.mm.trainer.pybossa.custom;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import qa.qcri.mm.trainer.pybossa.entity.ImageMetaData;
import qa.qcri.mm.trainer.pybossa.entity.NamibiaImage;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 3/28/15
 * Time: 4:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class MAPBoxAerialClickerFileFormat {

    public static JSONArray createAerialClickerData(List<ImageMetaData> metaDataList){
        JSONArray jsonArray = new JSONArray();
        JSONArray sizes = getBoundSize();
        JSONParser parser = new JSONParser();
        try{
            //metaDataList.get(i).getBounds()
            for(int i=0; i < metaDataList.size(); i++){
                JSONObject jsonObject = new JSONObject();
                JSONArray bounds = (JSONArray)parser.parse(metaDataList.get(i).getBounds());

                jsonObject.put("bounds", bounds);
                jsonObject.put("size", sizes);
                jsonObject.put("latlng",getLatLng(metaDataList.get(i)));
                jsonObject.put("source", metaDataList.get(i).getFileName());

                jsonObject.put("url", metaDataList.get(i).getFileName());

                jsonArray.add(jsonObject);
            }
        }
        catch (Exception e){
           System.out.println("MAPBoxAerialClickerFileFormat : createAerialClickerData - " + e.getMessage());
        }

        return jsonArray;
    }

    public static JSONArray getBoundSize(){
        JSONArray boundSize = new JSONArray();
        boundSize.add(new Integer(256));
        boundSize.add(new Integer(256));

        return boundSize;
    }

    public static JSONArray getLatLng(ImageMetaData imageMetaData){
        JSONArray coordinates = new JSONArray();
        coordinates.add(Double.parseDouble(imageMetaData.getLat()));
        coordinates.add(Double.parseDouble(imageMetaData.getLng()));

        return coordinates;
    }


}
