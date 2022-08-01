package com.youth.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youth.Entity.TaskInfo;

public interface TaskService extends IService<TaskInfo> {

    int updateTaskState(Integer expertId, Integer state, Integer taskId);
}
