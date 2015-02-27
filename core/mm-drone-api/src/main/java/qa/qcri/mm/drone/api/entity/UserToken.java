package qa.qcri.mm.drone.api.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 7/2/14
 * Time: 9:18 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "mm_uaviators",name = "userToken")
public class UserToken implements Serializable {
    private static final long serialVersionUID = -5527566248002296042L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column (name = "token", nullable = false)
    private String token;

    @Column (name = "status", nullable = false)
    private Integer status;

    public UserToken(){}

    public UserToken(String token, Integer status) {
        this.token = token;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
