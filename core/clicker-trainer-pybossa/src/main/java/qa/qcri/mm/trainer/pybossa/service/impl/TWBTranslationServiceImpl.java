package qa.qcri.mm.trainer.pybossa.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import qa.qcri.mm.trainer.pybossa.dao.TaskTranslationDao;
import qa.qcri.mm.trainer.pybossa.entity.TaskTranslation;
import qa.qcri.mm.trainer.pybossa.format.impl.TranslationProjectModel;
import qa.qcri.mm.trainer.pybossa.format.impl.TranslationRequestModel;
import qa.qcri.mm.trainer.pybossa.service.TranslationService;


/**
 * Created by kamal on 3/22/15.
 */
@Service("translationService")
public class TWBTranslationServiceImpl implements TranslationService {
	
    @Autowired
    private TaskTranslationDao taskTranslationDao;
    final private static String BASE_URL = "https://twb.translationcenter.org/api/v1";
    final private static String API_KEY = "jk26fh2yzwo4";



    protected static Logger logger = Logger.getLogger("service.translationService");

    public Map pushTranslationRequest(TranslationRequestModel request) {
        Map documentResult = pushDocumentForRequest(request);
        // maybe throw exceptions
        if (documentResult == null) {
            return null;
        }

        long documentIds[] = new long[1];
        documentIds[0] = ((Integer)documentResult.get("document_id")).longValue();
        request.setSourceDocumentIds(documentIds);
        final String url=BASE_URL+"/orders";
        HttpHeaders requestHeaders=new HttpHeaders();
        requestHeaders.add("X-Proz-API-Key", API_KEY);
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate=new RestTemplate();
        //restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        HttpEntity entity = new HttpEntity(getJsonForRequest(request), requestHeaders);

        ResponseEntity<Map> response=restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
        logger.debug(response);
        return response.getBody();

    }

    public Map pushDocumentForRequest(TranslationRequestModel request) {
        String filename = "TWB_Source_"+System.currentTimeMillis()+".csv";

        //decide whether its better to send file or content
        String content = getCSVData(request.getTranslationList());
        //generateCsvFile(filename, request.getTranslationList());

        final String url=BASE_URL+"/documents";
        HttpHeaders requestHeaders=new HttpHeaders();
        requestHeaders.add("X-Proz-API-Key", API_KEY);
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        RestTemplate restTemplate=new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        map.add("document", content);

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new    HttpEntity<LinkedMultiValueMap<String, Object>>(
                map, requestHeaders);
        ResponseEntity<Map> result = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);
        logger.debug("Result of document push:"+result.getBody());
        return result.getBody();

    }

    private static void generateCsvFile(String sFileName, List<TaskTranslation> list)
    {
        String result = null;
        try
        {
            File file = new File(sFileName);
            file.createNewFile();
            String data = getCSVData(list);
            FileWriter writer = new FileWriter(sFileName);
            writer.append(data);
            writer.flush();
            writer.close();


        }
        catch(IOException e)
        {
            logger.error("Error create translation csv file", e);
        }
    }

    private static String getCSVData(List<TaskTranslation> list) {
        StringBuffer buffer = new StringBuffer();
        if (list != null) {
            Iterator<TaskTranslation> iterator = list.iterator();
            while (iterator.hasNext()) {
                TaskTranslation translation = iterator.next();
                buffer.append(Long.toString(translation.getTaskId()));
                buffer.append(",");
                buffer.append(translation.getOriginalText());
                buffer.append(",");
                buffer.append(",");
                buffer.append("\n");

            }
        }
        return buffer.toString();
    }


    public String pullTranslationResponse() {
        return null;
    }



    public List<TranslationProjectModel> pullTranslationProjects(String clientId) {
        final String url=BASE_URL+"/projects?client="+clientId;
        HttpHeaders requestHeaders=new HttpHeaders();
        requestHeaders.add("X-Proz-API-Key", API_KEY);
        requestHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));
        RestTemplate restTemplate=new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        try {
            ResponseEntity<TranslationProjectModel[]> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<Object>(requestHeaders), TranslationProjectModel[].class);
            logger.debug(response);
            TranslationProjectModel[] projectArray = response.getBody();
            ArrayList<TranslationProjectModel> list = new ArrayList<TranslationProjectModel>(Arrays.asList(projectArray));
            return list;
        } catch (HttpClientErrorException exception) {
            logger.debug("Exception caught: " +exception.getResponseBodyAsString());
        }
        return null;
    }
    //temporary for testing
    public String pullTranslationProjectsAsString(String clientId) {
        final String url=BASE_URL+"/projects?client="+clientId;
        HttpHeaders requestHeaders=new HttpHeaders();
        requestHeaders.add("X-Proz-API-Key", API_KEY);
        RestTemplate restTemplate=new RestTemplate();


        ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<Object>(requestHeaders), String.class);
        logger.debug(response);
        return response.getBody();
    }

    //brute force but simpler to debug than using Jackson
    private String getJsonForRequest(TranslationRequestModel request) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String formattedDate = formatter.format(request.getDeadline());

        String jsonString = "{            \n" +
                "            \"contact_email\": \"" + request.getContactEmail() + "\",\n" +
                "            \"title\": \"" + request.getTitle() + "\",\n" +
                "            \"source_lang\": \"" + request.getSourceLanguage() + "\",\n" +
                "            \"target_langs\": [\"" + request.getTargetLanguages()[0] + "\"],\n" +
                "            \"source_document_ids\": [" + request.getSourceDocumentIds()[0] + "],\n" +
                "            \"source_wordcount\":" + request.getSourceWordCount() + ",\n" +
                "            \"instructions\": \"" + request.getInstructions() + "\",\n" +
                "            \"deadline\": \"" + formattedDate + "\",\n" +
                "            \"urgency\": \"" + request.getUrgency() + "\",\n" +
                "            \"project_id\": " + request.getProjectId() + ",\n" +
                "            \"callback_url\": \"" + request.getCallbackURL() + "\"\n" +
                "}";
        return jsonString;
    }

	@Override
	@Transactional
	public void createTranslation(TaskTranslation translation) {
		taskTranslationDao.save(translation);
		
	}

	@Override
	@Transactional
	public void updateTranslation(TaskTranslation translation) {
		taskTranslationDao.saveOrUpdate(translation);
	}

	@Override
	@Transactional
	public TaskTranslation findById(Long translationId) {
		 return taskTranslationDao.findTranslationByID(translationId);
	}

	@Override
	@Transactional
	public void delete(TaskTranslation translation) {
		taskTranslationDao.delete(translation);
	}

	@Override
	@Transactional
	public List<TaskTranslation> findAllTranslations() {
		return taskTranslationDao.getAll();
	}
}
