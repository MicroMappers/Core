package qa.qcri.mm.trainer.pybossa.service;


import qa.qcri.mm.trainer.pybossa.format.impl.TranslationProjectModel;
import qa.qcri.mm.trainer.pybossa.format.impl.TranslationRequestModel;

import java.util.List;

/**
 * Created by kamal on 3/22/15.
 */
public interface TranslationService {
    public String pushTranslationRequest(TranslationRequestModel request);
    public String pullTranslationResponse();
    public List<TranslationProjectModel> pullTranslationProjects(String clientId);
    public String pullTranslationProjectsAsString(String clientId);

}
