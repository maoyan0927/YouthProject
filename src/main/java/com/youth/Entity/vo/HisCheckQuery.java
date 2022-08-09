package com.youth.Entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HisCheckQuery {

    @ApiModelProperty("专家Id")
    private int expertId;

    @ApiModelProperty("孩子name")
    private String youthName;

    @ApiModelProperty("孩子身份证")
    private String youthCardId;

    @ApiModelProperty(value = "查询开始时间", example = "2022-07-18 10:12:10")
    private String begin;

    @ApiModelProperty(value = "查询结束时间", example = "2022-07-18 10:12:10")
    private String end;

    private Boolean isOnlyMe;
}
