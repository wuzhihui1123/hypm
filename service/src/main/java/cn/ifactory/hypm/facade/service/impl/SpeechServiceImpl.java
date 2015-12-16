package cn.ifactory.hypm.facade.service.impl;

import cn.ifactory.hypm.dao.SpeechDao;
import cn.ifactory.hypm.entity.Node;
import cn.ifactory.hypm.entity.Speech;
import cn.ifactory.hypm.facade.service.SpeechService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service("speechService")
public class SpeechServiceImpl implements SpeechService{
	@Resource(name="speechDao")
	private SpeechDao speechDao;

	public void save(Speech speech) {
		speechDao.save(speech);
	}

	public Speech get(String id) {
		return speechDao.get(id);
	}

	public void update(Speech speech) {
		speechDao.update(speech);
	}

	public void delete(Speech speech) {
		speech.setUseable(false);
		speechDao.update(speech);
	}

	public List<Speech> findByNode(Node node) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("node", node);
		params.put("useable", true);
		return speechDao.findByParams(params, "createDate asc");
		
	}

}
