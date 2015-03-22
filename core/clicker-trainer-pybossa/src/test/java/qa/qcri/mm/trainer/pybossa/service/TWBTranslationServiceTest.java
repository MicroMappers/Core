package qa.qcri.mm.trainer.pybossa.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 2/27/15
 * Time: 2:48 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml", "classpath:spring/hibernateContext.xml"})

public class TWBTranslationServiceTest {
    @Autowired
    TranslationService translationService;

    @Test
    public void testPullTranslations() throws Exception {

        //List list = translationService.pullTranslationProjects();
        //assert(list.size() > 0);

        String result = translationService.pullTranslationProjectsAsString("me");
        assert(result != null);

        String result2 = translationService.pullTranslationProjectsAsString("1211");
        assert(result2 != null);

    }
}
