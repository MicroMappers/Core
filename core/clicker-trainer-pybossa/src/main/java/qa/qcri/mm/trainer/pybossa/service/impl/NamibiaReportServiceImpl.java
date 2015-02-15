package qa.qcri.mm.trainer.pybossa.service.impl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qa.qcri.mm.trainer.pybossa.dao.NamibiaReportDao;
import qa.qcri.mm.trainer.pybossa.dao.TaskRunDao;
import qa.qcri.mm.trainer.pybossa.entity.NamibiaReport;
import qa.qcri.mm.trainer.pybossa.entity.TaskRun;
import qa.qcri.mm.trainer.pybossa.service.NamibiaReportService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 9/28/14
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("namibiaReportService")
@Transactional(readOnly = true)
public class NamibiaReportServiceImpl implements NamibiaReportService {

    @Autowired
    NamibiaReportDao namibiaReportDao;

    @Autowired
    TaskRunDao taskRunDao;


    @Override
    @Transactional(readOnly = false)
    public void createAreport(NamibiaReport rpt) {
        namibiaReportDao.dataEntry(rpt);
    }

    @Override
    public List<NamibiaReport> generateAllReport() {
        return namibiaReportDao.getAllReport();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = false)
    public void cleanUp() {
        JSONParser parser = new JSONParser();
        //List<TaskRun> ids = taskRunDao.getAllUserID();
       // Long[] idArr = new Long[ids.size()];
        //idArr = ids.toArray(idArr);
        //long[] doneArr = {102};
        int finalStep=0;
       // for(int i=0; i< 8;i++){
            //long s = idArr[i]; 12 ,24 ,42
        //long s = 39;
        long s = 74;
            //if(s != 0 && s!=102 && s!=4 && s!=103 && s!=104 && s!=110){
                System.out.println("=========userID: " + s);
                List<TaskRun> userTaskRun =  taskRunDao.getTaskRunByUserID(s);
                if(userTaskRun.size() > 1){

                    cleanUpUserTask(userTaskRun, parser);
                }

            //}
        //}


        //To change body of implemented methods use File | Settings | File Templates.
    }

    private void cleanUpUserTask(List<TaskRun> userTaskRun,JSONParser parser ){
        try{
          //  System.out.println("userTaskRun1 :" + userTaskRun.size());
            for(int i = 0 ; i < userTaskRun.size(); i++) {
                TaskRun currentTask = userTaskRun.get(i);
                JSONObject a =(JSONObject) parser.parse(currentTask.getInfo());
                JSONArray currentLoc = (JSONArray)a.get("loc");
             //   System.out.println("currentLoc1 :" + currentLoc.size());
              //  System.out.println("i :" + i);
                for (Object temp : currentLoc) {
                    JSONObject obj1 = (JSONObject)temp;
                    JSONObject geometry = (JSONObject)obj1.get("geometry");

                    for(int j= i + 1; j <userTaskRun.size(); j++ ){
                     //   System.out.println("j :" + j);
                        TaskRun theOtherTask = userTaskRun.get(j);
                        JSONObject obj2 =(JSONObject) parser.parse(theOtherTask.getInfo());
                        JSONArray theOtherLoc = (JSONArray)obj2.get("loc");
                        this.processJsonArrary(theOtherTask, geometry, theOtherLoc);

                    }
                }
            }
        }
        catch(Exception e){

        }


    }

    private JSONParser cParser = new JSONParser();
    private void processJsonArrary(TaskRun theOtherTask, JSONObject geometry, JSONArray OthertLoc){
        JSONArray removalJsonArr = new JSONArray();

        for (Object temp : OthertLoc) {
            JSONObject obj1 = (JSONObject)temp;
            JSONObject geom = (JSONObject)obj1.get("geometry");

            if(geometry.toJSONString().equals(geom.toJSONString())){
                JSONObject aDeleted = geom;
                removalJsonArr.add(aDeleted);
                obj1.remove("geometry");
                obj1.remove("maxBounds");
                obj1.remove("type");
                obj1.remove("properties");
                obj1.remove("bounds");
            }
        }

       // System.out.println("OthertLoc.toJSONString(): " + OthertLoc.toJSONString());
       // System.out.println("removalJsonArr.toJSONString(): " + removalJsonArr.toJSONString());

        try{

            if( theOtherTask.getDuplicateInfo()!=null)  {
                Object cData = cParser.parse(theOtherTask.getDuplicateInfo());
                JSONArray currentUpdateInfo = (JSONArray) cData;
                if(removalJsonArr.size() > 0){
                    for(Object aJson: removalJsonArr){
                        currentUpdateInfo.add(aJson);
                    }

                    taskRunDao.updateTaskRun(theOtherTask.getId(), OthertLoc.toJSONString(), currentUpdateInfo.toJSONString());

                }
            }
            else{
                taskRunDao.updateTaskRun(theOtherTask.getId(), OthertLoc.toJSONString(), removalJsonArr.toJSONString());
            }

        }
        catch(Exception e){
                System.out.println("exception: " + e);
        }

        //return OthertLoc;

    }




}
