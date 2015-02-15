package qa.qcri.mm.trainer.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qa.qcri.mm.trainer.api.service.TemplateService;
import qa.qcri.mm.trainer.api.template.CrisisApplicationListModel;
import qa.qcri.mm.trainer.api.template.CrisisLandingHtmlModel;
import qa.qcri.mm.trainer.api.template.CrisisLandingStatusModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 10/27/13
 * Time: 3:05 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/template")
@Component
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path("/app/id/{crisisid}")
    public List<CrisisApplicationListModel> getCrisisByID(@PathParam("crisisid") Long crisisid){

       return templateService.getApplicationListHtmlByCrisisID(crisisid);
    }


}
