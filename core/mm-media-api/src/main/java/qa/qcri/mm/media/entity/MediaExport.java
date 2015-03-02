package qa.qcri.mm.media.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 2/22/15
 * Time: 10:39 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "mm_media",name = "media_export")
public class MediaExport implements Serializable {

    private static final long serialVersionUID = -5527566248002296042L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "export_url",nullable = false)
    private String exportURL;


    @Column(name = "file_type",nullable = false)
    private String fileType;


    @Column(name = "status",nullable = false)
    private Date updated;

    public MediaExport() {
    }

    public MediaExport(String exportURL, String fileType) {
        this.exportURL = exportURL;
        this.fileType = fileType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExportURL() {
        return exportURL;
    }

    public void setExportURL(String exportURL) {
        this.exportURL = exportURL;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
