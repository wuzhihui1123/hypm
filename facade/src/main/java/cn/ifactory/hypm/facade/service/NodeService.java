package cn.ifactory.hypm.facade.service;

import cn.ifactory.hypm.entity.Node;

import java.util.Collection;

public interface NodeService {

	Node save(Node node);
	
	Node get(String id);
	
	void update(Node node);
	
	void merge(Node node);
	
	void delete(Node node);
	
	Collection<Node> findAll();
	
}
