package com.youth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youth.Entity.Slicing;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SlicingInfoMapper extends BaseMapper<Slicing> {

    int save(Slicing slicing);

    List<Slicing> getSlicingByYouthId(Integer youthId);
}
