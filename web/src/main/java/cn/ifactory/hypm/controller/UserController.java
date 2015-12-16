package cn.ifactory.hypm.controller;

import cn.ifactory.hypm.entity.User;
import cn.ifactory.hypm.facade.service.UserService;
import cn.ifactory.hypm.utils.JsonMapper;
import cn.ifactory.hypm.vo.TreeUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class UserController  {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Resource(name="userService")
	private UserService userService;
	@RequestMapping(value={"/admin/user/list.jhtml"})
	public String page() {
		return "admin/user_list";
	}
	@RequestMapping(value={"/user/getAll.jhtml"})
	@ResponseBody
	public String getAllByTree() {
		List<User> users = userService.findAll();
		return JsonMapper.AlwaysMapper().toJson(TreeUser.convert(users));
	}
	@RequestMapping(value={"/admin/user/getAll.jhtml"})
	@ResponseBody
	public String getAll() {
		return JsonMapper.AlwaysMapper().toJson(userService.findAll());
	}
	@RequestMapping(value={"/admin/user/input.jhtml"})
	public String input(User user) {
		return null;
	}
	@RequestMapping(value={"/admin/user/disable.jhtml"})
	@ResponseBody
	public String disable(String id) {
		User user = userService.get(id);
		Assert.notNull(user, "用户为空");
		user.setUseable(false);
		userService.update(user);
		return null;
	}
	@RequestMapping(value={"/admin/user/useable.jhtml"})
	@ResponseBody
	public String useable(String id) {
		User user = userService.get(id);
		Assert.notNull(user, "用户为空");
		user.setUseable(true);
		userService.update(user);
		return null;
	}
	@RequestMapping(value={"/admin/user/admin.jhtml"})
	@ResponseBody
	public String admin(String id) {
		User user = userService.get(id);
		Assert.notNull(user, "用户为空");
		user.setAdmin(true);
		userService.update(user);
		return null;
	}
	@RequestMapping(value={"/admin/user/adminCacel.jhtml"})
	@ResponseBody
	public String adminCacel(String id) {
		User user = userService.get(id);
		Assert.notNull(user, "用户为空");
		user.setAdmin(false);
		userService.update(user);
		return null;
	}
	
}
