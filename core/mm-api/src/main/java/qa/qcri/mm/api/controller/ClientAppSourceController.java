package qa.qcri.mm.api.controller;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qa.qcri.mm.api.service.ClientAppSourceService;
import qa.qcri.mm.api.store.CodeLookUp;
import qa.qcri.mm.api.store.StatusCodeType;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 6/12/14
 * Time: 9:28 AM
 * To change this template use File | Settings | File Templates.
 */
@Path("/source")
@Component
public class ClientAppSourceController {

    protected static Logger logger = Logger.getLogger("ClientAppSourceController");

    @Autowired
    private ClientAppSourceService clientAppSourceService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/save")
    public Response saveAppSource(String data){
        String returnValue = StatusCodeType.RETURN_SUCCESS;

        System.out.println("saveAppSource : " + data );

        try{
            JSONParser parser = new JSONParser();
            JSONArray objs = (JSONArray)parser.parse(data);
            for(Object a : objs){
                JSONObject obj = (JSONObject)a;
                String fileURL = (String)obj.get("fileURL");
                Long appID = (Long)obj.get("appID");

                System.out.println("fileURL : " + fileURL );

                System.out.println("appID : " + appID );

                clientAppSourceService.addExternalDataSouceWithClientAppID(fileURL, appID);
            }
        }
        catch(Exception e){
            returnValue = StatusCodeType.RETURN_FAIL;
            logger.error("saveAppSource got exception : " + e);
            System.out.println("saveAppSource excpetion : " + e );
        }

        return Response.status(CodeLookUp.APP_REQUEST_SUCCESS).entity(returnValue).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/aidr/push")
    public Response saveAIDRSource(String data){
        String returnValue = StatusCodeType.RETURN_SUCCESS;

        System.out.println("saveAppSource : " + data );

        try{
            JSONParser parser = new JSONParser();
            JSONArray objs = (JSONArray)parser.parse(data);
            for(Object a : objs){
                JSONObject obj = (JSONObject)a;
                String fileURL = (String)obj.get("fileURL");
                Long platformID = (Long)obj.get("platformID");
                System.out.println("fileURL : " + fileURL );

                clientAppSourceService.addExternalDataSourceWithClassifiedData(fileURL, platformID);
            }
        }
        catch(Exception e){
            returnValue = StatusCodeType.RETURN_FAIL;
            logger.error("saveAppSource got exception : " + e);
            System.out.println("saveAppSource excpetion : " + e );
        }

        return Response.status(CodeLookUp.APP_REQUEST_SUCCESS).entity(returnValue).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/mapbox/push")
    public Response saveMapBoxSource(String data){
        String returnValue = StatusCodeType.RETURN_SUCCESS;

        System.out.println("saveMapBoxSource : " + data );

        try{
            clientAppSourceService.handleMapBoxDataSource(data);
        }
        catch(Exception e){
            returnValue = StatusCodeType.RETURN_FAIL;
            logger.error("saveAppSource got exception : " + e);
            System.out.println("saveAppSource excpetion : " + e );
        }

        return Response.status(CodeLookUp.APP_REQUEST_SUCCESS).entity(returnValue).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/gist/push")
    public Response saveMapBoxGist(String data){

        String returnValue = StatusCodeType.RETURN_SUCCESS;
        System.out.println("saveMapBoxGist : " + data );

        try{
            clientAppSourceService.handleMapBoxGistDataSource(data);
        }
        catch(Exception e){
            returnValue = StatusCodeType.RETURN_FAIL;
            logger.error("saveAppSource got exception : " + e);
            System.out.println("saveAppSource excpetion : " + e );
        }

        return Response.status(CodeLookUp.APP_REQUEST_SUCCESS).entity(returnValue).build();
    }

}