package com.youth.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("height_forecast_result")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "身高结果属性")
public class HeightForecastResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("身高预测结果id")
    @TableId(value = "height_forecast_id",type = IdType.AUTO)
    private Integer heightForecastId;

    private Integer recoResultId;

    private Double finalHeightBp;

    private Double finalHeightImprove;

    private Double stageHeight;

    private Double geneticHeight;

    private String growthTrend;

    private String evaluate;

    private Date createTime;

    private Date updateTime;

    private Integer state;

}
