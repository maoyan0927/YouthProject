package com.youth.Entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;


@Data
@TableName("expert_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "专家属性")
public class ExpertInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("专家id")
    @TableId(value = "expert_id",type = IdType.AUTO)
    private Integer expertId;

    @ApiModelProperty("专家姓名")
    private String expertName;

    @ApiModelProperty("专家手机")
    private String expertPhone;

    @ApiModelProperty("专家密码")
    private String expertPassword;

    @ApiModelProperty("专家职称")
    private String expertTitle;

    @ApiModelProperty("专家单位")
    private String expertUnit;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty("用户状态")
    private Integer state;


}