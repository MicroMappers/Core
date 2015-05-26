package qa.qcri.mm.api.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 10/17/14
 * Time: 5:13 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "FilteredTaskRun")
public class FilteredTaskRun implements Serializable {

    private static final long serialVersionUID = -5527566248002296042L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "created", nullable = false)
    private Date created;

    @Column(name = "app_id", nullable = false)
    private Long app_id;

    @Column(name = "task_id", nullable = false)
    private Long task_id;

    @Column(name = "user_id", nullable = false)
    private Long user_id;

    @Column(name = "user_ip", nullable = false)
    private String user_ip;

    @Column(name = "finish_time", nullable = false)
    private String finish_time;

    @Column(name = "timeout", nullable = false)
    private Long timeout;

    @Column(name = "calibration", nullable = false)
    private Long calibration;

    @Column(name = "info", nullable = false)
    private String info;


    public FilteredTaskRun() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Long getApp_id() {
        return app_id;
    }

    public void setApp_id(Long app_id) {
        this.app_id = app_id;
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

    public String getUser_ip() {
        return user_ip;
    }

    public void setUser_ip(String user_ip) {
        this.user_ip = user_ip;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public Long getCalibration() {
        return calibration;
    }

    public void setCalibration(Long calibration) {
        this.calibration = calibration;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
