package com.youth.Service.Impl;

import com.youth.Entity.ExpertInfo;
import com.youth.Service.ExpertInfoService;
import com.youth.mapper.ExpertInfoMapper;

public class ExpertInfoServiceImpl implements ExpertInfoService {
    ExpertInfoMapper expertInfoMapper;

    public ExpertInfo getExpertInfoByPhone(String phone){
        return expertInfoMapper.getExpertInfoByPhone(phone);
    }
}
