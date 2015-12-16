package cn.ifactory.hypm.controller;

import cn.ifactory.hypm.entity.User;
import cn.ifactory.hypm.facade.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class RegisterController {
	@Resource(name="userService")
	private UserService userService;
	
	@RequestMapping(value="/register.jhtml",method=RequestMethod.GET)
	public String login(HttpServletRequest request) {
		return "common/register";
	}
	@RequestMapping(value="/register.jhtml",method=RequestMethod.POST)
	public String login(User user,Model model) {
		try{
			model.addAttribute("user", user);
			User dbUser = userService.findByUsername(user.getUsername());
			if(dbUser != null) {
				throw new RuntimeException("该用户名已被使用，请改用其他用户名");
			}
			userService.save(user);
			model.addAttribute("registerMesg", "注册成功，用户处于锁定状态，请联系管理员把用户启用");
		}catch(Exception e) {
			model.addAttribute("registerMesg", e.getMessage());
		}
		return "common/register";
	}
}
