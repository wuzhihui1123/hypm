package cn.ifactory.hypm.facade.service.impl;

import cn.ifactory.hypm.dao.LogDao;
import cn.ifactory.hypm.entity.Log;
import cn.ifactory.hypm.facade.service.LogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Transactional
@Service("logService")
public class LogServiceImpl implements LogService {
	@Resource(name="logDao")
	private LogDao logDao;
	
	public Log save(Log log) {
		return logDao.save(log);
	}

	public Log get(String id) {
		return logDao.get(id);
	}

	public void delete(Log log) {
		logDao.delete(log);
	}

	public List<Log> findAll() {
		return logDao.findByParams(null,"createDate desc");
	}

	public List<Log> findByPage(int firstResult,int maxResults) {
		return logDao.findByPage(null, firstResult, maxResults, "createDate desc");
		
	}

	public int findAllCount() {
		return logDao.getCountByParams(null);
	}
	
	

}
