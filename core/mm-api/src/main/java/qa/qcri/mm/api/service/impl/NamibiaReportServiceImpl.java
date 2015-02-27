package qa.qcri.mm.api.service.impl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qa.qcri.mm.api.dao.FilteredTaskRunDao;
import qa.qcri.mm.api.dao.NamibiaReportDao;
import qa.qcri.mm.api.entity.FilteredTaskRun;
import qa.qcri.mm.api.entity.NamibiaReport;
import qa.qcri.mm.api.service.NamibiaReportService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 9/29/14
 * Time: 10:01 AM
 * To change this template use File | Settings | File Templates.
 */
@Service("namibiaReportService")
@Transactional(readOnly = true)
public class NamibiaReportServiceImpl implements NamibiaReportService {

    @Autowired
    NamibiaReportDao namibiaReportDao;

    @Autowired
    FilteredTaskRunDao filteredTaskRunDao;

    @Override
    public List<NamibiaReport> getAllProcessedDataSetForReport() {
        return namibiaReportDao.getAllProcessedNamibiaReport();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<NamibiaReport> getSummerydDataSetForReport() {
        //List<NamibiaReport> dataSet = namibiaReportDao.getAllProcessedNamibiaReport() ;
        //return namibiaReportDao.getAllProcessedNamibiaReport();  //To change body of implemented methods use File | Settings | File Templates.
        List<NamibiaReport> reportList =    namibiaReportDao.getAllProcessedNamibiaReportSummery();
        return namibiaReportDao.getAllProcessedNamibiaReportSummery();
    }

    @Override
    public JSONArray getJSONSummerydDataSetForReport() {
        //List<NamibiaReport> dataSet = namibiaReportDao.getAllProcessedNamibiaReport() ;
        //return namibiaReportDao.getAllProcessedNamibiaReport();  //To change body of implemented methods use File | Settings | File Templates.
        List<NamibiaReport> reportList =    namibiaReportDao.getAllProcessedNamibiaReportSummery();
        JSONArray jsonArray = new JSONArray();
        for(int i= 0; i < reportList.size(); i++){
            if(reportList.get(i).getFoundCount() >=3){
                JSONObject a = new JSONObject();
                a.put("taskID", reportList.get(i).getTask_id());
                a.put("sourceImage", reportList.get(i).getSourceImage());
                a.put("slicedImage", reportList.get(i).getSlicedImage());
                a.put("found", reportList.get(i).getFoundCount());
                a.put("notFound", reportList.get(i).getNoFoundCount());

                jsonArray.add(a);
            }
        }
        return jsonArray;
    }

    @Override
    public JSONArray getJSONDataSetBySource(String imageSource) {
        List<NamibiaReport> reportList =    namibiaReportDao.getNamibiaReportByImageName(imageSource);
        JSONArray jsonArray = new JSONArray();
        for(int i= 0; i < reportList.size(); i++){
            JSONObject a = new JSONObject();
            a.put("taskID", reportList.get(i).getTask_id());
            a.put("sourceImage", reportList.get(i).getSourceImage());
            a.put("slicedImage", reportList.get(i).getSlicedImage());
            a.put("geo", reportList.get(i).getGeo());
            a.put("found", reportList.get(i).getFoundCount());
            a.put("notFound", reportList.get(i).getNoFoundCount());

           jsonArray.add(a);
        }

        return jsonArray;
    }

    @Override
    public List<FilteredTaskRun> getFilteredTaskRunByTask(long taskID) {
        return filteredTaskRunDao.getTaskRunByTaskID(taskID);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
