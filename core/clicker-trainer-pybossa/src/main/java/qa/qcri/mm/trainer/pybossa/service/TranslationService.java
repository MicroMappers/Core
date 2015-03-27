package qa.qcri.mm.trainer.pybossa.service;


import java.util.List;

import qa.qcri.mm.trainer.pybossa.entity.TaskTranslation;
import qa.qcri.mm.trainer.pybossa.format.impl.TranslationProjectModel;
import qa.qcri.mm.trainer.pybossa.format.impl.TranslationRequestModel;

/**
 * Created by kamal on 3/22/15.
 */
public interface TranslationService {
    public String pushTranslationRequest(TranslationRequestModel request);
    public String pullTranslationResponse();
    public List<TranslationProjectModel> pullTranslationProjects(String clientId);
    public String pullTranslationProjectsAsString(String clientId);
    
    public void createTranslation(TaskTranslation translation);
    public void updateTranslation(TaskTranslation translation);
	public TaskTranslation findById(Long translationId);
	public void delete(TaskTranslation translation);
	public List<TaskTranslation> findAllTranslations();
}
