package qa.qcri.mm.api.dao.impl;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import qa.qcri.mm.api.dao.NamibiaReportDao;
import qa.qcri.mm.api.entity.NamibiaReport;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 9/29/14
 * Time: 9:52 AM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class NamibiaReportDaoImpl extends AbstractDaoImpl<NamibiaReport,String> implements NamibiaReportDao {


    protected NamibiaReportDaoImpl(){
        super(NamibiaReport.class);
    }

    @Override
    public List<NamibiaReport> getAllProcessedNamibiaReport() {
        return  getAll();
    }

    @Override
    public List<NamibiaReport> getAllProcessedNamibiaReportSummery() {

        Criterion criterion = Restrictions.ge("foundCount", new Integer(0));
        return  findByProjection(Projections.projectionList().add(Projections.property("id"), "id")
                .add(Projections.property("task_id"), "task_id")
                .add(Projections.property("sourceImage"), "sourceImage")
                .add(Projections.property("slicedImage"), "slicedImage")
                .add(Projections.property("foundCount"), "foundCount")
                .add(Projections.property("noFoundCount"), "noFoundCount"), criterion);
    }

    @Override
    public List<NamibiaReport> getNamibiaReportByImageName(String imageFilename) {
        return findByCriteria(Restrictions.eq("sourceImage", imageFilename));  //To change body of implemented methods use File | Settings | File Templates.
    }
}
