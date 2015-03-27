package qa.qcri.mm.trainer.pybossa.entity;

/**
 * Created by kamal on 3/22/15.
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "taskTranslation")
public class TaskTranslation {
	    
    @Id
    @GeneratedValue
    @Column(name = "translationID", unique=true, nullable = false)
    private Long translationId;

    @Column(name = "taskID")
    private Long taskId;
    
    @Column(name = "clientAppID")
    private String clientAppId;

    @Column(name = "twbOrderID")
    private Long twbOrderId;
    
    @Column(name = "originalText")
    private String originalText;

    @Column(name = "translatedText")
    private String translatedText;
    
    @Column(name = "answerCode")
    private String answerCode;

    @Column(name = "status")
    private String status = "NEW";
    

	public Long getTranslationId() {
		return translationId;
	}

	public void setTranslationId(Long translationId) {
		this.translationId = translationId;
	}


	public Long getTwbOrderId() {
		return twbOrderId;
	}

	public void setTwbOrderId(Long twbOrderId) {
		this.twbOrderId = twbOrderId;
	}

	public String getOriginalText() {
		return originalText;
	}

	public void setOriginalText(String originalText) {
		this.originalText = originalText;
	}

	public String getTranslatedText() {
		return translatedText;
	}

	public void setTranslatedText(String translatedText) {
		this.translatedText = translatedText;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getClientAppId() {
		return clientAppId;
	}

	public void setClientAppId(String clientAppId) {
		this.clientAppId = clientAppId;
	}

	public String getAnswerCode() {
		return answerCode;
	}

	public void setAnswerCode(String answerCode) {
		this.answerCode = answerCode;
	}


    
    
}
