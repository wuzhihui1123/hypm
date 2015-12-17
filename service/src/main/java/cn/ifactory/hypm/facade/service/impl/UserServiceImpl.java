package cn.ifactory.hypm.facade.service.impl;

import cn.ifactory.hypm.dao.UserDao;
import cn.ifactory.hypm.entity.User;
import cn.ifactory.hypm.facade.service.UserService;
import cn.ifactory.hypm.utils.Digests;
import cn.ifactory.hypm.utils.Encodes;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {
    //
    public static final int HASH_INTERATIONS = 2;

    @Resource(name = "userDao")
    private UserDao userDao;

    public User save(User user) {
        encryptPassword(user);
        return userDao.save(user);
    }

    public User get(String id) {
        return userDao.get(id);
    }

    public void update(User user) {
        userDao.update(user);
    }

    public void delete(User user) {
        userDao.delete(user);
    }

    public List<User> findAll() {
        return userDao.findByParams(null);
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public User findByUsernameAndPassword(String username, String password) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", username);
        params.put("password", encryptPassword(password));
        User user = userDao.findOneByParams(params);
        return user;
    }

    public User findByUsername(String username) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", username);
        return userDao.findOneByParams(params);
    }

    /**
     * 用户密码加密
     *
     * @param user
     */
    private void encryptPassword(User user) {
        user.setPassword(encryptPassword(user.getPassword()));
    }

    /**
     * 用户密码加密
     *
     * @param password
     */
    private String encryptPassword(String password) {
        if (StringUtils.isEmpty(password)) {
            throw new RuntimeException("密码不能为空");
        }
        String ret = Encodes.encodeHex(Digests.sha1(password.getBytes(), HASH_INTERATIONS));
        return ret;
    }


}
