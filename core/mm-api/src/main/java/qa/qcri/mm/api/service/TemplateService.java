package qa.qcri.mm.api.service;

import qa.qcri.mm.api.template.CrisisApplicationListModel;

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
