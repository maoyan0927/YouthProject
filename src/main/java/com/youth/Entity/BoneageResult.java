package com.youth.Entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("bone_age_result")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "骨龄识别结果属性")
public class BoneageResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("骨龄预测结果id")
    @TableId(value = "bone_age_id")
    private Integer boneAgeId;

    private Integer recoResultId;

    private Integer scoreSum;

    private Double chnBoneage;

    private Double tw3Boneage;

    private Double twcBoneage;

    private String situation;

    private String expertSuggestion;

    private Date createTime;

    private Date updateTime;

    private Integer state;
}
