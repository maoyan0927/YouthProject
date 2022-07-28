package com.youth.Entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;


@Data
@TableName("admin_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "管理员属性")
public class AdminInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("管理员id")
    @TableId(value = "admin_id",type = IdType.AUTO)
    private Integer adminId;

    @ApiModelProperty("管理员账号")
    @TableField(value = "admin_account")
    private String adminAccount;

    @ApiModelProperty("管理员手机")
    @TableField(value = "admin_phone")
    private String adminPhone;

    @ApiModelProperty("管理员密码")
    @TableField(value = "admin_password")
    private String adminPassword;

    @ApiModelProperty("管理员姓名")
    @TableField(value = "admin_name")
    private String adminName;

    @ApiModelProperty("创建时间")
    //TODO: 创建自动填充处理器？https://blog.csdn.net/weixin_42526068/article/details/113039510
    @TableField(fill = FieldFill.INSERT,value = "create_time")
    private Date createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE,value = "update_time")
    private Date updateTime;

    @ApiModelProperty("管理员状态")
    @TableField(value = "state")
    private Integer state;


}