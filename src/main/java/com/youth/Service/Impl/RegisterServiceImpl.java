package com.youth.Service.Impl;

import com.youth.Controller.RegisterController;
import com.youth.Entity.User;
import com.youth.Service.RegisterService;
import com.youth.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    UserService userService;

    @Override
    public Boolean addUser(User user) {
        return userService.addUser(user);
    }

    @Override
    public User getUserInfoByUserPhone(String userPhone) {
        return userService.getUserInfoByUserPhone(userPhone);
    }

}
