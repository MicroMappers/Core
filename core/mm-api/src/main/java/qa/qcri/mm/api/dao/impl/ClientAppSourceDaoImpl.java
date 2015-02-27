package qa.qcri.mm.api.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import qa.qcri.mm.api.dao.ClientAppSourceDao;
import qa.qcri.mm.api.entity.ClientAppSource;
import qa.qcri.mm.api.store.StatusCodeType;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 3/4/14
 * Time: 12:17 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class ClientAppSourceDaoImpl extends AbstractDaoImpl<ClientAppSource, String> implements ClientAppSourceDao {

    protected ClientAppSourceDaoImpl(){
        super(ClientAppSource.class);
    }

    @Override
    public List<ClientAppSource> findActiveSourcePerClient(Long clientAppID) {
        return findByCriteria(Restrictions.conjunction()
                        .add(Restrictions.eq("clientAppID",clientAppID))
                        .add(Restrictions.eq("status", StatusCodeType.EXTERNAL_DATA_SOURCE_ACTIVE)));
    }

    @Override
    public boolean findDuplicateSource(String fileURL, Long clientAppID) {
        List<ClientAppSource>  sources  = findByCriteria(Restrictions.conjunction()
                .add(Restrictions.eq("clientAppID",clientAppID))
                .add(Restrictions.eq("sourceURL", fileURL)));

        if(sources.size() > 0)    {
            return true;
        }
        return false;


    }

    @Override
    public void acceptSource(String fileURL, Long clientAppID) {
        List<ClientAppSource>  sources = findActiveSourcePerClient( clientAppID );

        if(sources.size() > 0){
            System.out.println("sources : EXTERNAL_DATA_SOURCE_UPLOADED");
            ClientAppSource ca1 = new ClientAppSource(clientAppID, StatusCodeType.EXTERNAL_DATA_SOURCE_UPLOADED, fileURL);
            save(ca1);
        }
        else{
            System.out.println("sources : EXTERNAL_DATA_SOURCE_ACTIVE");
            ClientAppSource ca2 = new ClientAppSource(clientAppID, StatusCodeType.EXTERNAL_DATA_SOURCE_ACTIVE, fileURL);
            save(ca2);
        }
    }


    @Override
    public void createNewSource(ClientAppSource clientAppSource) {
           // save(clientAppSource);
            save(clientAppSource);
    }

}
