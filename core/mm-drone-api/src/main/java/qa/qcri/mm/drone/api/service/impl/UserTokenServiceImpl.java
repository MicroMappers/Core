package qa.qcri.mm.drone.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qa.qcri.mm.drone.api.dao.UserTokenDao;
import qa.qcri.mm.drone.api.entity.UserToken;
import qa.qcri.mm.drone.api.service.UserTokenService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 7/2/14
 * Time: 10:09 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("userTokenService")
@Transactional(readOnly = true)
public class UserTokenServiceImpl implements UserTokenService {

    @Autowired
    UserTokenDao userTokenDao;


    @Override
    public boolean isValidToken(String token) {
        List<UserToken> tokens = userTokenDao.findToken(token);
        if(tokens.size() > 0)
            return true;
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
