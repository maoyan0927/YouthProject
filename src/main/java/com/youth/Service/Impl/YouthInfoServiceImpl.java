package com.youth.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youth.Entity.AdminInfo;
import com.youth.Entity.YouthInfo;
import com.youth.Service.YouthInfoService;
import com.youth.mapper.YouthInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class YouthInfoServiceImpl extends ServiceImpl<YouthInfoMapper, YouthInfo> implements YouthInfoService {
    @Autowired
    private YouthInfoMapper youthInfoMapper;
    QueryWrapper<YouthInfo> queryWrapper = new QueryWrapper<>();

    @Override
    public List<YouthInfo> getYouthInfoByUserId(Integer userId) {
        return youthInfoMapper.getYouthInfoByUserId(userId);
    }

    @Override
    public YouthInfo getYouthInfoByCardId(String cardId) {
        queryWrapper.eq("youth_card_id",cardId);
        queryWrapper.last("LIMIT 1");
        return this.getOne(queryWrapper);
    }

    @Override
    public List<YouthInfo> getYouthInfoByYouthNameAndUserId(String youthName ,int userId) {
        if(!StringUtils.isEmpty(youthName)) {
            //构建条件
            queryWrapper.like("youth_name",youthName);
            queryWrapper.eq("user_id",userId);
            return this.list(queryWrapper);
        }
        return null;
    }

    @Override
    public Boolean deleteYouthInfoByYouthId(int youthId) {
        return this.removeById(youthId);
    }
}
