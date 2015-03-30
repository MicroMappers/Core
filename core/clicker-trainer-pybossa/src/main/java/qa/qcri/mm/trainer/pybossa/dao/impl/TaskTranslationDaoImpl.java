package qa.qcri.mm.trainer.pybossa.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import qa.qcri.mm.trainer.pybossa.dao.TaskTranslationDao;
import qa.qcri.mm.trainer.pybossa.entity.TaskTranslation;

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


}
