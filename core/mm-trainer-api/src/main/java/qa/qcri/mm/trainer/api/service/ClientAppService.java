package qa.qcri.mm.trainer.api.service;



import qa.qcri.mm.trainer.api.entity.ClientApp;
import qa.qcri.mm.trainer.api.template.ClientAppModel;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jilucas
 * Date: 9/19/13
 * Time: 11:59 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ClientAppService {

    ClientApp findClientAppByID(String columnName, Long id);
    ClientApp findClientAppByCriteria(String columnName, String value);
    List<ClientApp> getAllClientAppByClientID(Long clientID);
    List<ClientApp> findClientAppByStatus(Integer status);
    List<ClientApp> getAllClientAppByCrisisID(Long crisisID);
    List<ClientApp> findClientAppByAppType(String columnName, Integer typeID);
    void updateClientAppByShortName(String shortName, Integer status);
    ClientApp getClientAppByCrisisAndAttribute(Long crisisID, Long attributeID);
    void deactivateClientAppByCrisis(Long crisisID) throws Exception;
    void deactivateClientAppByAttribute(Long crisisID, Long attributeID) throws Exception;
    List<ClientAppModel> getAllActiveClientApps();
    String enableForClientAppStatusByCrisisID(Long crisisID, Integer status);

}
