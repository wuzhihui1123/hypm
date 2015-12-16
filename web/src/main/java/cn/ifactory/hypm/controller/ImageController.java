package cn.ifactory.hypm.controller;

import cn.ifactory.hypm.entity.Image;
import cn.ifactory.hypm.facade.service.ImageService;
import cn.ifactory.hypm.utils.CommonUtils;
import cn.ifactory.hypm.utils.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ImageController  {
	private static final Logger logger = LoggerFactory.getLogger(ImageController.class);
	@Resource(name="imageService")
	private ImageService imageService;
	
	@RequestMapping(value="/image/upload.jhtml",method=RequestMethod.POST)
	@ResponseBody
	public String upload(MultipartFile imgFile,HttpServletRequest request) {
		Map<String,String> ret = new HashMap<String,String>();
		try{
			if(!imgFile.isEmpty()) {
				Image image = new Image();
				image.setName(imgFile.getOriginalFilename());
				image.setDatas(imgFile.getBytes());
				imageService.save(image);
				ret.put("state", "SUCCESS");
				ret.put("url", request.getContextPath() + "/image/read.jhtml?id=" + image.getId());
				ret.put("title", imgFile.getOriginalFilename());
				ret.put("original", imgFile.getOriginalFilename());
			}
		}catch(Exception e) {
			ret.put("state", "ERROR");
			ret.put("url", "");
			ret.put("title", "");
			ret.put("original", "");
			if(logger.isErrorEnabled()) {
				logger.error("图片上传出错",e);
			}
		}
		
		return JsonMapper.AlwaysMapper().toJson(ret);
		
	}
	
	@RequestMapping(value="/image/read.jhtml",method=RequestMethod.GET)
	public void read(String id,WebRequest request, HttpServletResponse response) {
		try {
			Image image = imageService.get(id);
			if(image != null) {
				//过期时间。图片没有修改操作，因此给予一个尽量大的过期时间，以便浏览器后续访问中直接使用缓存而不再向服务器发送‘修改验证’请求
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.YEAR, 10);//十年后
				response.setDateHeader("Expires", calendar.getTime().getTime());
				
				//对数据库存储的图片访问采用缓存机制（数据库中图片数据没有修改操作，因此数据生成时间就是最后修改时间）
				long lastModified = image.getCreateDate() == null ? -1L : image.getCreateDate().getTime();
				if(request.checkNotModified(lastModified)) {
					return;
				}
				response.setContentType(CommonUtils.getImageMimeType(image.getName()));
				response.getOutputStream().write(image.getDatas());
			}else {
				//默认图片
				response.sendRedirect(request.getContextPath() +"/static/img/img_default.png");
			}
		} catch (IOException e) {
			e.printStackTrace();
			if(logger.isErrorEnabled()) {
				logger.error(String.format("读取图片失败[id:%s]", id), e);
			}
		}
		
	}

	public ImageService getImageService() {
		return imageService;
	}

	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}

	
}
