package com.youth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youth.Entity.HeightForecastResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HeightForecastMapper extends BaseMapper<HeightForecastResult> {

    HeightForecastResult getHeightByRecoId(Integer recoId);
}
