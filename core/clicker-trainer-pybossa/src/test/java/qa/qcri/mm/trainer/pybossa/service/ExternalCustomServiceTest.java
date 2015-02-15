package qa.qcri.mm.trainer.pybossa.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 12/2/14
 * Time: 4:38 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml", "classpath:spring/hibernateContext.xml"})
public class ExternalCustomServiceTest {

    @Autowired
    private ExternalCustomService externalCustomService;

    @Test
    public void testTestAerialClick() throws Exception {

      //  String jsonData =  this.sendGet("http://clickers.micromappers.org/api/taskrun?app_id=44257&task_id=534197");
       // TaskQueueResponse rsp = externalCustomService.testAerialClick(jsonData);

    }

    public String sendGet(String url) {
        final String USER_AGENT = "Mozilla/5.0";
        HttpURLConnection con = null;
        StringBuffer response = new StringBuffer();
        // System.out.println("sendGet url : " + url);
        // logger.debug("[sendGet url  for debugger: ]" + url);

        try {
            URL connectionURL = new URL(url);
            con = (HttpURLConnection) connectionURL.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();


            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(),"UTF-8"));
            String inputLine;
            // logger.debug("[response code ]" + responseCode);
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

        }catch (Exception ex) {
            System.out.println("ex Code sendGet: " + ex + " : sendGet url = " + url);

        }

        return response.toString();
    }
}
