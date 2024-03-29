package com.youth.Entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CheckQuery {

    @ApiModelProperty(value = "孩童姓名")
    private String youthName;

    @ApiModelProperty(value = "孩童身份证号")
    private String youthCardId;

    @ApiModelProperty(value = "查询开始时间", example = "2022-07-18 10:12:10")
    private String begin;

    @ApiModelProperty(value = "查询结束时间", example = "2022-07-18 10:12:10")
    private String end;
}
