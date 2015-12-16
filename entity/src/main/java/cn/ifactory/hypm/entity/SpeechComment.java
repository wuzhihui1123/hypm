package cn.ifactory.hypm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * 发言的评论
 * @author yahaS
 *
 */
@Entity
@Table(name="hypm_speech_comment")
public class SpeechComment extends BaseEntity {
	/**
	 * 评论人
	 */
	@ManyToOne(targetEntity=User.class,optional=false)
	@JoinColumn(name="user_id")
	@JsonIgnore
	private User user;
	
	/**
	 * 评论内容
	 */
	@Column(length=500,nullable=false)
	private String content;
	/**
	 * 发言所属节点
	 */
	@ManyToOne(targetEntity=Speech.class,optional=false)
	@JoinColumn(name="speech_id")
	@JsonIgnore
	private Speech speech;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Speech getSpeech() {
		return speech;
	}
	public void setSpeech(Speech speech) {
		this.speech = speech;
	}
	
	
	
}
