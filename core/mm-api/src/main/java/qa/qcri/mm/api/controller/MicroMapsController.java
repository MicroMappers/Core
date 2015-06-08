package qa.qcri.mm.api.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qa.qcri.mm.api.service.GeoService;
import qa.qcri.mm.api.service.MicroMapsService;
import qa.qcri.mm.api.template.CrisisGISModel;
import qa.qcri.mm.api.template.MicroMapsCrisisModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 4/22/15
 * Time: 10:23 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/micromaps")
@Component
public class MicroMapsController {
    protected static Logger logger = Logger.getLogger("MicroMapsController");

    @Autowired
    MicroMapsService microMapsService;

    @Autowired
    GeoService geoService;


    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.WILDCARD})
    @Path("/JSON/crisis")
    public String getAllCrisis() throws Exception {

        return microMapsService.getAllCrisisJSONP().toJSONString();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.WILDCARD})
    @Path("/JSONP/crisis")
    public String getAllCrisisJSONP() throws Exception {

        return "jsonp(" + microMapsService.getAllCrisisJSONP().toJSONString() + ");";
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.WILDCARD})
    @Path("/JSON/text/id/{id}")
    public String getAllTextJSON(@PathParam("id") long id) throws Exception {

        return microMapsService.getGeoClickerByClientApp(id).toJSONString();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.WILDCARD})
    @Path("/JSONP/text/id/{id}")
    public String getAllTextJSONP(@PathParam("id") long id) throws Exception {

        return "jsonp(" + microMapsService.getGeoClickerByClientApp(id).toJSONString() + ");";
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.WILDCARD})
    @Path("/JSON/image/id/{id}")
    public String getAllImageJSON(@PathParam("id") long id) throws Exception {

        return microMapsService.getGeoClickerByClientApp(id).toJSONString();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.WILDCARD})
    @Path("/JSONP/image/id/{id}")
    public String getAllImageJSONP(@PathParam("id") long id) throws Exception {

        return "jsonp(" + microMapsService.getGeoClickerByClientApp(id).toJSONString() + ");";
    }


    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.WILDCARD})
    @Path("/JSON/aerial/id/{id}")
    public String getAllAerialJSON(@PathParam("id") long id) throws Exception {

        return microMapsService.getGeoClickerByClientApp(id).toJSONString();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.WILDCARD})
    @Path("/JSONP/aerial/id/{id}")
    public String getAllAerialJSONP(@PathParam("id") long id) throws Exception {

        return "jsonp(" +microMapsService.getGeoClickerByClientApp(id).toJSONString() + ");";
    }


    @GET
    @Produces({MediaType.APPLICATION_ATOM_XML, MediaType.WILDCARD})
    @Path("/kml/text/id/{id}")
    public String getTextClickerKML(@PathParam("id") long id) throws Exception {

        return microMapsService.generateTextClickerKML(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_ATOM_XML, MediaType.WILDCARD})
    @Path("/kml/image/id/{id}")
    public String getImageClickerKML(@PathParam("id") long id) throws Exception {

        return microMapsService.generateImageClickerKML(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_ATOM_XML, MediaType.WILDCARD})
    @Path("/kml/aerial/id/{id}")
    public String getAerialClickerKML(@PathParam("id") long id) throws Exception {

        return microMapsService.generateAericalClickerKML(id);
    }


}
