package qa.qcri.mm.media.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 2/21/15
 * Time: 11:21 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "mm_media",name = "partner")
public class Partner implements Serializable {
    private static final long serialVersionUID = -5527566248002296042L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long partnerID;

    @Column(name = "name",nullable = false)
    private String name;

    @Column (name = "status", nullable = false)
    private Integer status;

    @Column (name = "created", nullable = false)
    private Date created;

    public Partner() {
    }

    public Long getPartnerID() {
        return partnerID;
    }

    public void setPartnerID(Long partnerID) {
        this.partnerID = partnerID;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
