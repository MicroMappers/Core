package qa.qcri.mm.trainer.pybossa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

        List list = translationService.pullTranslationProjects("1211");
        assert(list.size() > 0);

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
        model.setSourceWordCount(100); //random test
        model.setInstructions("Unit test instructions");
        model.setDeadline(new Date());
        model.setUrgency("high");
        model.setProjectId(5681);// hard coded for now

        model.setCallbackURL("https://www.example.com/my-callback-url");

        generateTestTranslationTasks(model);

        Map result = translationService.pushTranslationRequest(model);
        assertNotNull(result);
    }

    @Test
    public void testPushDocumentForRequest() {
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

        generateTestTranslationTasks(model);

        Map result = translationService.pushDocumentForRequest(model);
        assertNotNull(result.get("document_id"));
    }


    private void generateTestTranslationTasks(TranslationRequestModel model) {
        TaskTranslation translation = new TaskTranslation();
        translation.setTaskId((long) 1);
        translation.setClientAppId("1211");
        translation.setOriginalText("Je m'appelle Jacques");
        translation.setStatus("New");
        //translationService.createTranslation(translation);

        TaskTranslation translation2 = new TaskTranslation();
        translation2.setTaskId((long)2);
        translation2.setClientAppId("1211");
        translation2.setOriginalText("Me llamo es Juan");
        translation2.setStatus("New");
        //translationService.createTranslation(translation2);

        List<TaskTranslation> list = new ArrayList<TaskTranslation>();
        list.add(translation);
        list.add(translation2);
        model.setTranslationList(list);

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
