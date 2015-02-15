package qa.qcri.mm.drone.api.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 7/22/14
 * Time: 4:56 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "aidr_scheduler",name = "droneReport")
public class DroneReport implements Serializable {
    private static final long serialVersionUID = -5527566248002296042L;

    @Id
    @GeneratedValue
    @Column(name = "reportID")
    private Long reportID;

    @Column(name = "droneTrackerID",nullable = false)
    private Long droneTrackerID;

    @Column (name = "comment", nullable = false)
    private String comment;

    @Column (name = "updated", nullable = false)
    private Date updated;

    public DroneReport(){}

    public DroneReport(Long droneTrackerID, String comment){
        this.droneTrackerID = droneTrackerID;
        this.comment = comment;
    }

    public Long getReportID() {
        return reportID;
    }

    public void setReportID(Long reportID) {
        this.reportID = reportID;
    }

    public Long getDroneTrackerID() {
        return droneTrackerID;
    }

    public void setDroneTrackerID(Long droneTrackerID) {
        this.droneTrackerID = droneTrackerID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

}
