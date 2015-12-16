package cn.ifactory.hypm.controller;

import cn.ifactory.hypm.entity.Attachment;
import cn.ifactory.hypm.facade.service.AttachmentService;
import cn.ifactory.hypm.utils.ConstantData;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;

@Controller
public class AttachmentController  {
	private static final Logger logger = LoggerFactory.getLogger(AttachmentController.class);
	@Resource(name="attachmentService")
	private AttachmentService attachmentService;
	
	@RequestMapping("/attachment/download.jhtml")
	public String download(String id,HttpServletResponse response) throws Exception {
		response.setContentType("application/octet-stream");  
		Attachment attachment = attachmentService.get(id);
		if(attachment == null) return null;
		String fileName = attachment.getName();
		File downloadFile = new File(ConstantData.ATTACHMENT_DIR_PATH + attachment.getId() + fileName.substring(fileName.lastIndexOf(".")));
		if(downloadFile.exists() && downloadFile.isFile()) {
			response.setHeader("Content-disposition", "attachment; filename="  
					+ new String(fileName.getBytes("utf-8"), "ISO8859-1"));  
			response.setHeader("Content-Length", String.valueOf(downloadFile.length()));  
			BufferedInputStream bis = null;  
	        BufferedOutputStream bos = null;  
	        try{
	        	bis = new BufferedInputStream(FileUtils.openInputStream(downloadFile));  
	        	bos = new BufferedOutputStream(response.getOutputStream());  
	        	byte[] buff = new byte[2048];  
	        	int bytesRead;  
	        	while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
	        		bos.write(buff, 0, bytesRead);  
	        	}  
	        }finally {
	        	if(bis != null) bis.close();  
	        	if(bos != null) bos.close(); 
	        }
		}
		return null;
	}
	
}
