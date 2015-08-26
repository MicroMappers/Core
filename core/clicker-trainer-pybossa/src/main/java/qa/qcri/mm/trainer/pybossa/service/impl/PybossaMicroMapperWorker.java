package qa.qcri.mm.trainer.pybossa.service.impl;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qa.qcri.mm.trainer.pybossa.dao.CrisisDao;
import qa.qcri.mm.trainer.pybossa.dao.MarkerStyleDao;
import qa.qcri.mm.trainer.pybossa.entity.*;
import qa.qcri.mm.trainer.pybossa.format.impl.CVSRemoteFileFormatter;
import qa.qcri.mm.trainer.pybossa.format.impl.GeoJsonOutputModel;
import qa.qcri.mm.trainer.pybossa.format.impl.MicroMapperPybossaFormatter;
import qa.qcri.mm.trainer.pybossa.format.impl.MicromapperInput;
import qa.qcri.mm.trainer.pybossa.service.*;
import qa.qcri.mm.trainer.pybossa.store.StatusCodeType;
import qa.qcri.mm.trainer.pybossa.store.URLPrefixCode;
import qa.qcri.mm.trainer.pybossa.store.UserAccount;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 10/18/13
 * Time: 1:19 AM
 * To change this template use File | Settings | File Templates.
 */
@Service("pybossaMicroMapperWorker")
@Transactional(readOnly = false)
public class PybossaMicroMapperWorker implements MicroMapperWorker {

    protected static Logger logger = Logger.getLogger(PybossaMicroMapperWorker.class);

    private Client client;
    private CVSRemoteFileFormatter cvsRemoteFileFormatter = new CVSRemoteFileFormatter();
    private int MAX_PENDING_QUEUE_SIZE = 50;
    private int MAX_IMPORT_PROCESS_QUEUE_SIZE = 300;
    private String PYBOSSA_API_TASK_PUBLSIH_URL;

    private String PYBOSSA_API_TASK_RUN_BASE_URL;
    private String PYBOSSA_API_TASK_BASE_URL;

    private PybossaCommunicator pybossaCommunicator = new PybossaCommunicator();
    private JSONParser parser = new JSONParser();
    private MicroMapperPybossaFormatter pybossaFormatter = new MicroMapperPybossaFormatter();


    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientAppService clientAppService;

    @Autowired
    private TaskQueueService taskQueueService;

    @Autowired
    private ClientAppSourceService clientAppSourceService;

    @Autowired
    private ClientAppResponseService clientAppResponseService;

    @Autowired
    private ReportTemplateService reportTemplateService;

    @Autowired
    private ReportProductService reportProductService;

    @Autowired
    private ExternalCustomService externalCustomService;


    @Autowired
    private MarkerStyleDao markerStyleDao;

    @Autowired
    private CrisisDao crisisDao;


    public void setClassVariable() throws Exception{
        client = clientService.findClientByCriteria("name", UserAccount.MIROMAPPER_USER_NAME);
        if(client != null){
            PYBOSSA_API_TASK_PUBLSIH_URL = client.getHostURL() + URLPrefixCode.TASK_PUBLISH + client.getHostAPIKey();
            PYBOSSA_API_TASK_BASE_URL  = client.getHostURL() + URLPrefixCode.TASK_INFO;
            PYBOSSA_API_TASK_RUN_BASE_URL =  client.getHostURL() + URLPrefixCode.TASKRUN_INFO;
            MAX_PENDING_QUEUE_SIZE = client.getQueueSize();

        }

    }

    public void setClassVariableByUserName(String userName) throws Exception{
        client = clientService.findClientByCriteria("name", userName);

        if(client != null){
            PYBOSSA_API_TASK_PUBLSIH_URL = client.getHostURL() + URLPrefixCode.TASK_PUBLISH + client.getHostAPIKey();
            PYBOSSA_API_TASK_BASE_URL  = client.getHostURL() + URLPrefixCode.TASK_INFO;
            PYBOSSA_API_TASK_RUN_BASE_URL =  client.getHostURL() + URLPrefixCode.TASKRUN_INFO;
            MAX_PENDING_QUEUE_SIZE = client.getQueueSize();
        }

    }

    @Override
    public void processTaskPublish() throws Exception{
        System.out.println("processTaskImport is starting");
        setClassVariable();

        if(client == null){
            return;
        }

        List<ClientApp> appList = clientAppService.getAllClientAppByClientIDAndStatus(client.getClientID(), StatusCodeType.MICROMAPPER_ONLY );

        if(appList.size() > 0){
            for(int i=0; i < appList.size(); i++){
                ClientApp currentClientApp =  appList.get(i);

                List<ClientAppSource> datasources = clientAppSourceService.getClientAppSourceByStatus(currentClientApp.getClientAppID(),StatusCodeType.EXTERNAL_DATA_SOURCE_ACTIVE);
                //System.out.println(   "clientApp processTaskPublish datasources : " +  datasources.size());
                for(int j=0; j < datasources.size(); j++){

                    List<MicromapperInput> micromapperInputList = null;
                    String url = datasources.get(j).getSourceURL();

                    if(!cvsRemoteFileFormatter.doesSourcerExist(url)){
                        return;
                    }

                    if(currentClientApp.getAppType() == StatusCodeType.APP_MAP){
                        micromapperInputList = cvsRemoteFileFormatter.getGeoClickerInputData(url);
                    }
                    else{
                        if(currentClientApp.getAppType() == StatusCodeType.APP_AERIAL){
                            micromapperInputList = cvsRemoteFileFormatter.getAerialClickerInputData(url);
                        }
                        else if(currentClientApp.getAppType() == StatusCodeType.APP_3W){
                            micromapperInputList = cvsRemoteFileFormatter.get3WClickerInputData(url);
                        }
                        else{
                            micromapperInputList = cvsRemoteFileFormatter.getClickerInputData(url);
                        }
                    }

                    if(micromapperInputList != null){
                        ClientAppSource source = datasources.get(j);

                        if(micromapperInputList.size() > 0) {
                            this.publishToPybossa(currentClientApp, micromapperInputList , source.getClientAppSourceID());
                        }

                        clientAppSourceService.updateClientAppSourceStatus(source.getClientAppSourceID(), StatusCodeType.EXTERNAL_DATA_SOURCE_USED);

                    }
                }

                this.searchUpdateNextAvailableAppSource(currentClientApp.getClientAppID());

            }
        }

    }

    @Override
    public void processTaskImport() throws Exception{
        System.out.println("Data import is starting");
        setClassVariable();

        if(client == null){
            return;
        }

        List<ClientApp> appList = clientAppService.getAllClientAppByClientID(client.getClientID() );
        List<GeoJsonOutputModel> geoJsonOutputModels =  new ArrayList<GeoJsonOutputModel>();
        Iterator itr= appList.iterator();
        while(itr.hasNext()){
            ClientApp clientApp = (ClientApp)itr.next();

            if(clientApp.getStatus().equals(StatusCodeType.MICROMAPPER_ONLY)){
                List<TaskQueue> taskQueues =  taskQueueService.getTaskQueueByClientAppStatus(clientApp.getClientAppID(),StatusCodeType.TASK_PUBLISHED);
                if(taskQueues != null ){

                    int queueSize =   MAX_IMPORT_PROCESS_QUEUE_SIZE;
                    if(taskQueues.size() < MAX_IMPORT_PROCESS_QUEUE_SIZE)
                    {
                        queueSize =  taskQueues.size();
                    }

                    for(int i=0; i < queueSize; i++){
                        TaskQueue taskQueue = taskQueues.get(i);
                        Long taskID =  taskQueue.getTaskID();
                        String taskQueryURL = PYBOSSA_API_TASK_BASE_URL + clientApp.getPlatformAppID() + "&id=" + taskID;
                        String inputData = pybossaCommunicator.sendGet(taskQueryURL);
                        try {

                            boolean isFound = pybossaFormatter.isTaskStatusCompleted(inputData);

                            if(isFound){
                                this.processTaskQueueImport(clientApp,taskQueue,taskID, geoJsonOutputModels) ;
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            logger.error("processTaskImport: " + e);
                            System.out.println("getTaskQueueID*******************" + taskQueue.getTaskQueueID());
                            //To change body of catch statement use File | Settings | File Templates.
                        }
                    }
                    // Map data export
                    reportProductService.generateGeoJsonForClientApp(clientApp.getClientAppID());

                }
            }


        }
    }

    @Override
    public void processTaskImportOnDemand(String shortName) throws Exception{
        setClassVariable();
        if(client == null){
            return;
        }

        List<GeoJsonOutputModel> geoJsonOutputModels =  new ArrayList<GeoJsonOutputModel>();

            ClientApp clientApp = clientAppService.findClientAppByCriteria("shortName", shortName);

            List<TaskQueue> taskQueues =  taskQueueService.getTaskQueueByClientAppStatus(clientApp.getClientAppID(),StatusCodeType.TASK_PUBLISHED);

            if(taskQueues != null ){
                int queueSize = 1;
                int MAX_TO_PROCESS = MAX_IMPORT_PROCESS_QUEUE_SIZE;
                queueSize  = MAX_TO_PROCESS;
                if(taskQueues.size() < MAX_TO_PROCESS)
                {
                    queueSize =  taskQueues.size();
                }

                for(int i=0; i < queueSize; i++){
                    TaskQueue taskQueue = taskQueues.get(i);
                    Long taskID =  taskQueue.getTaskID();
                    System.out.println("taskID :" + taskID);
                    //String taskQueryURL = PYBOSSA_API_TASK_BASE_URL + clientApp.getPlatformAppID() + "&id=" + taskID;
                    //String inputData = pybossaCommunicator.sendGet(taskQueryURL);
                    try {

                      //  boolean isFound = pybossaFormatter.isTaskStatusCompleted(inputData);
                        boolean isFound = true;
                        if(isFound){
                            processTaskQueueImport(clientApp,taskQueue,taskID, geoJsonOutputModels) ;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            }


    }

    @Override
    public void processTaskExport() throws Exception {
        reportProductService.generateCVSReportForGeoClicker();
        reportProductService.generateMapBoxTemplateForAerialClicker();

    }

    private void processTaskQueueImport(ClientApp clientApp,TaskQueue taskQueue, Long taskID, List<GeoJsonOutputModel> geoJsonOutputModels) throws Exception {
        String PYBOSSA_API_TASK_RUN = PYBOSSA_API_TASK_RUN_BASE_URL + clientApp.getPlatformAppID() + "&task_id=" + taskID;

        String importResult = pybossaCommunicator.sendGet(PYBOSSA_API_TASK_RUN) ;

        if(!importResult.isEmpty() && importResult.length() > StatusCodeType.RESPONSE_MIN_LENGTH  ){

            TaskQueueResponse taskQueueResponse = null;
            ClientAppAnswer clientAppAnswer = clientAppResponseService.getClientAppAnswer(clientApp.getClientAppID());

            if(clientApp.getAppType().equals(StatusCodeType.APP_VIDEO)){
                taskQueueResponse = pybossaFormatter.getAnswerResponseForVideo(clientApp, importResult, parser, taskQueue.getTaskQueueID(), clientAppAnswer,reportTemplateService);

            }
            else if(clientApp.getAppType().equals(StatusCodeType.APP_MAP))
            {
                Crisis c = this.getCrisisDetail(clientApp);
                MarkerStyle style = this.getMarkerStyleForClientApp(clientApp, c);
                ClientAppSource clientAppSource = clientAppSourceService.getClientAppSourceByClientAppID(taskQueue.getClientAppSourceID());
                taskQueueResponse = pybossaFormatter.getAnswerResponseForGeo(importResult, parser, taskQueue.getTaskQueueID(), clientApp, c, style, clientAppSource);
            }
            else if(clientApp.getAppType().equals(StatusCodeType.APP_AERIAL))
            {

                if(clientApp.getIsCustom()){
                    taskQueueResponse = externalCustomService.getAnswerResponse(clientApp,importResult,taskQueue);

                }
                else{
                    // expected standard
                    taskQueueResponse = pybossaFormatter.getAnswerResponseForAerial(importResult, parser, taskQueue.getTaskQueueID(), clientApp);
                }

                if(taskQueueResponse != null){
                    clientAppResponseService.processTaskQueueResponse(taskQueueResponse);
                }

            }
            else{
                taskQueueResponse = pybossaFormatter.getAnswerResponse(clientApp, importResult, parser, taskQueue.getTaskQueueID(), clientAppAnswer, reportTemplateService);
            }

            if(taskQueueResponse != null){

                clientAppResponseService.processTaskQueueResponse(taskQueueResponse);
                taskQueue.setStatus(StatusCodeType.TASK_LIFECYCLE_COMPLETED);
                updateTaskQueue(taskQueue);
            }
            else{
                System.out.println("taskQueueResponse is null : No action is required or review configuration");
            }
        }
    }

    private void publishToPybossa(ClientApp currentClientApp, List<MicromapperInput> micromapperInputList, Long clientAppSourceID){
        try {
            List<String> aidr_data = pybossaFormatter.assemblePybossaTaskPublishForm(micromapperInputList, currentClientApp);

            for (String temp : aidr_data) {

                String response = pybossaCommunicator.sendPostGet(temp, PYBOSSA_API_TASK_PUBLSIH_URL) ;

                if(!response.startsWith(StatusCodeType.EXCEPTION_STRING)){

                    addToTaskQueue(response, currentClientApp.getClientAppID(), StatusCodeType.TASK_PUBLISHED, clientAppSourceID) ;
                }
                else{
                    addToTaskQueue(temp, currentClientApp.getClientAppID(), StatusCodeType.Task_NOT_PUBLISHED, clientAppSourceID) ;
                }
            }
            // data is consumed. need to mark as completed not to process anymore.
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void addToTaskQueue(String inputData, Long clientAppID, Integer status, Long clientAppSourceID){
        try {
            Object obj = parser.parse(inputData);
            JSONObject jsonObject = (JSONObject) obj;

            System.out.println("addToTaskQueue : " + inputData);

            Long taskID  = (Long)jsonObject.get("id");
            JSONObject info = (JSONObject)jsonObject.get("info");
            Long documentID = (Long)info.get("documentID");

            TaskQueue taskQueue = new TaskQueue(taskID, clientAppID, documentID, status);
            // mostly micromapper will have outside source. AIDR will have docID
            taskQueue.setClientAppSourceID(clientAppSourceID);

            taskQueueService.createTaskQueue(taskQueue);


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void updateTaskQueue(TaskQueue taskQueue){
        taskQueueService.updateTaskQueue(taskQueue);

    }

    private void searchUpdateNextAvailableAppSource(Long clientAppID){
        List<ClientAppSource> sourceList =  clientAppSourceService.getClientAppSourceByStatus(clientAppID, StatusCodeType.EXTERNAL_DATA_SOURCE_UPLOADED);

        System.out.println("searchUpdateNextAvailableAppSource : " +sourceList.size());

        if(sourceList.size() > 0){
            System.out.println("searchUpdateNextAvailableAppSource2 : " + sourceList.get(0).getClientAppSourceID());
            clientAppSourceService.updateClientAppSourceStatus(sourceList.get(0).getClientAppSourceID(), StatusCodeType.EXTERNAL_DATA_SOURCE_ACTIVE);
        }
    }


    private MarkerStyle getMarkerStyleForClientApp(ClientApp clientApp, Crisis c){

        MarkerStyle selectedStyle = null;
        try {
            List<MarkerStyle> styleTemplate = markerStyleDao.findByClientAppID(clientApp.getClientAppID().longValue()) ;
            if(styleTemplate.isEmpty()){
                styleTemplate = markerStyleDao.findByAppType(c.getClickerType());
            }

            if(styleTemplate.size() > 0){
                return  styleTemplate.get(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return selectedStyle;
    }

    private Crisis getCrisisDetail(ClientApp clientApp){
        List<Crisis> cList = crisisDao.getClientAppCrisisDetail(clientApp.getClientAppID());

        if(cList.size() > 0) {
            return cList.get(0);
        }
        return null;
    }
}
