package qa.qcri.mm.trainer.pybossa.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import qa.qcri.mm.trainer.pybossa.format.impl.TranslationRequestModel;

import java.util.Date;
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
    public void testPullTranslationProjects() throws Exception {

        //List list = translationService.pullTranslationProjects();
        //assert(list.size() > 0);

        String result = translationService.pullTranslationProjectsAsString("me");
        assert(result != null);

        String result2 = translationService.pullTranslationProjectsAsString("1211");
        assert(result2 != null);

    }

    @Test
    public void testPushTranslationRequest() {
        TranslationRequestModel model = new TranslationRequestModel();
        model.setContactEmail("test@test.com");
        model.setTitle("Request from Unit Test");
        model.setSourceLanguage("eng");
        String[] targets = {"fra","esl"};
        model.setTargetLanguages(targets);
        long[] documentIds = {125549};
        model.setSourceDocumentIds(documentIds);
        model.setSourceWordCount(100); //random test
        model.setInstructions("Unit test instructions");
        model.setDeadline(new Date());
        model.setUrgency("high");
        model.setProjectId(5681);// hard coded for now

        model.setCallbackURL("https://www.example.com/my-callback-url");

        String result = translationService.pushTranslationRequest(model);
        assert(result != null);
    }
}
