package cn.ifactory.hypm.facade.service;

import cn.ifactory.hypm.entity.Log;

import java.util.List;

public interface LogService {

	Log save(Log log);
	
	Log get(String id);
	
	void delete(Log log);

	List<Log> findAll();
	int findAllCount();
	
	List<Log> findByPage(int firstResult, int maxResults);
	
}
