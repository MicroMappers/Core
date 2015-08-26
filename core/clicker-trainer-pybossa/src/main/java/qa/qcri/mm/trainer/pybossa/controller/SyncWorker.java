package qa.qcri.mm.trainer.pybossa.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qa.qcri.mm.trainer.pybossa.service.MicroMapperWorker;
import qa.qcri.mm.trainer.pybossa.service.Worker;


/**
 * A synchronous worker
 */
@Component("syncWorker")
public class SyncWorker implements Worker {

	protected static Logger logger = Logger.getLogger("SyncWorker");

    @Autowired
    private MicroMapperWorker microMapperWorker;

	public void work() {
		String threadName = Thread.currentThread().getName();
		logger.info("   " + threadName + " has began working.(SyncWorker - run ClientApps)");

        try {
            microMapperWorker.processTaskPublish();
            microMapperWorker.processTaskImport();
            microMapperWorker.processTaskExport();

            Thread.sleep(180000);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            logger.info(e.getMessage());
        }

    }
	
}
