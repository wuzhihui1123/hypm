package cn.ifactory.hypm.vo;

import cn.ifactory.hypm.entity.Node;
import cn.ifactory.hypm.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


/**
 * 树形节点（业务数据：如产品、项目...)
 * @author yaha
 *
 */
public class TreeNode {
	
	
	public TreeNode(Node node) {
		this.id = node.getId();
		this.name = node.getName();
		this.pId = node.getParent() == null ? null : node.getParent().getId();
		this.descp = node.getDescp();
		this.startTime = node.getStartTime();
		this.endTime = node.getEndTime();
		try{
			for(User user : node.getUsers()) {
				userIds.add(user.getId());
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private String name;
	
	private String id;
	
	private String pId;
	
	private String descp;
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date startTime;
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date endTime; 
	
	private List<String> userIds = new ArrayList<String>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
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
	/**
	 * 到计划结束时间的剩余天数
	 * @return
	 */
	public long getFreeDay() {
		long ret = -1;
		if(getEndTime() != null) {
			long spaceTime = getEndTime().getTime() - System.currentTimeMillis();
			ret = spaceTime/(24*60*60*1000) + 1;
		}
		return ret;
	}
	

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * po-->vo 集合转换
	 * @param nodes
	 * @return
	 */
	public static Collection<TreeNode> convert(Collection<Node> nodes) {
		Collection<TreeNode> ret = new ArrayList<TreeNode>();
		for(Node node : nodes) {
			ret.add(new TreeNode(node));
		}
		return ret;
	}

	public String getDescp() {
		return descp;
	}

	public void setDescp(String descp) {
		this.descp = descp;
	}

	public List<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}
	
	
	
	
}
