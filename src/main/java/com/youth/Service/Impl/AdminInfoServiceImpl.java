package com.youth.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youth.Entity.AdminInfo;
import com.youth.Entity.User;
import com.youth.Service.AdminInfoService;
import com.youth.mapper.AdminInfoMapper;
import com.youth.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminInfoServiceImpl extends ServiceImpl<AdminInfoMapper, AdminInfo> implements AdminInfoService {
    @Autowired
    AdminInfoMapper adminInfoMapper;

    @Override
    public AdminInfo getAdminInfoByAccount(String account) {
        return null;
    }
}
