package com.youth.Entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;


@Data
@TableName("youth_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "孩童属性")
public class YouthInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("孩童id")
    @TableId(value = "youth_id", type = IdType.AUTO)
    private Integer youthId;

    @ApiModelProperty("孩童name")
    private String youthName;

    @ApiModelProperty("孩童性别")
    private Integer youthSex;

    @ApiModelProperty("孩童名族")
    private String youthNation;

    @ApiModelProperty("孩童出生日期")
    private Date youthBirth;

    @ApiModelProperty("孩童身份证")
    private String youthCardId;

    @ApiModelProperty("孩童当前住址")
    private String youthAddress;

    @ApiModelProperty("孩童籍贯")
    private String youthNativePlace;

    @ApiModelProperty("孩童父亲身高")
    private Double youthFatherHeight;

    @ApiModelProperty("孩童母亲身高")
    private Double youthMotherHeight;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
