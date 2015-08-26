package qa.qcri.mm.trainer.pybossa.service;

import qa.qcri.mm.trainer.pybossa.format.impl.GeoJsonOutputModel;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 11/22/13
 * Time: 2:28 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ReportProductService {

    void generateReportTemplateFromExternalSource() throws Exception;
    void generateCVSReportForGeoClicker() throws Exception;
    void generateGeoJsonForESRI(List<GeoJsonOutputModel> geoJsonOutputModels) throws Exception;
    void generateMapBoxTemplateForAerialClicker() throws Exception;
    void generateGeoJsonForClientApp(Long clientAppID) throws Exception;
}
