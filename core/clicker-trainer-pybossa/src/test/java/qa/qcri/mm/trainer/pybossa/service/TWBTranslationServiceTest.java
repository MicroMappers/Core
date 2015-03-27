package qa.qcri.mm.trainer.pybossa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import qa.qcri.mm.trainer.pybossa.dao.TaskTranslationDao;
import qa.qcri.mm.trainer.pybossa.entity.TaskTranslation;
import qa.qcri.mm.trainer.pybossa.format.impl.TranslationProjectModel;
import qa.qcri.mm.trainer.pybossa.format.impl.TranslationRequestModel;

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
        assertNotNull(result);

        String result2 = translationService.pullTranslationProjectsAsString("1211");
        assertNotNull(result2);

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
        assertNotNull(result);
    }
    
    @Test
    public void testCreateAndUpdateTranslation() throws Exception {
    	TaskTranslation translation = new TaskTranslation();
    	translationService.createTranslation(translation);
    	assertNotNull(translation.getTranslationId());
    	String newVal = "TEST";
    	translation.setStatus(newVal);
    	translationService.updateTranslation(translation);
    	translation = translationService.findById(translation.getTranslationId());
    	// we would really need to flush and clear the hibernate session for this next validation
    	assertEquals(newVal, translation.getStatus());
    	translationService.delete(translation);
    	assertEquals(0, translationService.findAllTranslations().size());
    }
    
    
}
