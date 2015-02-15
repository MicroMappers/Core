package qa.qcri.mm.trainer.pybossa.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 9/28/14
 * Time: 1:02 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "namibiaReportReprocess")
public class NamibiaReport  implements Serializable {

    private static final long serialVersionUID = -5527566248002296042L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "task_id", nullable = false)
    private Long task_id;

    @Lob
    @Column(name = "geo", length = 100000 )
    private String geo;

    @Column(name = "sourceImage", nullable = false)
    private String sourceImage;

    @Column(name = "slicedImage", nullable = false)
    private String slicedImage;

    @Column(name = "foundCount", nullable = false)
    private Integer foundCount;

    @Column(name = "noFoundCount", nullable = false)
    private Integer noFoundCount;

    public NamibiaReport(Long task_id, String geo, String sourceImage, String slicedImage, Integer foundCount, Integer noFoundCount) {
        this.task_id = task_id;
        this.geo = geo;
        this.sourceImage = sourceImage;
        this.slicedImage = slicedImage;
        this.foundCount = foundCount;
        this.noFoundCount = noFoundCount;
    }

    public NamibiaReport() {
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getGeo() {
        return geo;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }

    public String getSourceImage() {
        return sourceImage;
    }

    public void setSourceImage(String sourceImage) {
        this.sourceImage = sourceImage;
    }

    public String getSlicedImage() {
        return slicedImage;
    }

    public void setSlicedImage(String slicedImage) {
        this.slicedImage = slicedImage;
    }

    public Integer getFoundCount() {
        return foundCount;
    }

    public void setFoundCount(Integer foundCount) {
        this.foundCount = foundCount;
    }

    public Integer getNoFoundCount() {
        return noFoundCount;
    }

    public void setNoFoundCount(Integer noFoundCount) {
        this.noFoundCount = noFoundCount;
    }
}
