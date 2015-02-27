package qa.qcri.mm.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qa.qcri.mm.api.entity.Client;
import qa.qcri.mm.api.entity.ClientApp;

import qa.qcri.mm.api.service.*;
import qa.qcri.mm.api.store.StatusCodeType;
import qa.qcri.mm.api.template.CrisisApplicationListFormatter;
import qa.qcri.mm.api.template.CrisisApplicationListModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 10/27/13
 * Time: 3:12 PM
 * To change this template use File | Settings | File Templates.
 */

@Service("templateService")
@Transactional(readOnly = false)
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientAppService clientAppService;

    @Autowired
    private TaskQueueService taskQueueService;


    @Override
    public List<CrisisApplicationListModel> getApplicationListHtmlByCrisisID(Long cririsID) {


        Client client;
        List<CrisisApplicationListModel> applicationListModelList = new ArrayList<CrisisApplicationListModel>();
        List<ClientApp> clientAppList = clientAppService.getAllClientAppByCrisisID(cririsID);
        if(clientAppList != null){
            if(clientAppList.size() > 0){
                client = clientService.findClientbyID("clientID", clientAppList.get(0).getClientID());
                for(int i=0; i < clientAppList.size(); i++){
                    ClientApp clientApp = clientAppList.get(i);

                    if(!clientApp.getStatus().equals(StatusCodeType.CLIENT_APP_INACTIVE) && !clientApp.getStatus().equals(StatusCodeType.CLIENT_APP_DISABLED)){

                        CrisisApplicationListFormatter formatter = new CrisisApplicationListFormatter(clientApp,client,taskQueueService) ;
                        String url = formatter.getURLLink();
                        Integer remaining = formatter.getRemaining();
                        Integer totalCount = formatter.getTotalTaskNumber() - formatter.getRemaining();
                        if(totalCount < 0){
                            totalCount = 0;
                        }
                        String attNameValue = clientApp.getName();
                        String[] array = attNameValue.split("\\:");
                        String attName =null;

                        if(array.length > 1){
                            attName = array[1];
                        }

                        applicationListModelList.add(new CrisisApplicationListModel(clientApp.getNominalAttributeID(),attName.trim(),url,remaining, totalCount));
                    }

                }

            }
        }

        return applicationListModelList;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
