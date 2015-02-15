package qa.qcri.mm.trainer.pybossa.dao;

import qa.qcri.mm.trainer.pybossa.entity.NamibiaReport;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 9/28/14
 * Time: 1:15 PM
 * To change this template use File | Settings | File Templates.
 */
public interface NamibiaReportDao extends AbstractDao<NamibiaReport, String> {
    List<NamibiaReport> getAllReport();
    void dataEntry(NamibiaReport rpt);
}
