package qa.qcri.mm.trainer.pybossa.service;

import qa.qcri.mm.trainer.pybossa.entity.ClientAppEvent;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 12/5/13
 * Time: 1:25 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ClientAppEventService {

    ClientAppEvent getNextSequenceClientAppEvent(Long clientAppID);
}
