package cn.ifactory.hypm.facade.service.impl;

import cn.ifactory.hypm.dao.ImageDao;
import cn.ifactory.hypm.entity.Image;
import cn.ifactory.hypm.facade.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Transactional
@Service("imageService")
public class ImageServiceImpl implements ImageService {

	@Resource(name="imageDao")
	private ImageDao imageDao;
	public Image save(Image image) {
		return imageDao.save(image);
	}

	public Image get(String id) {
		return imageDao.get(id);
	}

	public void update(Image image) {
		imageDao.update(image);
	}

	public void merge(Image image) {
		imageDao.merge(image);
	}

	public void delete(Image image) {
		imageDao.delete(image);
	}
	

	
}
