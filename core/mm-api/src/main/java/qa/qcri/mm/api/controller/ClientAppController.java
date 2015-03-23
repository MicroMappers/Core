package qa.qcri.mm.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qa.qcri.mm.api.entity.ClientApp;
import qa.qcri.mm.api.service.ClientAppService;
import qa.qcri.mm.api.store.CodeLookUp;
import qa.qcri.mm.api.store.StatusCodeType;
import qa.qcri.mm.api.template.ClientAppModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 2/21/14
 * Time: 1:44 AM
 * To change this template use File | Settings | File Templates.
 */
@Path("/clientapp")
@Component
public class ClientAppController {
    @Autowired
    private ClientAppService clientAppService;



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/allactive")
    public List<ClientAppModel> getAllActive(){
        return clientAppService.getAllActiveClientApps();
    }




}
