package qa.qcri.mm.trainer.pybossa.dao;


import qa.qcri.mm.trainer.pybossa.entity.Client;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jilucas
 * Date: 9/18/13
 * Time: 5:49 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ClientDao extends AbstractDao<Client, String> {

    void createClient(Client client);
    Client findClientByID(String columnName,Long id);
    Client findClientByCriteria(String columnName, String value);
    List<Client> findActiveClient();

}
