package com.youth.Service.Impl;

import com.youth.Entity.ExpertInfo;
import com.youth.Entity.User;
import com.youth.Service.AdminInfoService;
import com.youth.Service.ExpertInfoService;
import com.youth.Service.LoginService;
import com.youth.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    UserService userService;
    @Autowired
    ExpertInfoService expertInfoService;
//    @Autowired
//    AdminInfoService adminInfoService;
    @Override
    public ExpertInfo getExpertInfoByPhone(String phone) {
        return expertInfoService.getExpertInfoByPhone(phone);
    }

    @Override
    public User getUserInfoByUserPhone(String userPhone) {
        return userService.getUserInfoByUserPhone(userPhone);
    }
}
