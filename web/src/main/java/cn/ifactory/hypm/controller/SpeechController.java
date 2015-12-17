package cn.ifactory.hypm.controller;

import cn.ifactory.hypm.entity.*;
import cn.ifactory.hypm.facade.service.AttachmentService;
import cn.ifactory.hypm.facade.service.LogService;
import cn.ifactory.hypm.facade.service.NodeService;
import cn.ifactory.hypm.facade.service.SpeechService;
import cn.ifactory.hypm.utils.ConstantData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;

@Controller
public class SpeechController  {
	private static final Logger logger = LoggerFactory.getLogger(SpeechController.class);
	@Resource(name="nodeService")
	private NodeService nodeService;
	@Resource(name="speechService")
	private SpeechService speechService;
	@Resource(name="attachmentService")
	private AttachmentService attachmentService;
	@Resource(name="logService")
	private LogService logService;

	/**
	 * 带附件发言
	 * @param speech
	 * @param nodeId
	 * @param session
	 * @param attachments
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/speech/input.jhtml",headers = "content-type=multipart/form-data")
	public String input(Speech speech,String nodeId,HttpSession session,@RequestParam("attachments") MultipartFile[] attachments) throws Exception {
		Node node = nodeService.get(nodeId);
		if(node == null) {
			throw new RuntimeException("node is null");
		}
		User currUser = (User)session.getAttribute(ConstantData.SESSION_USER);
		speech.setUser(currUser);

		speech.setNode(node);
		speech = speechService.save(speech);
		if(attachments != null) {
			for(MultipartFile file : attachments) {
				if(!file.isEmpty()){
					String fileName = file.getOriginalFilename();
					Attachment attachment = new Attachment();
					attachment.setName(fileName);
					attachment.setSpeech(speech);
					attachmentService.save(attachment);
					file.transferTo(new File(ConstantData.ATTACHMENT_DIR_PATH + attachment.getId() + fileName.substring(fileName.lastIndexOf("."))));
				}
			}
		}
		return "redirect:/node/detail.jhtml?id=" + nodeId;
	}
	/**
	 * 没有附件的发言
	 * @param speech
	 * @param nodeId
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/speech/input.jhtml")
	public String input(Speech speech,String nodeId,HttpSession session) throws Exception {
		return input(speech, nodeId, session, null);
	}
	@RequestMapping(value="/speech/delete.jhtml")
	@ResponseBody
	public String delete(String id,HttpSession session) {
		User currUser = (User)session.getAttribute(ConstantData.SESSION_USER);
		Boolean ret = false;
		try{
			Speech speech = speechService.get(id);
			if(speech != null) {
				speechService.delete(speech);
				ret = true;
				logService.save(new Log(String.format("%s", Log.TargetObj.SPEECH.getName()), String.format("%s | %s", currUser.getNickname(),currUser.getUsername()) , Log.OperateType.REMOVE,String.format("[id:%s]", speech.getId())));
			}
		}catch(Exception e) {
			if(logger.isWarnEnabled()) {
				logger.warn(String.format("speech删除失败[id:%s]", id),e);
			}
		}
		return ret.toString();
	}
}
