package qa.qcri.mm.media.dao;

import qa.qcri.mm.media.entity.MediaPool;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 2/22/15
 * Time: 11:43 AM
 * To change this template use File | Settings | File Templates.
 */
public interface MediaPoolDao extends AbstractDao<MediaPool, String>  {

    public void addToPool(MediaPool mediaPool);
    public List<MediaPool> getMediaPoolBySourceAndStatus(Long sourceID, Integer status);
}
