package com.youth.Service.Impl;

import cn.hutool.system.UserInfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youth.Entity.User;
import com.youth.Service.UserService;
import com.youth.mapper.UserMapper;
import com.youth.mapper.YouthInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    public User getUserInfoByUserPhone(String userPhone){
        return userMapper.getUserInfoByUserPhone(userPhone);
    }

    @Override
    public Boolean addUser(User user) {
        return this.save(user);
    }
}
