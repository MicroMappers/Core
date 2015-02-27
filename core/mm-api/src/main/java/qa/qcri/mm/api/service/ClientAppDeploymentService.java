package qa.qcri.mm.api.service;

import qa.qcri.mm.api.entity.ClientAppDeployment;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 6/12/14
 * Time: 7:28 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ClientAppDeploymentService {
     ClientAppDeployment getActiveDeploymentForAppType(int appType);
     void deactivateDeployment(Long deploymentID);
     List<ClientAppDeployment> getActiveDeployment();
     List<ClientAppDeployment> getMobileActiveDeployment();
}
