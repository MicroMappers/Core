package qa.qcri.mm.media.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 2/25/15
 * Time: 11:31 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml", "classpath:spring/hibernateContext.xml"})
public class PoolServiceTest {

    @Autowired
    PoolService poolService;

    @Test
    public void testProcessSourceQueue() throws Exception {
        poolService.processSourceQueue();
    }
}
