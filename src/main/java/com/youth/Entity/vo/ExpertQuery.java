package com.youth.Entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExpertQuery {

    @ApiModelProperty(value = "专家姓名")
    private String expertName;

    @ApiModelProperty(value = "专家职称")
    private String expertTitle;

    @ApiModelProperty(value = "专家所在单位")
    private String expertUnit;

    @ApiModelProperty(value = "专家手机号")
    private String expertPhone;

    @ApiModelProperty(value = "查询开始时间", example = "2022-07-18 10:12:10")
    private String begin;

    @ApiModelProperty(value = "查询结束时间", example = "2022-07-18 10:12:10")
    private String end;
}