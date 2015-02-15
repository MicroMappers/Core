package qa.qcri.mm.drone.api.dao;

import qa.qcri.mm.drone.api.entity.UserToken;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 7/2/14
 * Time: 9:30 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UserTokenDao extends AbstractDao<UserToken, String> {

   List<UserToken> findToken(String token);
}
