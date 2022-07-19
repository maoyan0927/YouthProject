package com.youth.Entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("slicing_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "切片属性")
public class Slicing implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("切片id")
    @TableId(value = "slicing_id")
    private Integer slicingId;

    @ApiModelProperty("骨龄片原始路径")
    private String dicomOriginPath;

    @ApiModelProperty("骨龄片路径")
    private String dicomPath;

    @ApiModelProperty("切片路径")
    private String slicePath;

    @ApiModelProperty("http地址")
    private String httpPath;

    @ApiModelProperty("孩童身高")
    private Double youthHeight;

    @ApiModelProperty("孩童体重")
    private Double youthWeight;

    @ApiModelProperty("拍片时间")
    private Date physicalTime;

    @ApiModelProperty("孩童id")
    private Integer youthId;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    //0-新增 1-更新 2-已有ai结果 3-提交审核中 4-专家评级 5-专家添加评价
    @ApiModelProperty("状态")
    private Integer state;

}
