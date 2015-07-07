package qa.qcri.mm.api;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import qa.qcri.mm.api.service.ClientAppSourceServiceTest;


/**
 * @author Kushal
 *
 */
@RunWith(Suite.class)
@SuiteClasses({
	ClientAppSourceServiceTest.class	
})

//Test Suite class implemented to run all the test classes in a specified order
public class AllTestSuite {
}
