package cn.ifactory.hypm.facade.service.impl;

import cn.ifactory.hypm.dao.SpeechCommentDao;
import cn.ifactory.hypm.entity.Speech;
import cn.ifactory.hypm.entity.SpeechComment;
import cn.ifactory.hypm.facade.service.SpeechCommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service("speechCommentService")
public class SpeechCommentServiceImpl implements SpeechCommentService{
	@Resource(name="speechCommentDao")
	private SpeechCommentDao commentDao;

	public SpeechComment save(SpeechComment speechComment) {
		return commentDao.save(speechComment);
	}

	public SpeechComment get(String id) {
		return commentDao.get(id);
	}

	public void update(SpeechComment speechComment) {
		commentDao.update(speechComment);
	}

	public void delete(SpeechComment speechComment) {
		commentDao.delete(speechComment);
	}

	public List<SpeechComment> findByNode(Speech speech) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("speech", speech);
		return commentDao.findByParams(params, "createDate desc");
	}

	
}
