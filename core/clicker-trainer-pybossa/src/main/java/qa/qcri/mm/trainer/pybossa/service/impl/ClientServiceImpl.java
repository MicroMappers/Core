package qa.qcri.mm.trainer.pybossa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qa.qcri.mm.trainer.pybossa.dao.ClientDao;
import qa.qcri.mm.trainer.pybossa.entity.Client;
import qa.qcri.mm.trainer.pybossa.service.ClientService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jilucas
 * Date: 9/19/13
 * Time: 11:45 AM
 * To change this template use File | Settings | File Templates.
 */
@Service("clientService")
@Transactional(readOnly = true)
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientDao clientDao;

    @Override
    public Client findClientbyID(String columnName, Long id) {
        return clientDao.findClientByID(columnName, id);
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Client findClientByCriteria(String columnName, String value) {

        return clientDao.findClientByCriteria(columnName, value);
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Client> getActiveClientList() {
        return clientDao.findActiveClient();  //To change body of implemented methods use File | Settings | File Templates.
    }
}
