package qa.qcri.mm.api.service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.text.translate.UnicodeEscaper;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import qa.qcri.mm.api.service.ClientAppSourceService;
import qa.qcri.mm.api.service.PartnerAppSourceService;
import au.com.bytecode.opencsv.CSVWriter;


/**
 * @author Kushal
 *
 */
@Service("partnerAppSourceService")
public class PartnerAppSourceServiceImpl implements PartnerAppSourceService {

	@Autowired
	ClientAppSourceService clientAppSourceService;

	private static UnicodeEscaper unicodeEscaper = UnicodeEscaper.above(127); 
	protected static Logger logger = Logger.getLogger("PartnerAppSourceService");

	@Override
	public void pushAppSource(String importURL, Long crisisId, Long userId, Long recordsCount, String crisisCode, String fileLocation) throws Exception{

		HttpClient httpClient = new DefaultHttpClient();
		String jsonResponse=null;
		JSONParser parser = new JSONParser();
		try {

			HttpGet request = new HttpGet(importURL + "/crisisID/" + crisisId + "/userID/" + userId);
			request.addHeader("content-type", "application/json");
			request.addHeader("Accept", "*/*");
			request.addHeader("Accept-Encoding", "gzip,deflate,sdch");
			request.addHeader("Accept-Language", "en-US,en;q=0.8");
			jsonResponse = httpClient.execute(request, new BasicResponseHandler());
		}catch (Exception ex) {
			logger.error("Exception while importing the human labelled tweets.",ex);
			throw new Exception("Exception while importing the human labelled tweets.");
		} finally {
			httpClient.getConnectionManager().shutdown();
		}

		try {
			JSONObject responseObj = (JSONObject) parser.parse(jsonResponse);
			JSONArray itemsArray = (JSONArray) responseObj.get("items");

			String[] csvRecord;
			Long count=0L;
			String filename = (new Date()).getTime()+"_"+crisisCode+".csv";
			CSVWriter writer = createNewCsvFile(fileLocation + "/" + filename);
			List<String> filesToPush = new ArrayList<String>(); 

			for (Object itemObj : itemsArray) {
				csvRecord = new String[5];

				responseObj = (JSONObject) parser.parse(itemObj.toString());
				JSONArray labelDataArray = (JSONArray) responseObj.get("labelData");

				//doc
				responseObj = (JSONObject) parser.parse(responseObj.get("doc").toString());
				responseObj = (JSONObject) parser.parse(responseObj.get("data").toString());
				csvRecord[0] = responseObj.get("id").toString();  //TweetId
				csvRecord[1] = unicodeEscaper.translate(responseObj.get("text").toString());  //Tweet Text
				
				csvRecord[3] = responseObj.get("created_at").toString();   //created At time
				responseObj = (JSONObject) parser.parse(responseObj.get("user").toString());
				csvRecord[2] = unicodeEscaper.translate(responseObj.get("name").toString());  //Tweet author

				//labelData
				for (Object object2 : labelDataArray) {
					responseObj = (JSONObject) parser.parse(object2.toString());
					responseObj = (JSONObject) parser.parse(responseObj.get("nominalLabelDTO").toString());
					responseObj = (JSONObject) parser.parse(responseObj.get("nominalAttributeDTO").toString());
					csvRecord[4] = responseObj.get("code").toString();    //Nominal Label Code
				}

				if(count++>=recordsCount){
					writer.flush();
					writer.close();
					filesToPush.add(fileLocation + "/" + filename);
					filename = (new Date()).getTime()+"_"+crisisCode+".csv";
					writer = createNewCsvFile(fileLocation + "/" + filename);
					writer.writeNext(csvRecord);
					count=1L;
				}
				else{
					writer.writeNext(csvRecord);
				}
			}
			writer.flush();
			writer.close();
			filesToPush.add(fileLocation + "/" + filename);

			for (String fileName : filesToPush) {
				clientAppSourceService.addExternalDataSourceWithClassifiedData(fileName, crisisId);
			}

		} catch (ParseException e) {
			logger.error("Exception while parsing the json ",e);
			throw new Exception("Exception while parsing the json ");
		}

	}


	private CSVWriter createNewCsvFile(String fileName) throws Exception{
		CSVWriter writer = null;
		try {
			writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
			String[] csvHeader = {"tweetId", "tweet", "author", "created", "answerCode"};
			writer.writeNext(csvHeader);

		} catch (FileNotFoundException e) {
			logger.error("Unable to create a new csv file", e);
			throw new Exception("Unable to create a new csv file", e);
		}
		return writer;
	}

}
