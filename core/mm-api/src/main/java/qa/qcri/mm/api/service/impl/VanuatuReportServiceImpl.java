package qa.qcri.mm.api.service.impl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qa.qcri.mm.api.dao.PamReportDao;
import qa.qcri.mm.api.entity.PamReport;
import qa.qcri.mm.api.entity.TaskQueueResponse;
import qa.qcri.mm.api.service.SkyeyeReportService;
import qa.qcri.mm.api.service.TaskQueueService;
import qa.qcri.mm.api.service.VanuatuReportService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 12/13/14
 * Time: 7:04 AM
 * To change this template use File | Settings | File Templates.
 */
@Service("vanuatuReportService")
@Transactional(readOnly = true)
public class VanuatuReportServiceImpl implements VanuatuReportService {

    private JSONParser parser = new JSONParser();

    @Autowired
    TaskQueueService taskQueueService;

    @Autowired
    PamReportDao pamReportDao;


    @Override
    public List<PamReport> getAllProcessedPamReport() {
        return pamReportDao.getAllProcessedPamReport();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PamReport getPamRecordByID(long id) {
        List<PamReport> report = pamReportDao.getPamReportByID(id) ;

        if(report.size() > 0){
            return report.get(0);
        }

        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
