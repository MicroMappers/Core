package qa.qcri.mm.trainer.pybossa.dao;

import qa.qcri.mm.trainer.pybossa.entity.TaskTranslation;

/**
 * 
 * @author dan landy
 *
 */
public interface TaskTranslationDao extends AbstractDao<TaskTranslation, String>  {

	TaskTranslation findTranslationByID(Long translationId);
    TaskTranslation findTranslationByTaskID(Long taskId);
}
