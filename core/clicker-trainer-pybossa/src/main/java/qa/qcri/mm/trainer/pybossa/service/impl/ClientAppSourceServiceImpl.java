package qa.qcri.mm.trainer.pybossa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qa.qcri.mm.trainer.pybossa.dao.ClientAppSourceDao;
import qa.qcri.mm.trainer.pybossa.entity.ClientAppSource;
import qa.qcri.mm.trainer.pybossa.service.ClientAppSourceService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 10/18/13
 * Time: 2:32 AM
 * To change this template use File | Settings | File Templates.
 */
@Service("clientAppSourceService")
@Transactional(readOnly = true)
public class ClientAppSourceServiceImpl implements ClientAppSourceService {

    @Autowired
    private ClientAppSourceDao clientAppSourceDao;

    @Override
    public List<ClientAppSource> getClientAppSourceByStatus(Long clientAppID, int status) {
        return clientAppSourceDao.getClientAppSource(clientAppID, status);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ClientAppSource> getClientAppSourceWithStatusCode(int status) {
        return clientAppSourceDao.getClientAppSourceWithStatusOnly(status);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = false)
    public void updateClientAppSourceStatus(Long clientAppID, int status) {
        System.out.println("clientAppID :" + clientAppID);
        clientAppSourceDao.updateClientAppSourceStatus(clientAppID, status);
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = false)
    public void insertNewClientAppSource(ClientAppSource clientAppSource) {
        try{
            clientAppSourceDao.insertClientAppSource(clientAppSource);
        }
        catch(Exception ex){
            System.out.println("insertNewClientAppSource exception : " + ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public ClientAppSource getClientAppSourceByClientAppID(Long clientAppSourceID) {
        return clientAppSourceDao.getClientAppSourceByID(clientAppSourceID);
    }
}
