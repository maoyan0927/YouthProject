package com.youth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youth.Entity.ExpertInfo;
import com.youth.Entity.User;

public interface ExpertInfoMapper extends BaseMapper<ExpertInfo> {
    ExpertInfo getExpertInfoByPhone (String phone);
}
