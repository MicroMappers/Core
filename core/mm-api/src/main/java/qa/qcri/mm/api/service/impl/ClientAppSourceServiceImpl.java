package qa.qcri.mm.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qa.qcri.mm.api.dao.ClientAppSourceDao;
import qa.qcri.mm.api.entity.ClientApp;
import qa.qcri.mm.api.entity.ClientAppSource;
import qa.qcri.mm.api.service.ClientAppService;
import qa.qcri.mm.api.service.ClientAppSourceService;
import qa.qcri.mm.api.store.StatusCodeType;

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

}
