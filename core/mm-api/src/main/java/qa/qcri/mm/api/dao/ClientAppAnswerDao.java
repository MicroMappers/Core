package qa.qcri.mm.api.dao;

import qa.qcri.mm.api.entity.ClientAppAnswer;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 6/15/14
 * Time: 1:34 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ClientAppAnswerDao {

    List<ClientAppAnswer> getClientAppAnswer(Long clientAppID);

}
