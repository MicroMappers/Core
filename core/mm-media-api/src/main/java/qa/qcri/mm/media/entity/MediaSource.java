package qa.qcri.mm.media.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 2/22/15
 * Time: 10:15 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "mm_media",name = "media_source")
public class MediaSource implements Serializable {

    private static final long serialVersionUID = -5527566248002296042L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "type",nullable = false)
    private Integer type;

    @Column(name = "source_url",nullable = false)
    private String sourceURL;

    @Column(name = "partner_id",nullable = false)
    private Long partnerID;

    @Column(name = "status",nullable = false)
    private Integer status;

    @Column(name = "updated",nullable = false)
    private Date updated;

    public MediaSource() {
    }

    public MediaSource(Integer type, String sourceURL, Long partnerID) {
        this.type = type;
        this.sourceURL = sourceURL;
        this.partnerID = partnerID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSourceURL() {
        return sourceURL;
    }

    public void setSourceURL(String sourceURL) {
        this.sourceURL = sourceURL;
    }

    public Long getPartnerID() {
        return partnerID;
    }

    public void setPartnerID(Long partnerID) {
        this.partnerID = partnerID;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
