package com.youth.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youth.Entity.YouthInfo;
import com.youth.Service.YouthInfoService;
import com.youth.mapper.YouthInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YouthInfoServiceImpl extends ServiceImpl<YouthInfoMapper, YouthInfo> implements YouthInfoService {
    @Autowired
    private YouthInfoMapper youthInfoMapper;

    @Override
    public List<YouthInfo> getYouthInfoByUserId(Integer userId) {
        List<YouthInfo> list = youthInfoMapper.getYouthInfoByUserId(userId);
        return list;
    }
}
