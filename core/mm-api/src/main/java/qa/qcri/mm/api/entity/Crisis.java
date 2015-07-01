package qa.qcri.mm.api.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 5/12/15
 * Time: 11:17 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "crisis")
public class Crisis implements Serializable {
    private static final long serialVersionUID = -5527566248002296042L;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;


    @Column (name = "crisisID", nullable = true)
    private Long crisisID;

    @Column (name = "crisisName", nullable = true)
    private String crisisName;

    @Column (name = "clientAppID", nullable = true)
    private Long clientAppID;

    @Column (name = "displayName", nullable = true)
    private String displayName;

    @Column (name = "description", nullable = true)
    private String description;

    @Column (name = "activationStart", nullable = true)
    private Date activationStart;

    @Column (name = "activationEnd", nullable = true)
    private Date activationEnd;

    @Column (name = "clickerType", nullable = true)
    private String clickerType;

    @Column (name = "refreshInMinute", nullable = true)
    private Integer refreshInMinute;

    @OneToOne
    @JoinColumn(name="clientAppID" ,nullable = false, insertable = false, updatable = false)
    private ClientApp clientApp;


    public Crisis() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCrisisID() {
        return crisisID;
    }

    public void setCrisisID(Long crisisID) {
        this.crisisID = crisisID;
    }

    public String getCrisisName() {
        return crisisName;
    }

    public void setCrisisName(String crisisName) {
        this.crisisName = crisisName;
    }

    public Long getClientAppID() {
        return clientAppID;
    }

    public void setClientAppID(Long clientAppID) {
        this.clientAppID = clientAppID;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getActivationStart() {
        return activationStart;
    }

    public void setActivationStart(Date activationStart) {
        this.activationStart = activationStart;
    }

    public Date getActivationEnd() {
        return activationEnd;
    }

    public void setActivationEnd(Date activationEnd) {
        this.activationEnd = activationEnd;
    }

    public String getClickerType() {
        return clickerType;
    }

    public void setClickerType(String clickerType) {
        this.clickerType = clickerType;
    }

    public ClientApp getClientApp() {
        return clientApp;
    }

    public void setClientApp(ClientApp clientApp) {
        this.clientApp = clientApp;
    }

    public Integer getRefreshInMinute() {
        return refreshInMinute;
    }

    public void setRefreshInMinute(Integer refreshInMinute) {
        this.refreshInMinute = refreshInMinute;
    }
}
