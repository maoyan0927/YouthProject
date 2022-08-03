package com.youth.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youth.Entity.TaskInfo;

import java.util.List;

public interface TaskService extends IService<TaskInfo> {

    int updateTaskState(Integer expertId, Integer state, Integer taskId);

    List<TaskInfo> NotInVerifyList(Integer expertId);

    List<TaskInfo> InVerifyList(Integer expertId);
}
