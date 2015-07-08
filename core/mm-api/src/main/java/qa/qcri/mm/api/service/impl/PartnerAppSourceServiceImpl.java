package qa.qcri.mm.api.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.text.translate.UnicodeEscaper;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import qa.qcri.mm.api.service.ClientAppSourceService;
import qa.qcri.mm.api.service.PartnerAppSourceService;
import qa.qcri.mm.api.util.Communicator;
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
	public void pushAppSource(String importURL, Long crisisId, Long recordsCount, String crisisCode, String fileLocation) throws Exception{
		String jsonResponse=null;
		JSONParser parser = new JSONParser();
		Communicator comm = new Communicator();
		String url = importURL + "/crisisCode/" + crisisCode;
		jsonResponse = comm.sendGet(url);

		try {
			JSONObject responseObj = (JSONObject) parser.parse(jsonResponse);
			JSONArray itemsArray = (JSONArray) responseObj.get("items");

			String[] csvRecord;
			Long count=0L;
			String filename = (new Date()).getTime()+"_"+crisisCode+".csv";
			CSVWriter writer = createNewCsvFile(fileLocation + File.separator + filename);
			List<String> filesToPush = new ArrayList<String>(); 

			for (Object itemObj : itemsArray) {



				responseObj = (JSONObject) parser.parse(itemObj.toString());
				JSONArray labelDataArray = (JSONArray) responseObj.get("labelData");
				if(!isMicroMappersTagged(labelDataArray)) {

					csvRecord = new String[5];

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
						csvRecord[4] = responseObj.get("nominalLabelCode").toString();    //Nominal Label Code
					}

					if(count++>=recordsCount){
						writer.flush();
						writer.close();
						filesToPush.add(fileLocation + File.separator + filename);
						filename = (new Date()).getTime()+"_"+crisisCode+".csv";
						writer = createNewCsvFile(fileLocation + File.separator + filename);
						writer.writeNext(csvRecord);
						count=1L;
					}
					else{
						writer.writeNext(csvRecord);
					}
				}

			}
			writer.flush();
			writer.close();
			filesToPush.add(fileLocation + File.separator + filename);

			for (String fileName : filesToPush) {
				clientAppSourceService.addExternalDataSourceWithClassifiedData(fileName, crisisId);
			}

		} catch (ParseException e) {
			logger.error("Exception while parsing the json ",e);
			throw new Exception("Exception while parsing the json ");
		}

	}

	private boolean isMicroMappersTagged(JSONArray labelDataArray){

		if(labelDataArray.size() < 1)
		{
			return true;
		}
		JSONObject obj = (JSONObject)labelDataArray.get(0);
		JSONObject idDto = (JSONObject)obj.get("idDTO") ;

		long userId = (long)idDto.get("userId")  ;
		if(idDto.get("userId")== 6){
			return true;
		}

		return false;
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
