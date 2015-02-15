package qa.qcri.mm.trainer.pybossa.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "taskRun")
public class TaskRun  implements Serializable {

    private static final long serialVersionUID = -5527566248002296042L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "task_id", nullable = false)
    private Long task_id;

    @Column(name = "user_id", nullable = false)
    private Long user_id;
    //duplicateInfo

    @Lob
    @Column(name = "duplicateInfo", length = 100000 )
    private String duplicateInfo;


    @Lob
    @Column(name = "info", length = 100000 )
    private String info;

    @Lob
    @Column(name = "updateInfo", length = 100000 )
    private String updateInfo;


    @Column(name = "user_ip", nullable = false)
    private String user_ip;

    @Column(name = "created", nullable = false)
    private Date created;

    public TaskRun() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTask_id() {
        return task_id;
    }

    public void setTask_id(Long task_id) {
        this.task_id = task_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getUpdateInfo() {
        return updateInfo;
    }

    public void setUpdateInfo(String updateInfo) {
        this.updateInfo = updateInfo;
    }

    public String getUser_ip() {
        return user_ip;
    }

    public void setUser_ip(String user_ip) {
        this.user_ip = user_ip;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getDuplicateInfo() {
        return duplicateInfo;
    }

    public void setDuplicateInfo(String duplicateInfo) {
        this.duplicateInfo = duplicateInfo;
    }
}
