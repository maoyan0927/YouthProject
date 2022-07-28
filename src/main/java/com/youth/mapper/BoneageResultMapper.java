package com.youth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youth.Entity.BoneageResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoneageResultMapper extends BaseMapper<BoneageResult> {

    BoneageResult getBoneageByRecoId(Integer recoId);
}
