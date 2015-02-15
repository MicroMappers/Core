package qa.qcri.mm.drone.api.service;

import mock.DroneTrackerMock;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 9/4/14
 * Time: 1:39 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml", "classpath:spring/hibernateContext.xml"})
public class DroneTrackerServiceTest {

    @Autowired
    private DroneTrackerService droneTrackerService;


    public void testUpdateUserMappingRequest() throws Exception {
        int iValue = droneTrackerService.updateUserMappingRequest(DroneTrackerMock.getExistingDroneTrackerMockData());
        System.out.println("iValue : " + iValue);
    }

   // @Test
    public void testDeleteUserMappingRequest() throws Exception {
        String email = "jikimlucas@gmail.com";
        long id = 1;

        int iValue = droneTrackerService.deleteUserMappingRequest(email, id);

    }

}
