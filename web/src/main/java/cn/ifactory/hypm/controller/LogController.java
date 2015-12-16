package cn.ifactory.hypm.controller;

import cn.ifactory.hypm.entity.Log;
import cn.ifactory.hypm.facade.service.LogService;
import cn.ifactory.hypm.utils.JsonMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LogController  {
	@Resource(name="logService")
	private LogService logService;
	
	@RequestMapping(value="/admin/log/getByPage.jhtml")
	@ResponseBody
	public String getByPage(Integer page,Integer rows) {
		Map<String,Object> ret = new HashMap<String,Object>();
		page = page == null ? 1 : page;
		rows = rows == null ? 50 : rows;
		List<Log> logs = logService.findByPage( (page - 1)*rows, rows);
		ret.put("rows", logs);
		ret.put("total", logService.findAllCount());
		return JsonMapper.AlwaysMapper().toJson(ret);
	}
	@RequestMapping(value="/admin/log/list.jhtml")
	public String list(Integer page,Integer rows) {
		return "admin/log_list";
	}
}
