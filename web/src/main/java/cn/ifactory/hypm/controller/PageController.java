package cn.ifactory.hypm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController  {
	private static final Logger logger = LoggerFactory.getLogger(PageController.class);
	@RequestMapping(value={"/index.jhtml"})
	public String page() {
		return "common/index";
	}
	@RequestMapping(value={"/admin.jhtml"})
	public String admin() {
		return "admin/index";
	}
	@RequestMapping(value={"/welcome.jhtml"})
	public String welcome() {
		return "common/welcome";
	}
	
	/**
	 * 404
	 * @return
	 */
	@RequestMapping("/error/404.jhtml")
	public String sls() {
		return "error/404";
	}
	
	/**
	 * 500
	 * @return
	 */
	@RequestMapping("/error/500.jhtml")
	public String wll() {
		return "error/500";
	}
	
	/**
	 * 没有访问权限
	 * @return
	 */
	@RequestMapping("/error/403.jhtml")
	public String noAccess() {
		return "error/403";
	}
	
}
