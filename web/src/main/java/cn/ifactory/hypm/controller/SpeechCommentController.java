package cn.ifactory.hypm.controller;

import cn.ifactory.hypm.entity.Speech;
import cn.ifactory.hypm.entity.SpeechComment;
import cn.ifactory.hypm.entity.User;
import cn.ifactory.hypm.facade.service.SpeechCommentService;
import cn.ifactory.hypm.facade.service.SpeechService;
import cn.ifactory.hypm.utils.ConstantData;
import cn.ifactory.hypm.utils.JsonMapper;
import cn.ifactory.hypm.vo.SpeechCommentVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class SpeechCommentController  {
	private static final Logger logger = LoggerFactory.getLogger(SpeechCommentController.class);
	@Resource(name="speechService")
	private SpeechService speechService;
	@Resource(name="speechCommentService")
	private SpeechCommentService speechCommentService;
	
	@RequestMapping(value="/speechComment/add.jhtml")
	@ResponseBody
	public String add(String speechId,SpeechComment comment,HttpSession session) {
		Boolean ret = false;
		try{
			User currUser = (User)session.getAttribute(ConstantData.SESSION_USER);
			comment.setUser(currUser);
			Speech speech = speechService.get(speechId);
			comment.setSpeech(speech);
			speechCommentService.save(comment);
			ret = true;
		}catch(Exception e) {
			if(logger.isErrorEnabled()) {
				logger.error(String.format("发言评论失败[speechId:%s]", speechId), e);
			}
		}
		return ret.toString();
	}
	@RequestMapping(value="/speechComment/view.jhtml")
	@ResponseBody
	public String view(String speechId) {
		Speech speech = speechService.get(speechId);
		return JsonMapper.AlwaysMapper().toJson(SpeechCommentVo.convert(speech.getComments()));
	}
	
}
