package qa.qcri.mm.trainer.pybossa.format.impl;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.util.StringUtils;
import qa.qcri.mm.trainer.pybossa.entity.*;
import qa.qcri.mm.trainer.pybossa.service.ReportTemplateService;
import qa.qcri.mm.trainer.pybossa.store.PybossaConf;
import qa.qcri.mm.trainer.pybossa.store.StatusCodeType;
import qa.qcri.mm.trainer.pybossa.util.DataFormatValidator;
import qa.qcri.mm.trainer.pybossa.util.JsonSorter;
import qa.qcri.mm.trainer.pybossa.util.LatLngUtils;
import qa.qcri.mm.trainer.pybossa.util.StreamConverter;

import java.io.InputStream;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: jilucas
 * Date: 9/17/13
 * Time: 4:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class MicroMapperPybossaFormatter {
    protected static Logger logger = Logger.getLogger("MicroMapperPybossaFormatter");
    public MicroMapperPybossaFormatter(){}

    public List<String> assemblePybossaTaskPublishForm( List<MicromapperInput> inputSources, ClientApp clientApp) throws Exception {

        List<String> outputFormatData = new ArrayList<String>();

        Iterator itr= inputSources.iterator();

        while(itr.hasNext()){
            MicromapperInput micromapperInput = (MicromapperInput)itr.next();
            JSONObject info = assemblePybossaInfoFormat(micromapperInput, clientApp) ;

            JSONObject tasks = new JSONObject();

            tasks.put("info", info);
            tasks.put("n_answers", clientApp.getTaskRunsPerTask());
            tasks.put("quorum", clientApp.getQuorum());
            tasks.put("calibration", new Integer(0));
            tasks.put("app_id", clientApp.getPlatformAppID());
            tasks.put("priority_0", new Integer(0));

            outputFormatData.add(tasks.toJSONString());

        }

        return outputFormatData;
    }

    private JSONObject assemblePybossaInfoFormat(MicromapperInput micromapperInput, ClientApp clientApp) throws Exception{


        JSONObject pybossaData = new JSONObject();
        pybossaData.put("question","please tag it.");

        if(clientApp.getAppType() == StatusCodeType.APP_MAP ){
            pybossaData = createGeoClickerInfo(pybossaData, micromapperInput);
            return  pybossaData;
        }


        if(clientApp.getAppType() == StatusCodeType.APP_AERIAL){
            pybossaData = createAerialClickerInfo(pybossaData, micromapperInput);
            return  pybossaData;
        }

        if(clientApp.getAppType() == StatusCodeType.APP_3W){
            pybossaData = create3WClickerInfo(pybossaData, micromapperInput);
            return  pybossaData;
        }
        // otherwise, process no geoclicker
        pybossaData = createNonGeoClickerInfo(pybossaData, micromapperInput);

        return pybossaData;
    }

    private JSONObject createNonGeoClickerInfo(JSONObject pybossaData, MicromapperInput micromapperInput ){

        pybossaData.put("author",micromapperInput.getAuthor());
        pybossaData.put("tweetid",micromapperInput.getTweetID());
        pybossaData.put("userID",micromapperInput.getAuthor());
        pybossaData.put("tweet",micromapperInput.getTweet());
        pybossaData.put("timestamp",micromapperInput.getCreated());
        pybossaData.put("lat",micromapperInput.getLat());
        pybossaData.put("lon",micromapperInput.getLng());
        pybossaData.put("url",micromapperInput.getUrl());
        pybossaData.put("imgurl",micromapperInput.getUrl());

        return pybossaData;

    }

    private JSONObject createGeoClickerInfo(JSONObject pybossaData, MicromapperInput micromapperInput ){

        pybossaData.put("author",micromapperInput.getAuthor());
        pybossaData.put("tweetid",micromapperInput.getTweetID());
        pybossaData.put("userID",micromapperInput.getAuthor());
        pybossaData.put("tweet",micromapperInput.getTweet());
        pybossaData.put("timestamp",micromapperInput.getCreated());
        pybossaData.put("lat",micromapperInput.getLat());
        pybossaData.put("lon",micromapperInput.getLng());
        pybossaData.put("url",micromapperInput.getUrl());
        pybossaData.put("imgurl",micromapperInput.getUrl());
        pybossaData.put("category",micromapperInput.getUrl());

        return pybossaData;
    }

    private JSONObject createAerialClickerInfo(JSONObject pybossaData, MicromapperInput micromapperInput ){

        pybossaData.put("url",micromapperInput.getUrl());
        pybossaData.put("imgurl",micromapperInput.getUrl());
        pybossaData.put("geo",micromapperInput.getGeo());
        pybossaData.put("mediaSize",micromapperInput.getMediaSize());
        pybossaData.put("category",micromapperInput.getUrl());
        pybossaData.put("mediasource",micromapperInput.getMediasSource());

        return pybossaData;
    }

    private JSONObject create3WClickerInfo(JSONObject pybossaData, MicromapperInput micromapperInput ){

        pybossaData.put("glide",micromapperInput.getGlide());
        pybossaData.put("link",micromapperInput.getLink());
        pybossaData.put("where",micromapperInput.getWhere());
        pybossaData.put("who",micromapperInput.getWho());
        pybossaData.put("langcode",micromapperInput.getLang());

        return pybossaData;
    }

    public boolean isTaskStatusCompleted(String data) throws Exception{
        /// will do later for importing process
        boolean isCompleted = false;
        if(DataFormatValidator.isValidateJson(data)){
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(data);
            JSONArray jsonObject = (JSONArray) obj;

            Iterator itr= jsonObject.iterator();

            while(itr.hasNext()){
                JSONObject featureJsonObj = (JSONObject)itr.next();
              //  logger.debug("featureJsonObj : " +  featureJsonObj);
                String status = (String)featureJsonObj.get("state") ;
              //  logger.debug("status : "  + status);
                if(status.equalsIgnoreCase(PybossaConf.TASK_STATUS_COMPLETED))
                {
                    isCompleted = true;
                }
            }

        }
        return isCompleted;
    }

    public TaskQueueResponse getAnswerResponseForVideo(ClientApp clientApp, String pybossaResult, JSONParser parser, Long taskQueueID, ClientAppAnswer clientAppAnswer, ReportTemplateService rtpService) throws Exception{

        JSONObject responseJSON = new JSONObject();

        JSONArray array = (JSONArray) parser.parse(pybossaResult) ;

        Iterator itr= array.iterator();
        String answer = null;

        JSONObject infoToSave = null;
        HashMap<Integer, Integer> severeTimeStamp = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> mildTimeStamp = new HashMap<Integer, Integer>();

        while(itr.hasNext()){
            JSONObject featureJsonObj = (JSONObject)itr.next();
            JSONObject info = (JSONObject)featureJsonObj.get("info");

            if(info.get("damage")!=null) {
                if(info.get("damage").toString().isEmpty()){
                    answer = "";
                }
                else{
                    String answerInfo = (String)info.get("damage");
                    JSONObject answers = (JSONObject)parser.parse(answerInfo);
                    String severeAnswer = (String)answers.get("severe");
                    String mildAnswer = (String)answers.get("mild");
                    if(severeAnswer!=null){
                        severeTimeStamp = addToTimeStampList(severeTimeStamp, severeAnswer);
                    }
                    if(mildAnswer != null){
                        mildTimeStamp = addToTimeStampList(mildTimeStamp, mildAnswer);
                    }

                    for (Map.Entry entry : mildTimeStamp.entrySet()) {
                        if((Integer)entry.getValue() >= clientAppAnswer.getVoteCutOff()) {
                            infoToSave = info;
                        }
                    }

                    for (Map.Entry entry : severeTimeStamp.entrySet()) {
                        if((Integer)entry.getValue() >= clientAppAnswer.getVoteCutOff()) {
                            infoToSave = info;
                        }
                    }
                }
            }

        }

        severeTimeStamp = removeItemBelowCutOffForVideo(severeTimeStamp, clientAppAnswer);
        mildTimeStamp =   removeItemBelowCutOffForVideo(mildTimeStamp, clientAppAnswer);

        if( severeTimeStamp.size() > 0){

            responseJSON.put("severe", StringUtils.collectionToCommaDelimitedString(severeTimeStamp.keySet()));

        }
        else{
            responseJSON.put("severe", "");
        }
        if( mildTimeStamp.size() > 0){
            responseJSON.put("mild", StringUtils.collectionToCommaDelimitedString(mildTimeStamp.keySet()));

        }
        else{
            responseJSON.put("mild", "");
        }

        handleItemAboveCutOffForVideo(taskQueueID, severeTimeStamp, mildTimeStamp, infoToSave, clientAppAnswer, rtpService);

        TaskQueueResponse taskQueueResponse = new TaskQueueResponse(taskQueueID, responseJSON.toJSONString(), "");
        return  taskQueueResponse;
    }

    public TaskQueueResponse getAnswerResponse(ClientApp clientApp, String pybossaResult, JSONParser parser, Long taskQueueID, ClientAppAnswer clientAppAnswer, ReportTemplateService rtpService) throws Exception{
        if(clientAppAnswer == null){
            System.out.println("clientAppAnswer is null ");
            return null;
        }

        JSONObject responseJSON = new JSONObject();

        String[] questions = getQuestion( clientAppAnswer,  parser);

        if(questions == null) {
            System.out.println("active answer key is null. No validation is required");
            return null;
        }

        int[] responses = new int[questions.length];

        JSONArray array = (JSONArray) parser.parse(pybossaResult) ;

        Iterator itr= array.iterator();
        String answer = null;

        int cutoffSize = getCutOffNumber(array.size(), clientApp.getTaskRunsPerTask(), clientAppAnswer)  ;

        while(itr.hasNext()){
            JSONObject featureJsonObj = (JSONObject)itr.next();

            JSONObject info = (JSONObject)featureJsonObj.get("info");

            Long taskID = (Long) featureJsonObj.get("id");

            answer = this.getUserAnswer(featureJsonObj, clientApp);
            //KPS: the answer will say "Not English"

            if(answer!=null && !clientApp.getAppType().equals(StatusCodeType.APP_MAP) ){
                for(int i=0; i < questions.length; i++ ){
                    if(questions[i].trim().equalsIgnoreCase(answer.trim())){
                        responses[i] = responses[i] + 1;
                        handleItemAboveCutOff(taskQueueID,responses[i], answer, info, clientAppAnswer, rtpService, cutoffSize);
                    }

                }
            }
        }

        String taskInfo = "";
        String responseJsonString = "";
        //if(!clientApp.getAppType().equals(StatusCodeType.APP_MAP)){
        for(int i=0; i < questions.length; i++ ){
            responseJSON.put(questions[i], responses[i]);
        }
        responseJsonString = responseJSON.toJSONString();
        //}

        //TODO: look to hook in here for translation
        TaskQueueResponse taskQueueResponse = new TaskQueueResponse(taskQueueID, responseJsonString, taskInfo);
        return  taskQueueResponse;
    }

    public TaskQueueResponse getAnswerResponseForGeo(String pybossaResult, JSONParser parser, Long taskQueueID) throws Exception{
        boolean noLocationFound  = isContainNoLocationInfo( pybossaResult,  parser);

        JSONArray array = (JSONArray) parser.parse(pybossaResult) ;

        Iterator itr= array.iterator();
        JSONArray locations  =  new JSONArray();
        String tweetID = null;

        while(itr.hasNext()){
            JSONObject featureJsonObj = (JSONObject)itr.next();
            if(featureJsonObj.get("info") instanceof String){
                System.out.println("getAnswerResponseForGeo : info is instance of string, not JSONObject " );
            }

            JSONObject info = (JSONObject)featureJsonObj.get("info");
            tweetID = (String) info.get("url");
            String locValue = info.get("loc").toString();
            if(!locValue.equalsIgnoreCase(PybossaConf.TASK_QUEUE_GEO_INFO_NOT_FOUND)){
                if(DataFormatValidator.isValidateJson(locValue)) {
                    JSONObject loc = (JSONObject)info.get("loc");
                    String locType = (String)loc.get("type");
                    if(locType.equalsIgnoreCase(PybossaConf.GEOJSON_TYPE_FEATURE_COLLECTION)){
                        JSONArray features = (JSONArray)loc.get("features");

                        for(int i= 0; i < features.size(); i++  ){
                            locations.add(features.get(i)) ;
                        }

                    }
                    else{
                        locations.add(info.get("loc"))   ;
                    }
                }
            }

        }

        JSONObject geoLocations;
        if(!noLocationFound) {
            geoLocations =  calculateCentralPoint(locations);
        }
        else{
            geoLocations = new JSONObject();
        }

        TaskQueueResponse taskQueueResponse = new TaskQueueResponse(taskQueueID, geoLocations.toJSONString(), tweetID);

        return  taskQueueResponse;
    }

    public TaskQueueResponse getAnswerResponseForAerial(String pybossaResult, JSONParser parser, Long taskQueueID) throws Exception{
        JSONArray array = (JSONArray) parser.parse(pybossaResult) ;

        Iterator itr= array.iterator();
        JSONArray locations  =  new JSONArray();
        String tweetID = null;

        while(itr.hasNext()){
            JSONObject featureJsonObj = (JSONObject)itr.next();

            JSONObject info = (JSONObject)featureJsonObj.get("info");
            String locValue = info.get("loc").toString();
            if(!locValue.equalsIgnoreCase(PybossaConf.TASK_QUEUE_GEO_INFO_NOT_FOUND) && !locValue.isEmpty()){
                JSONObject loc = (JSONObject)info.get("loc");
                locations.add(info.get("loc"))   ;
            }

        }

        TaskQueueResponse taskQueueResponse = new TaskQueueResponse(taskQueueID, locations.toJSONString(), tweetID);

        return  taskQueueResponse;
    }

    private JSONObject calculateCentralPoint(JSONArray locations){
        JSONObject geoResponse = new JSONObject();
        // Min 3 votes are required
        if(locations.size() < PybossaConf.DEFAULT_GEO_N_ANSWERS){
            return geoResponse;
        }

        JSONObject loc = (JSONObject)locations.get(0);
        JSONObject geometry = (JSONObject)loc.get("geometry");
        JSONArray coordinates =  (JSONArray)geometry.get("coordinates");
        System.out.println(coordinates.get(0) + "," + coordinates.get(1));

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonCoordinate = new JSONArray();
        jsonCoordinate.add(coordinates.get(0));
        jsonCoordinate.add(coordinates.get(1));
        jsonObject.put("coordinates", jsonCoordinate);
        jsonObject.put("type", "Point");
        jsonObject.put("distance", PybossaConf.ONE_MILE_DISTANCE );
        geoResponse.put("geometry", jsonObject);
        geoResponse.put("type","Feature");

        return geoResponse;
    }

    private HashMap<Integer, Integer> addToTimeStampList(HashMap<Integer, Integer> timeStampList, String answerValue){

        String[] answerAry = answerValue.split(",");
        for(int i=0; i< answerAry.length; i++){
            if(!answerAry[i].toString().isEmpty()){
                Integer sec = Double.valueOf(answerAry[i]).intValue();
                if(!timeStampList.containsKey(sec)) {
                    if(sec > 0){
                        Integer secAddOne = sec + 1;
                        if(!timeStampList.containsKey(secAddOne)){
                            if(sec > 1){
                                Integer secSubtractOne = sec - 1;
                                if(!timeStampList.containsKey(secSubtractOne)){
                                    timeStampList.put(sec, 1);
                                }
                                else{
                                    Integer value = timeStampList.get(secSubtractOne) ;
                                    timeStampList.put(secSubtractOne, value + 1);
                                }
                            }
                        }
                        else{
                            Integer value = timeStampList.get(secAddOne) ;
                            timeStampList.put(secAddOne, value + 1);
                        }
                    }
                }
                else{
                    Integer value = timeStampList.get(sec) ;
                    timeStampList.put(sec, value + 1);
                }
            }
        }
        return timeStampList;
    }

    private String getUserAnswer(JSONObject featureJsonObj, ClientApp clientApp){

        String answer = null;
        JSONObject info = (JSONObject)featureJsonObj.get("info");
        // tweet
        if(info.get("category")!=null) {
            answer = (String)info.get("category");
        }
        // image , video
        if(info.get("damage")!=null && clientApp.getAppType() == StatusCodeType.APP_IMAGE) {
            answer = (String)info.get("damage");
        }



        // geo
        if(info.get("loc")!=null) {
            answer = (String)info.get("loc");
        }

        return answer;
    }

    private String[] getQuestion(ClientAppAnswer clientAppAnswer, JSONParser parser) throws ParseException {
        String answerKey =  clientAppAnswer.getAnswer();
        if(clientAppAnswer.getActiveAnswerKey() != null){
            answerKey =      clientAppAnswer.getActiveAnswerKey();
        }

        JSONArray questionArrary =   (JSONArray) parser.parse(answerKey) ;

        int questionSize =  questionArrary.size();
        String[] questions = new String[questionSize];

        for(int i=0; i< questionSize; i++){
            JSONObject obj = (JSONObject)questionArrary.get(i);
            questions[i] =   (String)obj.get("qa");
        }

        return questions;
    }

    private void handleItemAboveCutOff(Long taskQueueID,int responseCount, String answer, JSONObject info, ClientAppAnswer clientAppAnswer, ReportTemplateService reportTemplateService, int cutOffSize){
        // MAKE SURE TO MODIFY TEMPLATE HTML  Standize OUTPUT FORMAT
        if(responseCount >= cutOffSize){
            String tweetID = (String)info.get("tweetid");
            String tweet = (String)info.get("tweet");
            String author= (String)info.get("author");
            String lat= (String)info.get("lat");
            String lng= (String)info.get("lon");
            String url= (String)info.get("url");
            String created = (String)info.get("timestamp");
            Long taskID = (Long)info.get("taskid");

            if(taskQueueID!=null && taskID!=null && tweetID!=null && (tweet!=null && !tweet.isEmpty())){
                ReportTemplate template = new ReportTemplate(taskQueueID,taskID,tweetID,tweet,author,lat,lng,url,created, answer, StatusCodeType.TEMPLATE_IS_READY_FOR_EXPORT, clientAppAnswer.getClientAppID());
                reportTemplateService.saveReportItem(template);
            }
            // save to output
        }
    }

    private void handleItemAboveCutOffForVideo(Long taskQueueID, HashMap<Integer, Integer> severeTimeStamp, HashMap<Integer, Integer> mildTimeStamp, JSONObject info, ClientAppAnswer clientAppAnswer, ReportTemplateService reportTemplateService){

        if(info == null){
            return;
        }
        String responseKeyWord = null;
        String tweetID = (String)info.get("tweetid");
        String tweet = (String)info.get("tweet");
        String author= (String)info.get("author");
        String lat= (String)info.get("lat");
        String lng= (String)info.get("lon");
        String url= (String)info.get("url");
        String created = (String)info.get("timestamp");
        Long taskID = (Long)info.get("taskid");

        List<Integer> unsortAnswerList = new ArrayList<Integer>();

        if(!severeTimeStamp.isEmpty() )  {
            for (Map.Entry entry : severeTimeStamp.entrySet()) {
                Integer value = (Integer)entry.getKey();
                if (!unsortAnswerList.contains(value))
                    unsortAnswerList.add(value);
                responseKeyWord = PybossaConf.VIDEO_CLICKER_RESPONSE_SEVERE;
            }
        }

        if(!mildTimeStamp.isEmpty() )  {
            for (Map.Entry entry : mildTimeStamp.entrySet()) {
                Integer value = (Integer)entry.getKey();
                if (!unsortAnswerList.contains(value))
                    unsortAnswerList.add(value);
                responseKeyWord = responseKeyWord + ":" +PybossaConf.VIDEO_CLICKER_RESPONSE_MILD;
            }
        }

        if(unsortAnswerList.size() > 1){
            Collections.sort(unsortAnswerList);
            String timeStampResponseValue = StringUtils.collectionToDelimitedString(unsortAnswerList,"-");
            timeStampResponseValue = timeStampResponseValue + "-" + responseKeyWord ;
            ReportTemplate template = new ReportTemplate(taskQueueID,taskID,tweetID,tweet,author,lat,lng,url,created, timeStampResponseValue , StatusCodeType.TEMPLATE_IS_READY_FOR_EXPORT, clientAppAnswer.getClientAppID());
            reportTemplateService.saveReportItem(template);
        }
    }

    private HashMap<Integer, Integer> removeItemBelowCutOffForVideo(HashMap<Integer, Integer> timeStampList, ClientAppAnswer clientAppAnswer ){

        for(Iterator<Map.Entry<Integer,Integer>> it=timeStampList.entrySet().iterator();it.hasNext();){
            Map.Entry<Integer, Integer> entry = it.next();
            if (entry.getValue() < clientAppAnswer.getVoteCutOff()) {
                it.remove();
            }
        }

        return timeStampList;
    }

    private boolean isContainNoLocationInfo(String pybossaResult, JSONParser parser) throws ParseException {
        boolean found = false;
        JSONArray array = (JSONArray) parser.parse(pybossaResult) ;

        Iterator itr= array.iterator();

        while(itr.hasNext()){
            JSONObject featureJsonObj = (JSONObject)itr.next();

            JSONObject info = (JSONObject)featureJsonObj.get("info");
            String locValue = info.get("loc").toString();
            if(locValue.equalsIgnoreCase(PybossaConf.TASK_QUEUE_GEO_INFO_NOT_FOUND)){
                found = true;
            }

        }
        return  found;
    }

    private int getCutOffNumber(int responseSize, int maxResponseSize, ClientAppAnswer clientAppAnswer){

        int cutOffSize =    clientAppAnswer.getVoteCutOff();

        if(responseSize > maxResponseSize){
            cutOffSize = (int)Math.round(maxResponseSize * 0.5);
        }

        return cutOffSize;
    }
}
