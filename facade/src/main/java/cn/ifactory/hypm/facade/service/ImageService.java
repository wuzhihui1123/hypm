package cn.ifactory.hypm.facade.service;

import cn.ifactory.hypm.entity.Image;

public interface ImageService {
	
	void save(Image image);
	
	Image get(String id);
	
	void update(Image image);
	
	void merge(Image image);
	
	void delete(Image image);
	
}
