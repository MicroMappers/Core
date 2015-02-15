package qa.qcri.mm.trainer.pybossa.service;

import qa.qcri.mm.trainer.pybossa.entity.Client;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jilucas
 * Date: 9/19/13
 * Time: 11:39 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ClientService {

    Client findClientbyID(String columnName, Long id);
    Client findClientByCriteria(String columnName, String value);
    List<Client> getActiveClientList();

}
