package qa.qcri.mm.trainer.api.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import qa.qcri.mm.trainer.api.dao.ClientAppDao;
import qa.qcri.mm.trainer.api.entity.Client;
import qa.qcri.mm.trainer.api.entity.ClientApp;
import qa.qcri.mm.trainer.api.service.ClientAppService;
import qa.qcri.mm.trainer.api.store.StatusCodeType;
import qa.qcri.mm.trainer.api.store.URLReference;
import qa.qcri.mm.trainer.api.template.ClientAppModel;
import qa.qcri.mm.trainer.api.util.Communicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jilucas
 * Date: 9/19/13
 * Time: 12:01 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("clientAppService")
@Transactional(readOnly = true)
public class ClientAppServiceImpl implements ClientAppService {

    protected static Logger logger = Logger.getLogger("ClientAppService");

    @Autowired
    private ClientAppDao clientAppDao;

    @Override
    public ClientApp findClientAppByID(String columnName, Long id) {
        return clientAppDao.findClientAppByID(columnName, id);//To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ClientApp findClientAppByCriteria(String columnName, String value) {
        return clientAppDao.findClientAppByCriteria(columnName, value);
       // return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ClientApp> getAllClientAppByClientID(Long clientID) {
        return clientAppDao.findAllClientApp(clientID) ;
    }

    @Override
    public List<ClientApp> findClientAppByStatus(Integer status) {
        return clientAppDao.findAllClientAppByStatus(status);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ClientApp> getAllClientAppByCrisisID(Long crisisID) {
        return clientAppDao.findAllClientAppByCrisisID(crisisID);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ClientApp> findClientAppByAppType(String columnName, Integer typeID) {
        return clientAppDao.findClientAppByAppType(columnName, typeID);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateClientAppByShortName(String shortName, Integer status) {
       ClientApp clientApp = findClientAppByCriteria("shortName",shortName);
       if(clientApp != null){
           clientApp.setStatus(status);
           clientAppDao.saveOrUpdate(clientApp);
       }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public String enableForClientAppStatusByCrisisID(Long crisisID, Integer status) {

        if(findActiveMobilePushClientApp()){
            return StatusCodeType.MOBILE_STATUS_UPDATE_FAIL_RUNING_APP;
        }

        String returnValue = "";

        List<ClientApp> apps = getAllClientAppByCrisisID(crisisID);
        int count = 0;
        ClientApp clientApp = null;
        for(ClientApp app : apps){
            if(app.getStatus().equals(StatusCodeType.AIDR_MICROMAPPER_BOTH)){
                clientApp = app;
                count++;
            }
            if(app.getStatus().equals(StatusCodeType.AIDR_ONLY)){
                clientApp = app;
                count++;
            }
        }
        if(clientApp == null){
            returnValue = StatusCodeType.MOBILE_STATUS_UPDATE_FAIL_WITH_NO_APP ;
        }

        if(count > 1){
            returnValue = StatusCodeType.MOBILE_STATUS_UPDATE_FAIL_MULTI_APP ;
        }

        if(clientApp != null && count == 1){
            clientAppDao.updateClientAppStatus(clientApp, status);
            returnValue = StatusCodeType.RETURN_SUCCESS;
        }

        return returnValue;
    }

    @Override
    public ClientApp getClientAppByCrisisAndAttribute(Long crisisID, Long attributeID) {
        List<ClientApp> appList = clientAppDao.findClientAppByCrisisAndAttribute(crisisID, attributeID);
        if(appList.size() > 0){
            return appList.get(0);
        }
        return null;

    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deactivateClientAppByCrisis(Long crisisID) throws Exception {
        List<ClientApp> clientAppList = clientAppDao.findAllClientAppByCrisisID(crisisID);
        Client client = null;

        if(clientAppList.size() > 0){
            Communicator pybossaCommunicator = new Communicator();
            client = clientAppList.get(0).getClient();
            for (int i = 0; i < clientAppList.size(); i++) {
                ClientApp currentClientApp = clientAppList.get(i);
                if(!currentClientApp.getStatus().equals(StatusCodeType.CLIENT_APP_DISABLED)){
                    String deleteURL = client.getHostURL() + URLReference.PYBOSAA_APP + currentClientApp.getPlatformAppID()+ URLReference.PYBOSSA_APP_UPDATE_KEY + client.getHostAPIKey();
                    //logger.debug("deactivateClientAppByCrisis deleteURL : " + deleteURL);
                    String returnValue = pybossaCommunicator.deleteGet(deleteURL);
                    //logger.debug("deactivateClientAppByCrisis deleteURL  returnValue: " + returnValue);
                    clientAppDao.updateClientAppStatus(currentClientApp, StatusCodeType.CLIENT_APP_DISABLED);
                }
            }
        }

    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deactivateClientAppByAttribute(Long crisisID, Long attributeID) throws Exception {
        List<ClientApp> clientAppList = clientAppDao.findClientAppByCrisisAndAttribute(crisisID, attributeID);
        Client client = null;

        if(clientAppList.size() > 0){
            Communicator pybossaCommunicator = new Communicator();
            client = clientAppList.get(0).getClient();
            for (int i = 0; i < clientAppList.size(); i++) {
                ClientApp currentClientApp = clientAppList.get(i);
                if(!currentClientApp.getStatus().equals(StatusCodeType.CLIENT_APP_DISABLED)){
                    String deleteURL = client.getHostURL() + URLReference.PYBOSAA_APP + currentClientApp.getPlatformAppID()+ URLReference.PYBOSSA_APP_UPDATE_KEY + client.getHostAPIKey();
                    //logger.debug("deactivateClientAppByAttribute : deleteURL : " + deleteURL);
                    String returnValue = pybossaCommunicator.deleteGet(deleteURL);
                    logger.debug("deactivateClientAppByAttribute : deleteURL  returnValue: " + returnValue);
                    clientAppDao.updateClientAppStatus(currentClientApp, StatusCodeType.CLIENT_APP_DISABLED);
                }
            }
        }

    }

    @Override
    public List<ClientAppModel> getAllActiveClientApps() {
        List<ClientApp> appList = clientAppDao.getAllActiveClientApp();
        List<ClientAppModel> aList = new ArrayList<ClientAppModel>();

        for (ClientApp t : appList) {
            //System.out.println(temp);
            //Long id, Long platformID, Long crisisID, String name, String shortName, Integer appType
            ClientAppModel model = new ClientAppModel(t.getClientAppID(),t.getPlatformAppID(),t.getCrisisID(),t.getName(), t.getShortName(), t.getAppType());
            aList.add(model);
        }

        return aList;  //To change body of implemented methods use File | Settings | File Templates.
    }


    private boolean findActiveMobilePushClientApp(){
        List<ClientApp> appList = clientAppDao.getAllActiveClientApp();
        if(appList.isEmpty() || appList.size() > 0){
            for(ClientApp app : appList){
                if(app.getStatus().equals(StatusCodeType.AIDR_MICROMAPPER_BOTH)){
                    return true;
                }
            }
        }
        return false;
    }
}
