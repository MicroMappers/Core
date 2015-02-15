package qa.qcri.mm.trainer.api.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 1/7/15
 * Time: 11:30 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml", "classpath:spring/hibernateContext.xml"})
public class GeoServiceTest {

    @Autowired
    private GeoService geoService;

    @Test
    public void testGetGeoJsonOutput() throws Exception {
        geoService.getGeoJsonOutput();

    }

}
