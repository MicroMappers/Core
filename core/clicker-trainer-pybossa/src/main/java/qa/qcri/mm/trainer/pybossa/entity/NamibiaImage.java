package qa.qcri.mm.trainer.pybossa.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 9/9/14
 * Time: 4:00 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "namibiaImage")
public class NamibiaImage implements Serializable {

    private static final long serialVersionUID = -5527566248002296042L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;


    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "source", nullable = false)
    private String source;

    @Column(name = "gridImage", nullable = false)
    private String gridImage;

    @Column(name = "animalFound", nullable = false)
    private Integer animalFound;


    public NamibiaImage(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGridImage() {
        return gridImage;
    }

    public void setGridImage(String gridImage) {
        this.gridImage = gridImage;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getAnimalFound() {
        return animalFound;
    }

    public void setAnimalFound(Integer animalFound) {
        this.animalFound = animalFound;
    }
}
