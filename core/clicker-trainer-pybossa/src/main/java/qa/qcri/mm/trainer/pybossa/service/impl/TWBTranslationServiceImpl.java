package qa.qcri.mm.trainer.pybossa.service.impl;

import org.apache.log4j.Logger;
import org.springframework.http.*;
import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import qa.qcri.mm.trainer.pybossa.format.impl.TranslationProjectModel;
import  qa.qcri.mm.trainer.pybossa.service.TranslationService;
import org.codehaus.jackson.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * Created by kamal on 3/22/15.
 */
@Service("translationService")
public class TWBTranslationServiceImpl implements TranslationService {

    protected static Logger logger = Logger.getLogger("service.translationService");
    public void pushTranslationRequest() {    }

    final private static String BASE_URL = "https://twb.translationcenter.org/api/v1";
    final private static String API_KEY = "jk26fh2yzwo4";


    public List<TranslationProjectModel> pullTranslationProjects() {
        final String url="https://twb.translationcenter.org/api/v1/projects?client=me";
        HttpHeaders requestHeaders=new HttpHeaders();
        requestHeaders.add("X-Proz-API-Key", "jk26fh2yzwo4");
        requestHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));
        RestTemplate restTemplate=new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


        ResponseEntity<TranslationProjectModel[]> response=restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<Object>(requestHeaders), TranslationProjectModel[].class);
        logger.debug(response);
        TranslationProjectModel[] projectArray = response.getBody();
        ArrayList<TranslationProjectModel>  list = new ArrayList<TranslationProjectModel>(Arrays.asList(projectArray));
        return list;
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
}
