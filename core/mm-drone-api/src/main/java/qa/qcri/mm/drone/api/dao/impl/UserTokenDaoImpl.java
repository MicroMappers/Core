package qa.qcri.mm.drone.api.dao.impl;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import qa.qcri.mm.drone.api.dao.UserTokenDao;
import qa.qcri.mm.drone.api.entity.UserToken;


import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 7/2/14
 * Time: 9:40 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class UserTokenDaoImpl extends AbstractDaoImpl<UserToken, String> implements UserTokenDao{


    protected UserTokenDaoImpl(){
        super(UserToken.class);
    }
    @Override
    public List<UserToken> findToken(String token) {
        return findByCriteria(Restrictions.eq("token", token));  //To change body of implemented methods use File | Settings | File Templates.
    }
}
