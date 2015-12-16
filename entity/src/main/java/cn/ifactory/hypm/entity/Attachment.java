package cn.ifactory.hypm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 附件
 * @author yaha
 *
 */
@Entity
public class Attachment extends BaseEntity {
	/**
	 * 附件所属发言
	 */
	@ManyToOne(targetEntity=Speech.class,optional=false)
	@JoinColumn(name="speech_id")
	private Speech speech;
	@Column(nullable=false)
	private String name;
	

	public Speech getSpeech() {
		return speech;
	}

	public void setSpeech(Speech speech) {
		this.speech = speech;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
