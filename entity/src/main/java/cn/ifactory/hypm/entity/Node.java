package cn.ifactory.hypm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 树形节点（业务数据：如产品、项目...)
 * @author yaha
 *
 */
@Entity
@Table(name="hypm_node")
public class Node extends BaseEntity {
	/**
	 * 名称
	 */
	@Column(unique=false)
	private String name;
	/**
	 * 介绍
	 */
	@Column(length=1000)
	private String descp;
	/**
	 * 参与人员
	 */
	@ManyToMany(targetEntity=User.class,fetch = FetchType.EAGER)
	@JoinTable(name="hypm_node_user",joinColumns=@JoinColumn(name="node_id"),inverseJoinColumns=@JoinColumn(name="user_id"))
	private List<User> users = new ArrayList<User>();
	/**
	 * 创建人
	 */
	@OneToOne(targetEntity=User.class,optional=false)
	@JoinColumn(name="create_user_id")
	private User createUser;
	/**
	 * 所属父节点
	 */
	@JsonIgnore
	@ManyToOne(targetEntity=Node.class)
	@JoinColumn(name="parent_id")
	private Node parent;
	
	/**
	 * 是否可用
	 */
	private boolean useable = true;
	
	/**
	 * 计划开始时间
	 */
	@Temporal(TemporalType.DATE)
	private Date startTime;
	/**
	 * 计划结束时间
	 */
	@Temporal(TemporalType.DATE)
	private Date endTime;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDescp() {
		return descp;
	}
	/**
	 * 描述字符长度控制
	 * @return
	 */
	public String getDescpSimple() {
		String ret = descp == null ? "" : descp;
		if(ret.length() > 50) {
			ret = ret.substring(0, 50) + "...";
		}
		return ret;
	}
	public void setDescp(String descp) {
		this.descp = descp;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public User getCreateUser() {
		return createUser;
	}
	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public boolean isUseable() {
		return useable;
	}
	public void setUseable(boolean useable) {
		this.useable = useable;
	}
	
}
