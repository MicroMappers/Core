package qa.qcri.mm.media.dao;

import qa.qcri.mm.media.entity.MediaSource;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 2/22/15
 * Time: 11:59 AM
 * To change this template use File | Settings | File Templates.
 */
public interface MediaSourceDao extends AbstractDao<MediaSource, String>  {

    public List<MediaSource> getSourceByStatusAndPartner(Long partnerID, Integer status, Integer mediaType);
}
