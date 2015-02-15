package qa.qcri.mm.trainer.pybossa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qa.qcri.mm.trainer.pybossa.dao.ClientAppDao;
import qa.qcri.mm.trainer.pybossa.entity.ClientApp;
import qa.qcri.mm.trainer.pybossa.service.ClientAppService;

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

    @Autowired
    private ClientAppDao clientAppDao;

    @Override
    @Transactional(readOnly = false)
    public void createClientApp(ClientApp clientApp) {
        clientAppDao.createClientApp(clientApp);
    }

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
    public List<ClientApp> getAllCrisis() {

        return clientAppDao.getAllCrisisID();  //To change body of implemented methods use File | Settings | File Templates.
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
    public List<ClientApp> getAllClientAppByClientIDAndStatus(Long clientID, Integer status) {
        return clientAppDao.getAllClientAppByClientIDAndStatus(clientID, status);
        //return clientAppDao.findAllClientAppByCrisisID(crisisID);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ClientApp> getAllClientAppByCrisisIDAndStatus(Long crisisID, Integer status) {
        return clientAppDao.getAllClientAppByCrisisIDAndStatus(crisisID, status);
        //return clientAppDao.findAllClientAppByCrisisID(crisisID);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = false)
    public void updateClientAppStatus(ClientApp clientApp, Integer status) {
        ClientApp app = findClientAppByID("clientAppID", clientApp.getClientAppID());
        if(app != null){
            app.setStatus(status);
            clientAppDao.updateClientApp(app);
        }
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
