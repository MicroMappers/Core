package qa.qcri.mm.media.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 2/22/15
 * Time: 10:24 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "mm_media",name = "media_pool")
public class MediaPool implements Serializable {

    private static final long serialVersionUID = -5527566248002296042L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "source_id",nullable = false)
    private Long sourceID;

    @Column(name = "pool_url",nullable = false)
    private String poolURL;

    @Column(name = "status",nullable = false)
    private Integer status;

    @Column(name = "export_id",nullable = true)
    private Long exportID;

    @Column(name = "updated",nullable = false)
    private Date updated;

    public MediaPool() {
    }

    public MediaPool(Long sourceID, String poolURL, Integer status) {
        this.sourceID = sourceID;
        this.poolURL = poolURL;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSourceID() {
        return sourceID;
    }

    public void setSourceID(Long sourceID) {
        this.sourceID = sourceID;
    }

    public String getPoolURL() {
        return poolURL;
    }

    public void setPoolURL(String poolURL) {
        this.poolURL = poolURL;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getExportID() {
        return exportID;
    }

    public void setExportID(Long exportID) {
        this.exportID = exportID;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
