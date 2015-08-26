package qa.qcri.mm.trainer.pybossa.service.impl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qa.qcri.mm.trainer.pybossa.custom.NamibiaAerialClickerFileFormat;
import qa.qcri.mm.trainer.pybossa.dao.*;
import qa.qcri.mm.trainer.pybossa.entity.*;
import qa.qcri.mm.trainer.pybossa.format.impl.SkyEyeDataOutputProcessor;
import qa.qcri.mm.trainer.pybossa.format.impl.VanuatuDataOutputProcessor;
import qa.qcri.mm.trainer.pybossa.service.ClientAppService;
import qa.qcri.mm.trainer.pybossa.service.ExternalCustomService;
import qa.qcri.mm.trainer.pybossa.service.NamibiaReportService;
import qa.qcri.mm.trainer.pybossa.store.PybossaConf;
import qa.qcri.mm.trainer.pybossa.store.UserAccount;

import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 9/9/14
 * Time: 4:29 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("externalCustomService")
@Transactional(readOnly = true)
public class ExternalCustomServiceImpl  implements ExternalCustomService {

    @Autowired
    NamibiaImageDao namibiaImageDao;

    @Autowired
    TaskRunDao taskRunDao;

    @Autowired
    NamibiaReportService namibiaReport;

    @Autowired
    ClientAppService clientAppService;

    @Autowired
    ImageMetaDataDao imageMetaDataDao;

    @Autowired
    private MarkerStyleDao markerStyleDao;


    private SkyEyeDataOutputProcessor skyEyeDataOutputProcessor = null;
    private VanuatuDataOutputProcessor vanuatuDataOutputProcessor = null;

    JSONParser cParser = new JSONParser();

    @Override
    public String NamibiaImageWithTag(int tagValue) {

        List<NamibiaImage> namibiaImageList =  namibiaImageDao.getClientAppSourceByTag(tagValue);
        JSONArray list = NamibiaAerialClickerFileFormat.createAerialClickerData(namibiaImageList);
        System.out.println(list.toJSONString());

        return list.toJSONString();
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TaskQueueResponse getAnswerResponseForSkyEyes(ClientApp clientApp, String datasource, TaskQueue taskQueue) throws Exception {
        if(skyEyeDataOutputProcessor == null || skyEyeDataOutputProcessor.getClientApp().equals(clientApp))  {
            skyEyeDataOutputProcessor = new SkyEyeDataOutputProcessor(clientApp);
            skyEyeDataOutputProcessor.setMarkerStyleDao(markerStyleDao);
        }

        return skyEyeDataOutputProcessor.process(datasource, taskQueue);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String NamibiaImage() {
        String folderName = "/static/namibia/day5_rgb_zebra/slice";
        List<NamibiaImage> namibiaImageList =  namibiaImageDao.getClientAppSourceByFolderName(folderName);
        JSONArray list = NamibiaAerialClickerFileFormat.createAerialClickerData(namibiaImageList);
        System.out.println(list.toJSONString());

        return list.toJSONString();
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TaskQueueResponse getAnswerResponseForAerial(String pybossaResult, JSONParser parser, Long taskQueueID, Long taskID) throws Exception{

        TaskQueueResponse taskQueueResponse = null;

        try{
            Integer foundCount = 0;
            Integer noFoundCount = 0;

            JSONArray array = (JSONArray) parser.parse(pybossaResult) ;

            if(array.size() > 0) {
                JSONObject response = (JSONObject)array.get(0);
                String infoString = (String)response.get("info");
                JSONObject answer = (JSONObject)parser.parse(infoString);
                String slicedImage = (String)answer.get("imgurl");
                String sTemp = slicedImage.substring(slicedImage.lastIndexOf("IMG_"), slicedImage.length());

                int pos = sTemp.toLowerCase().indexOf(".jpg");


                sTemp = sTemp.substring(0, pos-2) + sTemp.substring(pos, sTemp.length());

                String sourceImage = sTemp;

                Iterator itr= array.iterator();

                JSONArray locations  =  new JSONArray();

                String tweetID = null;

                while(itr.hasNext()){
                    JSONObject featureJsonObj = (JSONObject)itr.next();

                    String featureJsonObjString = (String)featureJsonObj.get("info");

                    JSONObject info = (JSONObject)parser.parse(featureJsonObjString);
                    //JSONObject info = (JSONObject)featureJsonObj.get("info");

                    String locValue = info.get("loc").toString();
                    if(!locValue.equalsIgnoreCase(PybossaConf.TASK_QUEUE_GEO_INFO_NOT_FOUND) && !locValue.isEmpty()){
                        JSONArray loc = (JSONArray)info.get("loc");
                        if(!loc.isEmpty() && loc.size() > 0){
                            foundCount = foundCount + 1;
                            locations.add(info.get("loc"))   ;
                        }
                    }

                }

                noFoundCount =   array.size() - foundCount;

                JSONObject finalAnswer = new JSONObject();
                finalAnswer.put("geo", locations);
                finalAnswer.put("sourceImage", sourceImage);
                finalAnswer.put("slicedImage", slicedImage);
                finalAnswer.put("foundCount", foundCount);
                finalAnswer.put("noFoundCount", noFoundCount);

                taskQueueResponse = new TaskQueueResponse(taskQueueID, finalAnswer.toJSONString(), tweetID);
                NamibiaReport rpt = new NamibiaReport(taskID, locations.toJSONString(), sourceImage, slicedImage, foundCount, noFoundCount);
                namibiaReport.createAreport(rpt);
            }


        }
        catch(Exception e){
            System.out.println("pybossaResult : " + pybossaResult) ;
            System.out.println("Exception e : " + e) ;

        }


        return  taskQueueResponse;

    }


    private TaskQueueResponse getAnswerResponseForPAM(ClientApp clientApp, String datasource, TaskQueue taskQueue) throws Exception {
        if(vanuatuDataOutputProcessor == null || vanuatuDataOutputProcessor.getClientApp().equals(clientApp))  {
            vanuatuDataOutputProcessor = new VanuatuDataOutputProcessor(clientApp);
            vanuatuDataOutputProcessor.setImageMetaDataDao(imageMetaDataDao);
            vanuatuDataOutputProcessor.setMarkerStyleDao(markerStyleDao);
        }

        TaskQueueResponse response = vanuatuDataOutputProcessor.process(datasource, taskQueue);

        return response;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TaskQueueResponse getAnswerResponse(ClientApp clientApp, String datasource, TaskQueue taskQueue) throws Exception {

        System.out.println("name : " + clientApp.getShortName());
        if(clientApp.getShortName().equalsIgnoreCase(UserAccount.PAM_APP)) {
            // PAM
            System.out.println("pam");
           return getAnswerResponseForPAM(clientApp,  datasource,  taskQueue);
        }

        if(clientApp.getShortName().equalsIgnoreCase(UserAccount.SKY_EYES_COCONUT_APP) || clientApp.getShortName().equalsIgnoreCase(UserAccount.SKY_EYES_RUBY_APP)) {
            //
            System.out.println("skyeyes");
            return getAnswerResponseForSkyEyes(clientApp,  datasource,  taskQueue);
        }

        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TaskQueueResponse testAerialClick(String pybossaResult) throws Exception{

        //   SkyEyeDataOutputProcessor p = new SkyEyeDataOutputProcessor(null);

        //  p.process(pybossaResult, null);

        return null;

    }

}
