package cn.ifactory.hypm.facade.service;

import cn.ifactory.hypm.entity.Attachment;
import cn.ifactory.hypm.entity.Speech;

import java.util.List;

public interface AttachmentService {

	Attachment save(Attachment attachment);
	
	Attachment get(String id);
	
	void update(Attachment attachment);
	
	void delete(Attachment attachment);

	List<Attachment> findBySpeech(Speech speech);
	
	
}
