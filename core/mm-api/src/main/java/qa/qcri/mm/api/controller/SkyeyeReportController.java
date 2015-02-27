package qa.qcri.mm.api.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qa.qcri.mm.api.entity.TaskQueueResponse;
import qa.qcri.mm.api.service.SkyeyeReportService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 12/13/14
 * Time: 6:40 AM
 * To change this template use File | Settings | File Templates.
 */
@Path("/skyeye")
@Component
public class SkyeyeReportController {


    protected static Logger logger = Logger.getLogger("SkyeyeReportController");

    @Autowired
    SkyeyeReportService skyeyeReportService;

    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path("/reports/{source}")
    public List<TaskQueueResponse> getResponseReport(@PathParam("source") String source){
        System.out.print("source : " + source);
        return skyeyeReportService.getSummerydDataSetForReport(source);

    }

    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path("/jsonp/reports/{source}")
    public String getJSONPSummeryReport(@PathParam("source") String source){

        return "jsonp(" + skyeyeReportService.getJSONSummerydDataSetForReport(source).toJSONString() + ");";
    }


    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path("/test")
    public String getTester(){
        return "{\"status\":\"working\"}";

    }

    @GET
    @Produces( MediaType.APPLICATION_XML )
    @Path("/KML/reports/resources/{source}")
    public String getKMLResources(@PathParam("source") String source){
       return skyeyeReportService.getKMLSummeryDataSetByResources(source);

    }

    @GET
    @Produces( MediaType.APPLICATION_XML )
    @Path("/KML/reports/{source}")
    public String getKMLAll(@PathParam("source") String source){
        return skyeyeReportService.getKMLSummeryDataSetForReport(source);

    }

    @GET
    @Produces( MediaType.APPLICATION_XML )
    @Path("/KML/reports/blue/{source}")
    public String getKMLBlue(@PathParam("source") String source){
        return skyeyeReportService.getKMLSummeryDataSetByLayerType(source, "polyline");

    }

    @GET
    @Produces( MediaType.APPLICATION_XML )
    @Path("/KML/reports/red/{source}")
    public String getKMLRed(@PathParam("source") String source){
        return skyeyeReportService.getKMLSummeryDataSetByLayerType(source, "polyline2");

    }
}
