package qa.qcri.mm.drone.api.controller;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qa.qcri.mm.drone.api.entity.DroneTracker;
import qa.qcri.mm.drone.api.service.DroneTrackerService;
import qa.qcri.mm.drone.api.store.LookUp;
import qa.qcri.mm.drone.api.util.HTTPResultTemplate;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 6/23/14
 * Time: 6:13 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/web")
@Component
public class DroneTrackerController {
    protected static Logger logger = Logger.getLogger("DroneTrackerController");

    @Autowired
    private DroneTrackerService droneTrackerService;

    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path("/tester")
    public String tester(){

        String returnValue = "{\"test\": \"Hello World\"}";
        return returnValue;

    }

    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path("/pending/drones/{token}")
    public List<DroneTracker> getPendingDrones(@PathParam("token") String token){

        return droneTrackerService.getAllPendingDroneGeoData(token);

    }

    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path("/getdrones")
    public JSONArray getAllDrones(){

        return droneTrackerService.getAllApprovedDroneGeoData();

    }

    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path("/jsonp/getdrones")
    public String getJSONPAllDrones(){
        JSONArray jArrary = droneTrackerService.getAllApprovedDroneGeoData();
        String returnValue = "jsonp(" + jArrary.toJSONString() + ");";
        return returnValue;

    }

    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path("/jsonp/drones/after/{droneID}")
    public String getJSONPDronesAfterID(@PathParam("droneID") Long droneID){
        String returnValue = null;
        //System.out.println(" getJSONPDronesAfterID droneID : " + droneID);
        if(droneID.equals(null)){
            JSONArray jArrary = droneTrackerService.getAllApprovedDroneGeoData();
            returnValue = "jsonp(" + jArrary.toJSONString() + ");";
        }
        else{
            JSONArray jArrary = droneTrackerService.getAllApprovedDroneGeoDataAfterID(droneID);
            returnValue = "jsonp(" + jArrary.toJSONString() + ");";
        }

        return returnValue;

    }

    @POST
    @Produces( MediaType.APPLICATION_JSON )
    @Path("/add")
    public void addNewDrone(String data){
        System.out.println("addNewDrone : " + data);
        droneTrackerService.saveUserMappingRequest(data);
      //  return Response.status(CodeLookUp.APP_REQUEST_SUCCESS).qa.qcri.mm.media.entity(StatusCodeType.POST_COMPLETED).build();

    }

    @POST
    @Produces( MediaType.APPLICATION_JSON )
    @Path("/jsonp/add")
    public void addJSONPNewDrone(String data){
        System.out.println("addJSONPNewDrone : " + data);
        String temp = data.replace("jsonp(","");
        temp.replace(");","");
        System.out.println("addJSONPNewDrone - temp : " + temp);
        droneTrackerService.saveUserMappingRequest(temp);
        //  return Response.status(CodeLookUp.APP_REQUEST_SUCCESS).qa.qcri.mm.media.entity(StatusCodeType.POST_COMPLETED).build();

    }


    @POST
    @Produces( MediaType.APPLICATION_JSON )
    @Path("/update")
    public Response updateDrone(String data){
        System.out.println("updateDrone : " + data);
        int result = droneTrackerService.updateUserMappingRequest(data);
        return Response.status(LookUp.REQUEST_SUCCESS).entity(HTTPResultTemplate.buildStatusResult(result)).build();

    }

    @POST
    @Produces( MediaType.APPLICATION_JSON )
    @Path("/jsonp/update")
    public String updateJSONPNewDrone(String data){
        System.out.println("updateJSONPNewDrone : " + data);

        int result = droneTrackerService.updateUserMappingRequest(data);

        String returnValue = "jsonp(" + HTTPResultTemplate.buildStatusResult(result) + ");";
        return returnValue;
    }

    @POST
    @Produces( MediaType.APPLICATION_JSON )
    @Path("/delete/{id}/{email}")
    public Response deleteDrone(@PathParam("id") long id, @PathParam("email") String email){
        System.out.println("deleteDrone id : " + id);
        System.out.println("deleteDrone email: " + email);
        int result = droneTrackerService.deleteUserMappingRequest(email, id);
        return Response.status(LookUp.REQUEST_SUCCESS).entity(HTTPResultTemplate.buildStatusResult(result)).build();

    }

    @POST
    @Produces( MediaType.APPLICATION_JSON )
    @Path("/jsonp/delete/{id}/{email}")
    public String deleteJSONPNewDrone(@PathParam("id") long id, @PathParam("email") String email){

        System.out.println("deleteDrone id : " + id);
        System.out.println("deleteDrone email: " + email);

        int result = droneTrackerService.deleteUserMappingRequest(email, id);

        String returnValue = "jsonp(" + HTTPResultTemplate.buildStatusResult(result) + ");";
        return returnValue;
    }

}
