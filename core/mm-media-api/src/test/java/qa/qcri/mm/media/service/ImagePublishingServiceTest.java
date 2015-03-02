package qa.qcri.mm.media.service;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 3/1/15
 * Time: 2:04 PM
 * To change this template use File | Settings | File Templates.
 */

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml", "classpath:spring/hibernateContext.xml"})
public class ImagePublishingServiceTest {

     @Autowired
     ImagePublishingService imagePublishingService;

    @Test
    public void doSomething(){
        try{
            imagePublishingService.publishToPicasa("testing");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.getMessage();
        }

    }
}
