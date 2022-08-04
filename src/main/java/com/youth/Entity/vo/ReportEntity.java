package com.youth.Entity.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
@Data
@TableName("report")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "检测报告")
public class ReportEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer recoResultId;
    private String expertName;//检测是由谁进行
    private Integer slicingId;

    private String youthName;
    private String youthSex;
    private String youthNation;
    private String youthBirth;
    private Date physicalTime;
    private Double youthHeight;
    private Double youthWeight;

    private Double chnBoneage;
    private Double tw3Boneage;
    private Double twcBoneage;
    private String situation;
    private String expertSuggestion;

    private Double finalHeightBp;
    private Double finalHeightImprove;
    private Double geneticHeight;
    private String stageHeight;
    private String growthTrend;
    private String evaluate;

    private Date updateTime;//骨龄结果得出时间

}
