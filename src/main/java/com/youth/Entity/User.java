package com.youth.Entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user_info")
@ApiModel(value = "用户属性")
public class User {

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("用户name")
    private String userName;

    @ApiModelProperty("用户手机")
    private String userPhone;

    @ApiModelProperty("用户密码")
    private String userPassword;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("用户状态")
    private Integer state;


}
