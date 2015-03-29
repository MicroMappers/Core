package qa.qcri.mm.trainer.pybossa.service;


import java.util.List;
import java.util.Map;

import qa.qcri.mm.trainer.pybossa.entity.ClientApp;
import qa.qcri.mm.trainer.pybossa.entity.TaskTranslation;
import qa.qcri.mm.trainer.pybossa.format.impl.TranslationProjectModel;
import qa.qcri.mm.trainer.pybossa.format.impl.TranslationRequestModel;

/**
 * Created by kamal on 3/22/15.
 */
public interface TranslationService {
    public Map pushTranslationRequest(TranslationRequestModel request);

    public Map processTranslations(ClientApp clientApp);

    public Map pushDocumentForRequest(TranslationRequestModel request);

    public String pullTranslationResponse();
    public List<TranslationProjectModel> pullTranslationProjects(String clientId);
    public String pullTranslationProjectsAsString(String clientId);
    
    public void createTranslation(TaskTranslation translation);
    public void updateTranslation(TaskTranslation translation);
	public TaskTranslation findById(Long translationId);
	public void delete(TaskTranslation translation);
	public List<TaskTranslation> findAllTranslations();
    public List<TaskTranslation> findAllTranslationsByClientAppIdAndStatus(Long clientAppId, String status);
}
