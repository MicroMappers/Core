package qa.qcri.mm.trainer.pybossa.service;

import qa.qcri.mm.trainer.pybossa.entity.NamibiaReport;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 9/28/14
 * Time: 1:42 PM
 * To change this template use File | Settings | File Templates.
 */
public interface NamibiaReportService {
    void createAreport(NamibiaReport rpt);
    List<NamibiaReport> generateAllReport();

    void cleanUp();
}
