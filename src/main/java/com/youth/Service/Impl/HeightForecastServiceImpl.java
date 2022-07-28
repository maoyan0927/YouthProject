package com.youth.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youth.Entity.HeightForecastResult;
import com.youth.Service.HeightForecastService;
import com.youth.mapper.HeightForecastMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class HeightForecastServiceImpl extends ServiceImpl<HeightForecastMapper, HeightForecastResult> implements HeightForecastService {

    @Autowired
    private HeightForecastMapper heightForecastMapper;

    @Override
    public HeightForecastResult getHeightByRecoId(Integer recoId) {
        return heightForecastMapper.getHeightByRecoId(recoId);
    }
}
