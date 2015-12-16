package cn.ifactory.hypm.facade.service;

import cn.ifactory.hypm.entity.User;

import java.util.List;

public interface UserService {
	
	void save(User user);
	
	User get(String id);
	
	void update(User user);
	
	void delete(User user);

	List<User> findAll();
	
	User findByUsernameAndPassword(String username, String password);
	
	User findByUsername(String username);
	
}
