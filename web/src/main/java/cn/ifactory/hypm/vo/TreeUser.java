package cn.ifactory.hypm.vo;

import cn.ifactory.hypm.entity.User;

import java.util.ArrayList;
import java.util.Collection;


/**
 * 树形节点--用户
 * @author yaha
 *
 */
public class TreeUser {
	
	public TreeUser(User user) {
		this.id = user.getId();
		this.name = user.getNickname();
		this.pId = "-1";
	}

	private String name;
	
	private String id;
	
	private String pId;
	
	private boolean open = true;
	

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

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
	
	/**
	 * po-->vo 集合转换
	 * @param users
	 * @return
	 */
	public static Collection<TreeUser> convert(Collection<User> users) {
		Collection<TreeUser> ret = new ArrayList<TreeUser>();
		for(User user : users) {
			ret.add(new TreeUser(user));
		}
		return ret;
	}
	
	
}
