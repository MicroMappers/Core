package qa.qcri.mm.drone.api.service;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 7/2/14
 * Time: 9:56 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UserTokenService {
    boolean isValidToken(String token);
}
