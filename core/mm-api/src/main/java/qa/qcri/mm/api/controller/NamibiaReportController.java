package qa.qcri.mm.api.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qa.qcri.mm.api.entity.FilteredTaskRun;
import qa.qcri.mm.api.entity.NamibiaReport;
import qa.qcri.mm.api.service.NamibiaReportService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 9/29/14
 * Time: 10:13 AM
 * To change this template use File | Settings | File Templates.
 */
@Path("/namibia")
@Component
public class NamibiaReportController {

    protected static Logger logger = Logger.getLogger("NamibiaReportController");

    @Autowired
    NamibiaReportService namibiaReportService;

    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path("/reports")
    public List<NamibiaReport> getSummeryReport(){
        return namibiaReportService.getSummerydDataSetForReport();

    }

    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path("/jsonp/reports")
    public String getJSONPSummeryReport(){
        return "jsonp(" + namibiaReportService.getJSONSummerydDataSetForReport().toJSONString() + ");";
    }

    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path("/jsonp/image/source/{source}")
    public String getJSONPSourceImageReport(@PathParam("source") String source){
        return "jsonp(" + namibiaReportService.getJSONDataSetBySource(source).toJSONString() + ");";
    }


    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path("/taskrun/{taskID}")
    public List<FilteredTaskRun> getTaskRunResult(@PathParam("taskID") Long taskID){
        return namibiaReportService.getFilteredTaskRunByTask(taskID);

    }


    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path("/test")
    public String getTester(){
        return "{\"status\":\"working\"}";

    }

}
