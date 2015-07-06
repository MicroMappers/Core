package qa.qcri.mm.api.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import qa.qcri.mm.api.service.PartnerAppSourceService;
import qa.qcri.mm.api.store.CodeLookUp;
import qa.qcri.mm.api.store.StatusCodeType;


/**
 * @author Kushal
 *
 */
@Path("/partnerAppSource")
@Component
public class PartnerAppSourceController {
	protected static Logger logger = Logger.getLogger("PartnerAppSourceController");

	@Autowired
	PartnerAppSourceService partnerAppSourceService;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/pushAppSource")
	public Response pushAppSource(String data){
		String returnValue = StatusCodeType.RETURN_SUCCESS;
		try {
			JSONParser parser = new JSONParser();
			JSONObject dataObj = (JSONObject) parser.parse(data);
			String config = dataObj.get("config").toString();

			if(config!=null){
				try (InputStream input = new FileInputStream(config);){
					Properties properties = new Properties();
					properties.load(input);

					String importURL = properties.getProperty("IMPORT_URL");
					Long crisisId = Long.valueOf(properties.getProperty("CRISIS_ID"));
					Long userId = Long.valueOf(properties.getProperty("USER_ID"));
					Long recordsCount = Long.valueOf(properties.getProperty("NUMBER_OF_RECORDS_PER_VOLUME")) != null ? 
							Long.valueOf(properties.getProperty("NUMBER_OF_RECORDS_PER_VOLUME")) : 1500L;
					String crisisCode = properties.getProperty("CRISIS_CODE");
					String fileLocation = properties.getProperty("FILE_LOCATION");

					if(!StringUtils.isEmpty(importURL)  || crisisId!=null || userId!=null  || recordsCount!=null 
							|| !StringUtils.isEmpty(crisisCode) || !StringUtils.isEmpty(fileLocation) ){
						partnerAppSourceService.pushAppSource(importURL, crisisId, userId, recordsCount, crisisCode, fileLocation);
					}
					else{
						returnValue = StatusCodeType.RETURN_FAIL;
						logger.error("Properties are unassigned or not defined properly in configuration file: " + config);
					}

				} catch (IOException e) {
					returnValue = StatusCodeType.RETURN_FAIL;
					logger.error("Error in reading config properties file: " + config, e);
				}
			}
		}catch(Exception e){
			returnValue = StatusCodeType.RETURN_FAIL;
			logger.error("Error in pushAppSource : " + e);
		}
		return Response.status(CodeLookUp.APP_REQUEST_SUCCESS).entity(returnValue).build();
	}
}
