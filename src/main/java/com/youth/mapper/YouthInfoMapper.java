package com.youth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youth.Entity.YouthInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface YouthInfoMapper extends BaseMapper<YouthInfo> {
    List<YouthInfo> getYouthInfoByUserId(Integer userId);
}
