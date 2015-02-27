package qa.qcri.mm.api.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 2/27/15
 * Time: 2:48 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml", "classpath:spring/hibernateContext.xml"})

public class ClientAppSourceServiceTest {
    @Autowired
    ClientAppSourceService clientAppSourceService;

    @Test
    public void testAddExternalDataSourceWithClassifiedData() throws Exception {
        String fileURL ="hello.txt";
        long platformID = 41;

        clientAppSourceService.addExternalDataSourceWithClassifiedData(fileURL, platformID);

    }
}
