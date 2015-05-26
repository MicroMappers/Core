package qa.qcri.mm.trainer.pybossa.entity;

import qa.qcri.mm.trainer.pybossa.store.StatusCodeType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jilucas
 * Date: 9/18/13
 * Time: 4:11 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "clientApp")
public class ClientApp implements Serializable {
    private static final long serialVersionUID = -5527566248002296042L;

    @Id
    @GeneratedValue
    @Column(name = "clientAppID")
    private Long clientAppID;

    @Column (name = "clientID", nullable = false)
    private Long clientID;

    @Column (name = "crisisID", nullable = false)
    private Long crisisID;

    @Column (name = "nominalAttributeID", nullable = false)
    private Long nominalAttributeID;

    @Column (name = "name", nullable = false)
    private String name;

    @Column (name = "description", nullable = false)
    private String description;

    @Column (name = "platformAppID", nullable = false)
    private Long platformAppID;

    @Column (name = "shortName", nullable = false)
    private String shortName;

    @Column (name = "taskRunsPerTask", nullable = false)
    private Integer taskRunsPerTask;

    @Column (name = "quorum", nullable = false)
    private Integer quorum;

    @Column (name = "iconURL", nullable = true)
    private String iconURL;

    @Column (name = "status", nullable = false)
    private Integer status;

    @Column (name = "created", nullable = true)
    private Date created;

    @Column (name = "appType", nullable = false)
    private Integer appType;

    @Column (name = "isCustom", nullable = false)
    private boolean isCustom;

    @ManyToOne
    @JoinColumn(name="clientID" ,nullable = false, insertable = false, updatable = false)
    private Client client;

    @OneToOne
    @JoinColumn(name="clientAppID" ,nullable = false, insertable = false, updatable = false)
    private Crisis crisis;


    public ClientApp(){}

    public ClientApp(Long clientID, Long crisisID, String name, String description, Long platformAppID, String shortName, Long nominalAttributeID, int taskRunsPerTask, int appType){

        this.clientID = clientID;
        this.crisisID = crisisID;
        this.name = name;
        this.description = description;
        this.platformAppID =  platformAppID;
        this.shortName = shortName;
        this.nominalAttributeID = nominalAttributeID;
        this.quorum = StatusCodeType.MICROMAPPER_ONLY;
        this.status = StatusCodeType.MICROMAPPER_ONLY;
        this.taskRunsPerTask = taskRunsPerTask;
        this.appType = appType;
        this.isCustom = false;
    }

    public ClientApp(Long clientID, Long crisisID, String name, String description, Long platformAppID, String shortName, Long nominalAttributeID, int taskRunsPerTask, int appType, boolean isCustom){

        this.clientID = clientID;
        this.crisisID = crisisID;
        this.name = name;
        this.description = description;
        this.platformAppID =  platformAppID;
        this.shortName = shortName;
        this.nominalAttributeID = nominalAttributeID;
        this.quorum = StatusCodeType.MICROMAPPER_ONLY;
        this.status = StatusCodeType.MICROMAPPER_ONLY;
        this.taskRunsPerTask = taskRunsPerTask;
        this.appType = appType;
        this.isCustom = isCustom;
    }

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Long getClientAppID() {
        return clientAppID;
    }

    public void setClientAppID(Long clientAppID) {
        this.clientAppID = clientAppID;
    }


    public Long getClientID() {
        return clientID;
    }

    public void setClientID(Long clientID) {
        this.clientID = clientID;
    }

    public Long getCrisisID() {
        return crisisID;
    }

    public void setCrisisID(Long crisisID) {
        this.crisisID = crisisID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPlatformAppID() {
        return platformAppID;
    }

    public void setPlatformAppID(Long platformAppID) {
        this.platformAppID = platformAppID;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Integer getTaskRunsPerTask() {
        return taskRunsPerTask;
    }

    public void setTaskRunsPerTask(Integer taskRunsPerTask) {
        this.taskRunsPerTask = taskRunsPerTask;
    }

    public Integer getQuorum() {
        return quorum;
    }

    public void setQuorum(Integer quorum) {
        this.quorum = quorum;
    }

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Long getNominalAttributeID() {
        return nominalAttributeID;
    }

    public void setNominalAttributeID(Long nominalAttributeID) {
        this.nominalAttributeID = nominalAttributeID;
    }

    public boolean getIsCustom() {
        return this.isCustom;
    }

    public void setIsCustom(boolean isCustom) {
        this.isCustom = isCustom;
    }

    public Crisis getCrisis() {
        return crisis;
    }

    public void setCrisis(Crisis crisis) {
        this.crisis = crisis;
    }
}
