package qa.qcri.mm.trainer.api.service;

import qa.qcri.mm.trainer.api.entity.ClientAppAnswer;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 6/16/14
 * Time: 1:48 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ClientAppAnswerService {
    ClientAppAnswer getClientAppAnswer(Long clientAppID);
}
