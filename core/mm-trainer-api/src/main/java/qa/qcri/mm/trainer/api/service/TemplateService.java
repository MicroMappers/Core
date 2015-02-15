package qa.qcri.mm.trainer.api.service;

import qa.qcri.mm.trainer.api.template.CrisisApplicationListModel;
import qa.qcri.mm.trainer.api.template.CrisisLandingHtmlModel;
import qa.qcri.mm.trainer.api.template.CrisisLandingStatusModel;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 10/27/13
 * Time: 3:10 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TemplateService {

    List<CrisisApplicationListModel> getApplicationListHtmlByCrisisID(Long crisisID);

}
