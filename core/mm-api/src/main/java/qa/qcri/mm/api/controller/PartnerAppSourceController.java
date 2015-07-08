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
import qa.qcri.mm.api.util.DataFormatValidator;


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
			if(data!=null && DataFormatValidator.isValidateJson(data)){
				JSONParser parser = new JSONParser();
				JSONObject config = (JSONObject) parser.parse(data);

				String importURL = (String)config.get("IMPORT_URL");

				Long crisisId = (long)config.get("CRISIS_ID");

				Long recordsCount = config.get("NUMBER_OF_RECORDS_PER_VOLUME") != null ? (long)config.get("NUMBER_OF_RECORDS_PER_VOLUME") : 1500L;

				String crisisCode = (String)config.get("CRISIS_CODE");

				String fileLocation = (String)config.get("FILE_LOCATION");

				if(!StringUtils.isEmpty(importURL)  || crisisId!=null || recordsCount!=null
						|| !StringUtils.isEmpty(crisisCode) || !StringUtils.isEmpty(fileLocation) ){

					partnerAppSourceService.pushAppSource(importURL, crisisId, recordsCount, crisisCode, fileLocation);
				}
				else{
					returnValue = StatusCodeType.RETURN_FAIL;
					logger.error("Properties are unassigned or not defined properly in configuration file: " + config);
				}
			}
		}catch(Exception e){
			returnValue = StatusCodeType.RETURN_FAIL;
			logger.error("Error in pushAppSource : " + e);
		}
		return Response.status(CodeLookUp.APP_REQUEST_SUCCESS).entity(returnValue).build();
	}
}
