package qa.qcri.mm.trainer.pybossa.service.impl;

import au.com.bytecode.opencsv.CSVWriter;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qa.qcri.mm.trainer.pybossa.custom.MAPBoxAerialClickerFileFormat;
import qa.qcri.mm.trainer.pybossa.dao.CrisisDao;
import qa.qcri.mm.trainer.pybossa.dao.ImageMetaDataDao;
import qa.qcri.mm.trainer.pybossa.dao.TaskQueueResponseDao;
import qa.qcri.mm.trainer.pybossa.entity.*;
import qa.qcri.mm.trainer.pybossa.format.impl.CVSRemoteFileFormatter;
import qa.qcri.mm.trainer.pybossa.format.impl.GeoJsonOutputModel;
import qa.qcri.mm.trainer.pybossa.format.impl.MicromapperInput;
import qa.qcri.mm.trainer.pybossa.service.*;
import qa.qcri.mm.trainer.pybossa.store.PybossaConf;
import qa.qcri.mm.trainer.pybossa.store.StatusCodeType;
import qa.qcri.mm.trainer.pybossa.store.URLPrefixCode;
import qa.qcri.mm.trainer.pybossa.store.UserAccount;
import qa.qcri.mm.trainer.pybossa.util.DateTimeConverter;

import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 11/22/13
 * Time: 2:29 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("reportProductService")
@Transactional(readOnly = false)
public class ReportProductServiceImpl implements ReportProductService {

    protected static Logger logger = Logger.getLogger("reportProductService");

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientAppService clientAppService;

    @Autowired
    private ReportTemplateService reportTemplateService;

    @Autowired
    private ClientAppSourceService clientAppSourceService;

    @Autowired
    private ClientAppEventService clientAppEventService;

    @Autowired
    private ImageMetaDataDao imageMetaDataDao;

    @Autowired
    private TaskQueueResponseDao taskQueueResponseDao;

    @Autowired
    private TaskQueueService taskQueueService;

    private CVSRemoteFileFormatter cvsRemoteFileFormatter;

    private Client client;

    private JSONParser parser = new JSONParser();

    public void setClassVariable() throws Exception{
        client = clientService.findClientByCriteria("name", UserAccount.MIROMAPPER_USER_NAME);
    }

    @Override
    public void generateReportTemplateFromExternalSource() throws Exception {
        List<ClientAppSource> datasources = clientAppSourceService.getClientAppSourceWithStatusCode(StatusCodeType.EXTERNAL_DATA_SOURCE_TO_GEO_READY_REPORT);

        for(int j=0; j < datasources.size(); j++){

            String url = datasources.get(j).getSourceURL();
            Long clientAppID = datasources.get(j).getClientAppID();

            if(!cvsRemoteFileFormatter.doesSourcerExist(url)){
                return;
            }

            List<MicromapperInput> micromapperInputList = cvsRemoteFileFormatter.getInputDataForReportTemplate(url);
            this.generateReportTemplate(micromapperInputList,clientAppID );
        }

    }

    @Override
    public void generateCVSReportForGeoClicker() throws Exception{
        setClassVariable();

        if(client == null){
            return;
        }

        List<ClientApp> appList = clientAppService.getAllClientAppByClientID(client.getClientID() );

        Iterator itr= appList.iterator();
        while(itr.hasNext()){
            ClientApp clientApp = (ClientApp)itr.next();
            ClientAppEvent targetClinetApp = clientAppEventService.getNextSequenceClientAppEvent(clientApp.getClientAppID());

            if(targetClinetApp != null ){
                List<ReportTemplate> templateList =  reportTemplateService.getReportTemplateByClientApp(clientApp.getClientAppID(), StatusCodeType.TEMPLATE_IS_READY_FOR_EXPORT);

                if(templateList.size() > StatusCodeType.MAX_CSV_ROW_QUEUE_SIZE ){

                    CVSRemoteFileFormatter formatter = new CVSRemoteFileFormatter();

                    String fileName = PybossaConf.DEFAULT_TRAINER_FILE_PATH + DateTimeConverter.reformattedCurrentDateForFileName() + clientApp.getShortName() + "export.csv";
                    CSVWriter writer = formatter.instanceToOutput(fileName);

                    for(int i=0; i < templateList.size(); i++){
                        ReportTemplate rpt = templateList.get(i);
                        String ans = rpt.getAnswer();

                        if(!ans.equalsIgnoreCase("none"))  {
                            formatter.addToCVSOuputFile(generateOutputData(rpt),writer);
                            rpt.setStatus(StatusCodeType.TEMPLATE_EXPORTED);
                            reportTemplateService.updateReportItem(rpt);
                        }

                    }
                    formatter.finalizeCVSOutputFile(writer);

                    ClientAppSource appSource = new ClientAppSource(targetClinetApp.getClientAppID(), StatusCodeType.EXTERNAL_DATA_SOURCE_ACTIVE, fileName);
                    clientAppSourceService.insertNewClientAppSource(appSource);
                }
            }
        }
    }

    @Override
    public void generateGeoJsonForESRI(List<GeoJsonOutputModel> geoJsonOutputModels) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
        JSONParser parser = new JSONParser();

        JSONArray jsonArray ;
        FileWriter fw ;
        BufferedWriter bw;

        if(geoJsonOutputModels.size() > 0){
            CVSRemoteFileFormatter formatter = new CVSRemoteFileFormatter();

            String fileName = PybossaConf.DEFAULT_TRAINER_GEO_FILE_PATH + DateTimeConverter.reformattedCurrentDateForFileName()+"export.geojson";
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
                jsonArray = new JSONArray();
            }
            else{
                fw = new FileWriter(file.getAbsoluteFile());
                bw = new BufferedWriter(fw);
                bw.write("");
                bw.close();

                Object obj = parser.parse(new FileReader(fileName));
                jsonArray = (JSONArray) obj;
            }


            for(GeoJsonOutputModel item : geoJsonOutputModels) {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("info", (JSONObject)parser.parse(item.getGeoJsonInfo()));

                jsonObject.put("features", (JSONObject)parser.parse(item.getGeoJson()));
                jsonArray.add(jsonObject);
            }

            fw = new FileWriter(file.getAbsoluteFile());
            bw = new BufferedWriter(fw);
            bw.write(jsonArray.toJSONString());
            bw.close();
        }

    }

    @Override
    public void generateMapBoxTemplateForAerialClicker() throws Exception {

        List<ImageMetaData> imageMetaDataList = imageMetaDataDao.getMapBoxDataTile(StatusCodeType.MAPBOX_TILE_IMPORTED, 1000);
        String fileJsonName = PybossaConf.DEFAULT_TRAINER_FILE_PATH + DateTimeConverter.reformattedCurrentDateForFileName() + "MapBoxExport.json";

        if(imageMetaDataList.size() > 0){
            JSONArray jsonArray = MAPBoxAerialClickerFileFormat.createAerialClickerData(imageMetaDataList);
            try {

                //String fileJsonName ="/Users/jlucas/Documents/ConservationDrones/TEST/export2.json";

                if(jsonArray.size() == 0){
                    return;
                }
                File fileJson = new File(fileJsonName);


                FileWriter geoWriter = new FileWriter(fileJson);

                geoWriter.write(jsonArray.toJSONString());
                geoWriter.flush();
                geoWriter.close();


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(imageMetaDataList.size() > 0){
                    for(ImageMetaData i : imageMetaDataList) {
                        imageMetaDataDao.updateExportedData(i);
                    }
                   // hard-coded id. bad code!!! willl be updated.
                    ClientAppSource appSource = new ClientAppSource(Long.valueOf(257), StatusCodeType.MAPBOX_TILE_EXPORTED, fileJsonName);
                    clientAppSourceService.insertNewClientAppSource(appSource);
                }

            }
        }
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void generateGeoJsonForClientApp(Long clientAppID) throws Exception {

        ClientApp clientApp = clientAppService.findClientAppByID("clientAppID", clientAppID);

        if(clientApp.getAppType().equals(StatusCodeType.APP_MAP)){

            JSONObject geoClickerOutput = new JSONObject();
            JSONArray features = new JSONArray();

            System.out.println("crisis :" + clientApp.getName());

            List<TaskQueue> taskQueueList = taskQueueService.getTaskQueueByClientAppStatus(clientAppID, StatusCodeType.TASK_LIFECYCLE_COMPLETED);

            System.out.println("taskQueueList :" + taskQueueList.size());

            for(TaskQueue t: taskQueueList){

                List<TaskQueueResponse> responses = taskQueueResponseDao.getTaskQueueResponse(t.getTaskQueueID());

                if(responses.size() > 0 ){
                    if(!responses.get(0).getResponse().equalsIgnoreCase("{}") && !responses.get(0).getResponse().equalsIgnoreCase("[]")){
                        JSONArray eachFeatureArrary = (JSONArray)parser.parse(responses.get(0).getResponse());
                        for(Object a : eachFeatureArrary){
                            features.add((JSONObject) a);
                        }

                    }
                }

            }

            String fileName = PybossaConf.GEOJSON_HOME + clientAppID + ".json";
            File f = new File(fileName);

            if(f.exists()){
                f.delete();
            }

            geoClickerOutput.put("type", "FeatureCollection");
            geoClickerOutput.put("features", features);

            PrintWriter writer = new PrintWriter(f, "UTF-8");
            writer.println(geoClickerOutput.toJSONString());
            writer.close();
        }

    }


    private String[] generateOutputData(ReportTemplate rpt){

        String[] data = new String[8];
        data[0] =   rpt.getTweetID().toString();
        data[1] =  rpt.getTweet();
        data[2] = rpt.getAuthor();
        data[3] = rpt.getLat();
        data[4] = rpt.getLon();
        data[5] = rpt.getUrl();
        data[6] = rpt.getCreated();
        data[7] = rpt.getAnswer();

        return data;
    }

    private void generateReportTemplate(List<MicromapperInput> micromapperInputList, Long clientAppID){
        long taskID = 0;
        long taskQueueID = 0;
        for(MicromapperInput ins : micromapperInputList){
            ReportTemplate template = new ReportTemplate( taskQueueID,taskID,ins.getTweetID(), ins.getTweet(),ins.getAuthor(), ins.getLat(), ins.getLng(), ins.getUrl(),ins.getCreated(),
                    ins.getAnswer(), StatusCodeType.TEMPLATE_IS_READY_FOR_EXPORT, clientAppID);

            reportTemplateService.saveReportItem(template);

        }

    }
}
