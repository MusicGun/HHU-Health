package com.test.examine.service.common.impl;


import com.test.examine.entity.Lab;
import com.test.examine.entity.User;
import com.test.examine.mapper.LabMapper;
import com.test.examine.mapper.RoleMapper;
import com.test.examine.mapper.UserMapper;
import com.test.examine.service.common.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    private RoleMapper roleMapper;

    private LabMapper labMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, RoleMapper roleMapper,LabMapper labMapper) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.labMapper = labMapper;
    }
    @Cacheable("user")
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public User findUserById(String id) {
        return userMapper.findUserById(id);
    }


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
    public boolean checkUser(String id) {
        if (userMapper.checkId(id) == null) {
            return false;
        }
        return true;
    }

    @CacheEvict(value = "user",key = "user.id")
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int addUser(User user) {
        //先添加主键
        userMapper.addUser(user);
        roleMapper.addRole(user.getId(), user.getRole(), user.getAuthority());
        return 1;
    }

    @CacheEvict(value = "user")
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int deleteUser(String id) {
        userMapper.deleteUser(id);
        return 1;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public int deleteAdmin(String id){
        System.out.println(id);
        Lab lab = labMapper.findLabById(id);
        if(lab!=null)
        labMapper.updateLabAdmin(lab.getLabid(),null);
        userMapper.deleteAdmin(id);
        return 1;
    }

}
