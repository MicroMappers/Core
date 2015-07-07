package qa.qcri.mm.api.service;


/**
 * @author Kushal
 *
 */
public interface PartnerAppSourceService {

	void pushAppSource(String importURL, Long crisisId, Long recordsCount, String crisisCode, String fileLocation) throws Exception;
}
