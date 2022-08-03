package com.youth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youth.Entity.TaskInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskInfoMapper extends BaseMapper<TaskInfo> {
    int update(Integer taskId,Integer expertId,Integer state);

    List<TaskInfo> NotInVerifyList(Integer expertId);

    List<TaskInfo> InVerifyList(Integer expertId);
}
