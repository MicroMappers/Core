package qa.qcri.mm.trainer.pybossa.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 9/30/14
 * Time: 11:03 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml", "classpath:spring/hibernateContext.xml"})
public class NamibiaReportServiceTest {


    @Autowired
    NamibiaReportService namibiaReportService;

    @Test
    public void testCleanUp() throws Exception {

       // namibiaReportService.cleanUp();

    }
}
