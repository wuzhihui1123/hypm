package cn.ifactory.hypm.controller;

import cn.ifactory.hypm.entity.User;
import cn.ifactory.hypm.facade.service.UserService;
import cn.ifactory.hypm.utils.ConstantData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
	@Resource(name="userService")
	private UserService userService;
	
	@RequestMapping(value="/login.jhtml",method=RequestMethod.GET)
	public String login(HttpServletRequest request) {
		return "common/login";
	}
	@RequestMapping(value="/logout.jhtml",method=RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session != null) {
			session.invalidate();
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="/login.jhtml",method=RequestMethod.POST)
	public String login(User user,HttpServletRequest request,Model model) {
		User dbUser = userService.findByUsernameAndPassword(user.getUsername(), user.getPassword());
		if(dbUser == null) {
			model.addAttribute("error", "用户名或密码不正确");
			return "common/login";
		}else if(!dbUser.isUseable()) {
			model.addAttribute("error", "账户被禁用，请联系管理员启用");
			return "common/login";
		}else {
			request.getSession(true).setAttribute(ConstantData.SESSION_USER, dbUser);
			return "redirect:/";
		}
		
	}
}
