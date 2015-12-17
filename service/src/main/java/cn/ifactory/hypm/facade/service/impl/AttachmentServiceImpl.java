package cn.ifactory.hypm.facade.service.impl;

import cn.ifactory.hypm.dao.AttachmentDao;
import cn.ifactory.hypm.entity.Attachment;
import cn.ifactory.hypm.entity.Speech;
import cn.ifactory.hypm.facade.service.AttachmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Transactional
@Service("attachmentService")
public class AttachmentServiceImpl implements AttachmentService {
	@Resource(name="attachmentDao")
	private AttachmentDao attachmentDao;
	public Attachment save(Attachment attachment) {
		return attachmentDao.save(attachment);
	}

	public Attachment get(String id) {
		return attachmentDao.get(id);
	}

	public void update(Attachment attachment) {
		attachmentDao.update(attachment);
	}

	public void delete(Attachment attachment) {
		attachmentDao.delete(attachment);
	}

	public List<Attachment> findBySpeech(Speech speech) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("speech", speech);
		return attachmentDao.findByParams(params);
	}
	
	
}
