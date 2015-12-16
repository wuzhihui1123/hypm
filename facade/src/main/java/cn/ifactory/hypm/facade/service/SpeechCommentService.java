package cn.ifactory.hypm.facade.service;

import cn.ifactory.hypm.entity.Speech;
import cn.ifactory.hypm.entity.SpeechComment;

import java.util.List;

public interface SpeechCommentService {
	
	void save(SpeechComment speechComment);
	
	SpeechComment get(String id);
	
	void update(SpeechComment speechComment);
	
	void delete(SpeechComment speechComment);

	List<SpeechComment> findByNode(Speech speech);
	
	
}
