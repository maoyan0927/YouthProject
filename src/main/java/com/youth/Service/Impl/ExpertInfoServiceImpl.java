package com.youth.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youth.Entity.ExpertInfo;
import com.youth.Entity.User;
import com.youth.Service.ExpertInfoService;
import com.youth.mapper.ExpertInfoMapper;
import com.youth.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpertInfoServiceImpl extends ServiceImpl<ExpertInfoMapper, ExpertInfo> implements ExpertInfoService {
    @Autowired
    ExpertInfoMapper expertInfoMapper;

    public ExpertInfo getExpertInfoByPhone(String phone){
        return expertInfoMapper.getExpertInfoByPhone(phone);
    }
}
