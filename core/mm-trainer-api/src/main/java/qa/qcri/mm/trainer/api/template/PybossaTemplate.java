package qa.qcri.mm.trainer.api.template;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import qa.qcri.mm.trainer.api.entity.*;

import java.util.Iterator;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: jilucas
 * Date: 9/16/13
 * Time: 4:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class PybossaTemplate {

    private boolean findMatchingLabel(String[] categorySet, String label){

        boolean found = false;
        for(int i=0; i< categorySet.length; i++){
              String category =  categorySet[i];
              if(category.equalsIgnoreCase(label)) {
                  found = true;
              }
        }

        return found;

    }

    private String getJedisJson(Long crisisID, JSONArray attributeIDJson){
        JSONObject jedisJson = new JSONObject();

        jedisJson.put("crisis_id", crisisID);
        jedisJson.put("attributes", attributeIDJson);

        return jedisJson.toJSONString();

    }

    private JSONObject getUtilDatJson(JSONObject featureJsonObj){

        JSONObject utilJson = new JSONObject();
        utilJson.put("clientuserID",featureJsonObj.get("user_id")) ;
        utilJson.put("datetimelog",featureJsonObj.get("dateHistory")) ;

        return utilJson;
    }
}
