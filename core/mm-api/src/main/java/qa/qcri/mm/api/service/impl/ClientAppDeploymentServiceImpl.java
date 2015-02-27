package qa.qcri.mm.api.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qa.qcri.mm.api.dao.ClientAppDeploymentDao;
import qa.qcri.mm.api.entity.ClientAppDeployment;
import qa.qcri.mm.api.service.ClientAppDeploymentService;
import qa.qcri.mm.api.store.StatusCodeType;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 6/15/14
 * Time: 9:43 AM
 * To change this template use File | Settings | File Templates.
 */
@Service("clientAppDeploymentService")
@Transactional(readOnly = true)
public class ClientAppDeploymentServiceImpl implements ClientAppDeploymentService {

    protected static Logger logger = Logger.getLogger("ClientAppDeploymentService");

    @Autowired
    ClientAppDeploymentDao clientAppDeploymentDao;


    @Override
    public ClientAppDeployment getActiveDeploymentForAppType(int appType) {
        List<ClientAppDeployment> deploymentList = clientAppDeploymentDao.findClientAppDeploymentByAppType(appType);

        if(deploymentList.size() > 0){
            return deploymentList.get(0);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public void deactivateDeployment(Long deploymentID) {
        clientAppDeploymentDao.updateClientAppDeploymentStatus(deploymentID, StatusCodeType.DEPLOYMENT_RETIRED);
    }

    @Override
    public List<ClientAppDeployment> getActiveDeployment() {
        return clientAppDeploymentDao.findActiveClientAppDeployment();
    }

    @Override
    public List<ClientAppDeployment> getMobileActiveDeployment() {
        return clientAppDeploymentDao.findClientAppDeploymentByStatus(StatusCodeType.DEPLOYMENT_MOBILE);
    }
}
