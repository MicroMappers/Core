package qa.qcri.mm.trainer.pybossa.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jilucas
 * Date: 9/25/13
 * Time: 7:22 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "taskQueue")
public class TaskQueue implements Serializable {
    private static final long serialVersionUID = -5527566248002296042L;

    @Id
    @GeneratedValue
    @Column(name = "taskQueueID")
    private Long taskQueueID;

    @Column (name = "taskID", nullable = false)
    private Long taskID;

    @Column (name = "clientAppID", nullable = false)
    private Long clientAppID;

    @Column (name = "documentID", nullable = true)
    private Long documentID;

    @Column (name = "clientAppSourceID", nullable = true)
    private Long clientAppSourceID;

    @Column (name = "status", nullable = false)
    private Integer status;

    @Column (name = "created", nullable = true)
    private Date created;

    @Column (name = "updated", nullable = true)
    private Date updated;

    public TaskQueue(){}

    public TaskQueue(Long taskID, Long clientAppID, Long documentID, int status){
        this.taskID = taskID;
        this.clientAppID = clientAppID;
        this.documentID = documentID;
        this.status = status;
    }

    public TaskQueue(Long clientAppID,int status){
        this.clientAppID = clientAppID;
        this.status = status;
    }

    public Long getTaskQueueID() {
        return taskQueueID;
    }

    public void setTaskQueueID(Long taskQueueID) {
        this.taskQueueID = taskQueueID;
    }

    public Long getTaskID() {
        return taskID;
    }

    public void setTaskID(Long taskID) {
        this.taskID = taskID;
    }

    public Long getClientAppID() {
        return clientAppID;
    }

    public void setClientAppID(Long clientAppID) {
        this.clientAppID = clientAppID;
    }

    public Long getDocumentID() {
        return documentID;
    }

    public void setDocumentID(Long documentID) {
        this.documentID = documentID;
    }

    public Long getClientAppSourceID() {
        return clientAppSourceID;
    }

    public void setClientAppSourceID(Long clientAppSourceID) {
        this.clientAppSourceID = clientAppSourceID;
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

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

}
