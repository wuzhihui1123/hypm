package cn.ifactory.hypm.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 发言
 * @author yaha
 *
 */
@Entity
@Table(name="hypm_speech")
public class Speech extends BaseEntity {
	/**
	 * 发言人
	 */
	@ManyToOne(targetEntity=User.class,optional=false)
	@JoinColumn(name="user_id")
	private User user;
	
	/**
	 * 发言内容
	 */
	@Column(length=10000)
	private String content;
	/**
	 * 发言所属节点
	 */
	@ManyToOne(targetEntity=Node.class,optional=false)
	@JoinColumn(name="node_id")
	private Node node;
	
	/**
	 * 是否可用
	 */
	private boolean useable = true;
	
	/**
	 * 附件
	 */
	@OneToMany(targetEntity=Attachment.class,mappedBy="speech",fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	private List<Attachment> attachs = new ArrayList<Attachment>();
	/**
	 * 发言评论
	 */
	@OneToMany(targetEntity=SpeechComment.class,mappedBy="speech",fetch = FetchType.EAGER)
	@OrderBy("createDate DESC")
	@Fetch(FetchMode.SUBSELECT)
	private List<SpeechComment> comments = new ArrayList<SpeechComment>();

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

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public List<Attachment> getAttachs() {
		return attachs;
	}

	public void setAttachs(List<Attachment> attachs) {
		this.attachs = attachs;
	}

	public boolean isUseable() {
		return useable;
	}

	public void setUseable(boolean useable) {
		this.useable = useable;
	}

	public List<SpeechComment> getComments() {
		return comments;
	}

	public void setComments(List<SpeechComment> comments) {
		this.comments = comments;
	}
	
	
	
}
