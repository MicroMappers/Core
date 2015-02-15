package qa.qcri.mm.drone.api.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qa.qcri.mm.drone.api.service.DroneReportService;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 7/22/14
 * Time: 6:31 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/report")
@Component
public class DroneReportController {
    protected static Logger logger = Logger.getLogger("DroneReportController");

    @Autowired
    DroneReportService droneReportService;

    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path("/tester")
    public String tester(){

        String returnValue = "{\"test\": \"Hello World\"}";
        return returnValue;

    }


    @POST
    @Produces( MediaType.APPLICATION_JSON )
    @Path("/post")
    public void addNewUserVote(String data){
        logger.info(String.format("addNewUserVote data to read \"%s\".", data)) ;
        //LOG.debug(new LogMessage(String.format("Failed to read \"%s\". Substituting new empty file.", path), ex));

        droneReportService.saveUserVote(data);

    }
}
