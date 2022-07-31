package com.youth.Entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("task_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "任务属性")
public class TaskInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("任务id")
    @TableId(value = "task_id",type = IdType.AUTO)
    private Integer taskId;

    @ApiModelProperty("识别Id")
    private Integer recoId;

    @ApiModelProperty("专家id")
    private Integer expertId;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty("任务状态")
    private Integer state;
}
