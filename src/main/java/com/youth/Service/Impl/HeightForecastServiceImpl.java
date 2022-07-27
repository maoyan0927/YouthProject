package com.youth.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youth.Entity.HeightForecastResult;
import com.youth.Service.HeightForecastService;
import com.youth.mapper.HeightForecastMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HeightForecastServiceImpl extends ServiceImpl<HeightForecastMapper, HeightForecastResult> implements HeightForecastService {
}
