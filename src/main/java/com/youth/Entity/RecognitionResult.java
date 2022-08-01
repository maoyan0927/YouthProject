package com.youth.Entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("recognition_result")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "识别结果属性")
public class RecognitionResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("识别结果id")
    @TableId(value = "reco_result_id",type = IdType.AUTO)
    private Integer recoResultId;

    @ApiModelProperty("切割id")
    private Integer slicingId;

    private Integer raoRank;

    private Integer touRank;

    private Integer gouRank;

    private Integer chiRank;

    private Integer zhang1Rank;

    private Integer zhang3Rank;

    private Integer zhang5Rank;

    private Integer jin1Rank;

    private Integer jin3Rank;

    private Integer jin5Rank;

    private Integer zhong3Rank;

    private Integer zhong5Rank;

    private Integer yuan1Rank;

    private Integer yuan3Rank;

    private Integer yuan5Rank;

    @ApiModelProperty("识别类型")
    private Integer kind;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty("状态")
    private Integer state;



}
