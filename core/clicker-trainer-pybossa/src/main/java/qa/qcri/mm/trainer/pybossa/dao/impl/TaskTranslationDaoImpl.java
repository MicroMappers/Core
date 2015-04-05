package qa.qcri.mm.trainer.pybossa.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import qa.qcri.mm.trainer.pybossa.dao.TaskTranslationDao;
import qa.qcri.mm.trainer.pybossa.entity.TaskTranslation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author dan landy
 *
 */
@Repository
public class TaskTranslationDaoImpl extends AbstractDaoImpl<TaskTranslation, String> implements TaskTranslationDao {

    protected TaskTranslationDaoImpl(){
        super(TaskTranslation.class);
    }

	@Override
	public TaskTranslation findTranslationByID(Long translationId) {
		TaskTranslation translation = findByCriterionID(Restrictions.eq("translationId", translationId));
        return translation;  
	}


    public TaskTranslation findTranslationByTaskID(Long taskId) {
        TaskTranslation translation = findByCriterionID(Restrictions.eq("taskId", taskId));
        return translation;
    }

    public List<TaskTranslation> findAllTranslationsByClientAppIdAndStatus(Long clientAppId, String status, Integer count) {
        Map map = new HashMap();
        map.put("clientAppId", clientAppId.toString());
        map.put("status", status);
        List<TaskTranslation> list = findByCriteria(Restrictions.allEq(map), count);
        return list;
    }


}
