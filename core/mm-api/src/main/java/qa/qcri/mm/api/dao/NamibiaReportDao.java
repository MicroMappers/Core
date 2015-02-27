package qa.qcri.mm.api.dao;

import qa.qcri.mm.api.entity.NamibiaReport;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 9/29/14
 * Time: 9:49 AM
 * To change this template use File | Settings | File Templates.
 */
public interface NamibiaReportDao  extends AbstractDao<NamibiaReport, String>  {

    List<NamibiaReport> getAllProcessedNamibiaReport();
    List<NamibiaReport> getAllProcessedNamibiaReportSummery();
    List<NamibiaReport> getNamibiaReportByImageName(String name);
}
