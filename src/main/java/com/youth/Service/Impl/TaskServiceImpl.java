package com.youth.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youth.Entity.TaskInfo;
import com.youth.Service.TaskService;
import com.youth.mapper.TaskInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TaskServiceImpl extends ServiceImpl<TaskInfoMapper, TaskInfo> implements TaskService {
    @Autowired
    private TaskInfoMapper taskInfoMapper;

    @Override
    public int updateTaskState(Integer expertId, Integer state, Integer taskId) {
        return taskInfoMapper.update(taskId,expertId,state);
    }

    @Override
    public List<TaskInfo> NotInVerifyList(Integer expertId) {
        return taskInfoMapper.NotInVerifyList(expertId);
    }

    @Override
    public List<TaskInfo> InVerifyList(Integer expertId) {
        return taskInfoMapper.InVerifyList(expertId);
    }
}
