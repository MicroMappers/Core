package qa.qcri.mm.trainer.pybossa.dao.impl;

import org.springframework.stereotype.Repository;
import qa.qcri.mm.trainer.pybossa.dao.NamibiaReportDao;
import qa.qcri.mm.trainer.pybossa.entity.NamibiaReport;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 9/28/14
 * Time: 1:22 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class NamibiaReportDaoImpl extends AbstractDaoImpl<NamibiaReport, String> implements NamibiaReportDao {

    protected NamibiaReportDaoImpl(){
        super(NamibiaReport.class);
    }

    @Override
    public List<NamibiaReport> getAllReport() {
        return getAll();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void dataEntry(NamibiaReport rpt) {
       save(rpt);
    }
}
