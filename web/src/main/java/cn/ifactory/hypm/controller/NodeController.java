package cn.ifactory.hypm.controller;

import cn.ifactory.hypm.entity.Log;
import cn.ifactory.hypm.entity.Node;
import cn.ifactory.hypm.entity.User;
import cn.ifactory.hypm.facade.service.LogService;
import cn.ifactory.hypm.facade.service.NodeService;
import cn.ifactory.hypm.facade.service.SpeechService;
import cn.ifactory.hypm.facade.service.UserService;
import cn.ifactory.hypm.utils.ConstantData;
import cn.ifactory.hypm.utils.JsonMapper;
import cn.ifactory.hypm.vo.TreeNode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class NodeController  {
	private static final Logger logger = LoggerFactory.getLogger(NodeController.class);
	@Resource(name="nodeService")
	private NodeService nodeService;
	@Resource(name="userService")
	private UserService userService;
	@Resource(name="speechService")
	private SpeechService speechService;
	@Resource(name="logService")
	private LogService logService;
	@RequestMapping("/node/input.jhtml")
	@ResponseBody
	public String input(Node node,String parentId,String userIds,String startTimeStr,String endTimeStr,HttpSession session) {
		User currUser = (User)session.getAttribute(ConstantData.SESSION_USER);
		node.setCreateUser(currUser);
		//参与人员
		if(StringUtils.isNotBlank(userIds)) {
			List<User> users = new ArrayList<User>();
			String ids[] = userIds.split(",");
			for(String uid : ids) {
				User u = userService.get(uid);
				if(u != null) users.add(u);
			}
			node.setUsers(users);
		}
		//计划开始时间与计划结束时间(可改造为spring转换器来实现String-->Date的转换)
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if(StringUtils.isNotBlank(startTimeStr)) {
			try {
				node.setStartTime(df.parse(startTimeStr));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotBlank(endTimeStr)) {
			try {
				node.setEndTime(df.parse(endTimeStr));
			} catch (ParseException e) {
				e.printStackTrace(); 
			}
		}
		
		if(StringUtils.isBlank(node.getId())) {//新增
			node.setParent(nodeService.get(parentId));
			nodeService.save(node);
			logService.save(new Log(String.format("%s[%s]", Log.TargetObj.NODE.getName(),node.getName()), String.format("%s | %s", currUser.getNickname(),currUser.getUsername()) , Log.OperateType.ADD, String.format("[id:%s]",node.getId())));
		}else {
			node.setParent(nodeService.get(node.getId()).getParent());
			nodeService.merge(node);
			logService.save(new Log(String.format("%s[%s]", Log.TargetObj.NODE.getName(),node.getName()), String.format("%s | %s", currUser.getNickname(),currUser.getUsername()) , Log.OperateType.UPDATE, String.format("[id:%s]",node.getId())));
		}
		return JsonMapper.AlwaysMapper().toJson(new TreeNode(node));
	}
	@RequestMapping("/node/delete.jhtml")
	@ResponseBody
	public String delete(String id,HttpSession session) {
		User currUser = (User)session.getAttribute(ConstantData.SESSION_USER);
		Node node = nodeService.get(id);
		nodeService.delete(node);
		logService.save(new Log(String.format("%s[%s]", Log.TargetObj.NODE.getName(),node.getName()), String.format("%s | %s", currUser.getNickname(),currUser.getUsername()) , Log.OperateType.REMOVE, String.format("[id:%s]", node.getId())));
		return null;
	}
	@RequestMapping("/node/getAll.jhtml")
	@ResponseBody
	public String getAll() {
		Collection<Node> nodes = nodeService.findAll();
		return JsonMapper.AlwaysMapper().toJson(TreeNode.convert(nodes));
	}
	
	@RequestMapping("/node/detail.jhtml")
	public String detail(String id,Model model) {
		Node node = nodeService.get(id);
		if(node != null) {
			model.addAttribute("node", node);
			model.addAttribute("speechList", speechService.findByNode(node));
		}
		return "common/node_detail";
	}
	@RequestMapping("/node/move.jhtml")
	@ResponseBody 
	public String move(String id,String targetId,HttpSession session) {
		User currUser = (User)session.getAttribute(ConstantData.SESSION_USER);
		Boolean ret = false;
		try{
			Node node = nodeService.get(id);
			Node targetNode = nodeService.get(targetId);
			if(node != null && targetNode != null) {
				Node parentNode = node.getParent();
				node.setParent(targetNode);
				nodeService.update(node);
				ret = true;
				logService.save(new Log(String.format("%s[%s]", Log.TargetObj.NODE.getName(),node.getName()), String.format("%s | %s", currUser.getNickname(),currUser.getUsername()) , Log.OperateType.MOVE, String.format("从%s--->%s[id:%s ---> id:%s]",
						parentNode == null? "root" : parentNode.getName(),
						targetNode == null ? "root" : targetNode.getName(),
						parentNode == null ? "" : parentNode.getId(),
						targetNode == null ? "" : targetNode.getId())));
			}
		}catch(Exception e) {
			if(logger.isWarnEnabled()) {
				logger.warn(String.format("node移动失败[id:%s,targetId:%s]", id,targetId),e);
			}
		}
		return ret.toString();
	}
	
}
