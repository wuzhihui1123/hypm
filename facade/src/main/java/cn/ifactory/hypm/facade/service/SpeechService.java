package cn.ifactory.hypm.facade.service;

import cn.ifactory.hypm.entity.Node;
import cn.ifactory.hypm.entity.Speech;

import java.util.List;

public interface SpeechService {

	Speech save(Speech speech);
	
	Speech get(String id);
	
	void update(Speech speech);
	
	void delete(Speech speech);

	List<Speech> findByNode(Node node);
	
	
}
