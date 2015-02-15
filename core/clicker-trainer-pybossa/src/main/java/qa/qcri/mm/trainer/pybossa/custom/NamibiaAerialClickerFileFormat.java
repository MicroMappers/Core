package qa.qcri.mm.trainer.pybossa.custom;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import qa.qcri.mm.trainer.pybossa.entity.NamibiaImage;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 9/9/14
 * Time: 3:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class NamibiaAerialClickerFileFormat {

    public static JSONArray createAerialClickerData(List<NamibiaImage> namibiaImages){
        JSONArray jsonArray = new JSONArray();
        JSONArray sizes = getBoundSize();

        for(int i=0; i < namibiaImages.size(); i++){
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("bounds","");
            jsonObject.put("size", sizes);
            jsonObject.put("source", namibiaImages.get(i).getSource());
            String url = namibiaImages.get(i).getPath() +"/" +namibiaImages.get(i).getGridImage();
            jsonObject.put("url", url);

            jsonArray.add(jsonObject);
        }

       return jsonArray;
    }

    public static JSONArray getBoundSize(){
        JSONArray boundSize = new JSONArray();
        boundSize.add(new Integer(256));
        boundSize.add(new Integer(256));

        return boundSize;
    }
}
