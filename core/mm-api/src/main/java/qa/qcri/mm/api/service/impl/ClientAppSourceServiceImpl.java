package qa.qcri.mm.api.service.impl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qa.qcri.mm.api.dao.ClientAppSourceDao;
import qa.qcri.mm.api.dao.ImageMetaDataDao;
import qa.qcri.mm.api.entity.ClientApp;
import qa.qcri.mm.api.entity.ClientAppSource;
import qa.qcri.mm.api.entity.ImageMetaData;
import qa.qcri.mm.api.service.ClientAppService;
import qa.qcri.mm.api.service.ClientAppSourceService;
import qa.qcri.mm.api.store.CodeLookUp;
import qa.qcri.mm.api.store.StatusCodeType;
import qa.qcri.mm.api.util.Communicator;
import qa.qcri.mm.api.util.DataFormatValidator;
import qa.qcri.mm.api.util.GISUtil;
import qa.qcri.mm.api.util.GeoLocation;

import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 6/11/14
 * Time: 9:50 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("clientAppSourceService")
@Transactional(readOnly = false)
public class ClientAppSourceServiceImpl implements ClientAppSourceService {
    @Autowired
    ClientAppSourceDao clientAppSourceDao;

    @Autowired
    ClientAppService clientAppService;

    @Autowired
    ImageMetaDataDao imageMetaDataDao;

    @Override
    @Transactional(readOnly = false)
    public void addExternalDataSouceWithClientAppID(String fileURL, Long clientAppID) {
        System.out.println("fileURL : " + fileURL );
        System.out.println("clientAppID : " + clientAppID );

        boolean dublicateFound = clientAppSourceDao.findDuplicateSource(fileURL, clientAppID);

        if(!dublicateFound){
            List<ClientAppSource>  sources = clientAppSourceDao.findActiveSourcePerClient( clientAppID );

            if(sources.size() > 0){
                System.out.println("sources : EXTERNAL_DATA_SOURCE_UPLOADED");
                ClientAppSource ca1 = new ClientAppSource(clientAppID, StatusCodeType.EXTERNAL_DATA_SOURCE_UPLOADED, fileURL);
                clientAppSourceDao.createNewSource(ca1);

            }
            else{
                System.out.println("sources : EXTERNAL_DATA_SOURCE_ACTIVE");
                ClientAppSource ca2 = new ClientAppSource(clientAppID, StatusCodeType.EXTERNAL_DATA_SOURCE_ACTIVE, fileURL);
                clientAppSourceDao.createNewSource(ca2);
            }
        }

    }

    @Override
    @Transactional(readOnly = false)
    public void addExternalDataSourceWithClassifiedData(String fileURL, Long platformAppID) {
        //To change body of implemented methods use File | Settings | File Templates.

        ClientApp clientApp = clientAppService.findClientAppByID("platformAppID", platformAppID);

        if(clientApp != null) {
            ClientAppSource ca2 = new ClientAppSource(clientApp.getClientAppID(), StatusCodeType.EXTERNAL_DATA_SOURCE_TO_GEO_READY_REPORT, fileURL);
            clientAppSourceDao.createNewSource(ca2);
        }

    }

    @Override
    @Transactional(readOnly = false)
    public void addExternalDataSouceWithAppType(String fileURL, Integer appType) {

        List<ClientApp> clientApps =  clientAppService.findClientAppByAppType("appType", appType);

        for(ClientApp app : clientApps)
        {
            addExternalDataSouceWithClientAppID(fileURL, app.getClientAppID());
        }

    }

    @Override
    @Transactional(readOnly = false)
    public void addExternalDataSouceWithPlatFormInd(String fileURL, Long micromappersID) {
        ClientApp clientApps =  clientAppService.findClientAppByID("platformAppID",micromappersID);
        if(clientApps!= null){
            addExternalDataSouceWithClientAppID(fileURL, clientApps.getClientAppID());
        }
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = false)
    public void handleMapBoxDataSource(String jsonString) {
        JSONParser parser = new JSONParser();
        try {

            if(!DataFormatValidator.isValidateJson(jsonString)){
                return;
            }

            JSONArray jsonObject = (JSONArray) parser.parse(jsonString);

            Iterator itr= jsonObject.iterator();

            while(itr.hasNext()){
                Object iObj =  itr.next() ;

                JSONObject aJson = (JSONObject)iObj;
                String fname = (String)aJson.get("url");

                double dblLat = (Double)aJson.get("lat");
                double dblLng = (Double)aJson.get("lng");

                String lat = String.valueOf(dblLat);
                String lng = String.valueOf(dblLng);

                JSONArray bounds = this.getBoundsByLatLng(aJson);
                ImageMetaData imageMetaData = new ImageMetaData(fname, lat, lng, bounds.toJSONString());
                imageMetaDataDao.saveMapBoxDataTile(imageMetaData);

            }
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    @Transactional(readOnly = false)
    public void handleMapBoxGistDataSource(String url) {
        JSONParser parser = new JSONParser();
        try {
            GISUtil util = new GISUtil();
            Communicator communicator = new Communicator();
            String jsonString = communicator.sendGet(url);
           // System.out.print(jsonString);

            JSONArray jsonObject = (JSONArray) parser.parse(jsonString);

            Iterator itr= jsonObject.iterator();

            while(itr.hasNext()){
                Object iObj =  itr.next() ;
                String fname = (String)iObj;
                if(fname.indexOf("?access_token=") > -1){
                    fname = fname.substring(0, fname.indexOf("?access_token=")) ;
                }
                double[] coorindates = util.getLatLng(fname);
                String lat = String.valueOf(coorindates[0]);
                String lng = String.valueOf(coorindates[1]);

                fname.replace(".png", "@2x.png");

                JSONArray bounds = this.getBoundsByLatLng(coorindates);

                ImageMetaData imageMetaData = new ImageMetaData(fname, lat, lng, bounds.toJSONString());

                imageMetaDataDao.saveMapBoxDataTile(imageMetaData);

            }
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    private JSONArray getBoundsByLatLng(JSONObject aJson ){

        double dblLat = (Double)aJson.get("lat");
        double dblLng = (Double)aJson.get("lng");


        GeoLocation currentLocation = GeoLocation.fromDegrees(dblLat, dblLng);
        GeoLocation[] boundingCoordinates = currentLocation.boundingCoordinates(CodeLookUp.EARTH_DISTANCE, CodeLookUp.EARTH_RADIUS);

        double minLat = boundingCoordinates[0].getLatitudeInDegrees();
        double minLon = boundingCoordinates[0].getLongitudeInDegrees();

        double maxLat = boundingCoordinates[1].getLatitudeInDegrees();
        double maxLon = boundingCoordinates[1].getLongitudeInDegrees();

        JSONArray jsonArray = new JSONArray();

        jsonArray.add(minLon);
        jsonArray.add(minLat);
        jsonArray.add(maxLon);
        jsonArray.add(maxLat);

        return jsonArray;
    }

    private JSONArray getBoundsByLatLng(double[] coorindates ){

        double dblLat = coorindates[0];
        double dblLng = coorindates[1];


        GeoLocation currentLocation = GeoLocation.fromDegrees(dblLat, dblLng);
        GeoLocation[] boundingCoordinates = currentLocation.boundingCoordinates(CodeLookUp.EARTH_DISTANCE, CodeLookUp.EARTH_RADIUS);

        double minLat = boundingCoordinates[0].getLatitudeInDegrees();
        double minLon = boundingCoordinates[0].getLongitudeInDegrees();

        double maxLat = boundingCoordinates[1].getLatitudeInDegrees();
        double maxLon = boundingCoordinates[1].getLongitudeInDegrees();

        JSONArray jsonArray = new JSONArray();

        jsonArray.add(minLon);
        jsonArray.add(minLat);
        jsonArray.add(maxLon);
        jsonArray.add(maxLat);

        return jsonArray;
    }

}
