package qa.qcri.mm.trainer.pybossa.service;


import qa.qcri.mm.trainer.pybossa.format.impl.TranslationProjectModel;

import java.util.List;

/**
 * Created by kamal on 3/22/15.
 */
public interface TranslationService {
    public void pushTranslationRequest();
    public List<TranslationProjectModel> pullTranslationProjects();
    public String pullTranslationProjectsAsString(String clientId);

}
