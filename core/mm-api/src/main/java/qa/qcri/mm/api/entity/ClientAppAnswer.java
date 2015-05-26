package qa.qcri.mm.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.lang.String;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 10/20/13
 * Time: 1:10 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "clientAppAnswer")
public class ClientAppAnswer implements Serializable {
    private static final long serialVersionUID = -5527566248002296042L;

    @Id
    @Column(name = "clientAppID")
    private Long clientAppID;

    @Column (name = "answer", nullable = false)
    private String answer;

    @Column (name = "activeAnswerKey", nullable = true)
    private String activeAnswerKey;

    @Column (name = "answerMarkerInfo", nullable = true)
    private String answerMarkerInfo;

    @Column (name = "created", nullable = false)
    private Date created;

    public String getActiveAnswerKey() {
        return activeAnswerKey;
    }

    public void setActiveAnswerKey(String activeAnswerKey) {
        this.activeAnswerKey = activeAnswerKey;
    }

    public ClientAppAnswer(){}

    public ClientAppAnswer(Long clientAppID, String answer){
        this.clientAppID = clientAppID;
        this.answer = answer;

    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Long getClientAppID() {
        return clientAppID;
    }

    public void setClientAppID(Long clientAppID) {
        this.clientAppID = clientAppID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerMarkerInfo() {
        return answerMarkerInfo;
    }

    public void setAnswerMarkerInfo(String answerMarkerInfo) {
        this.answerMarkerInfo = answerMarkerInfo;
    }
}
