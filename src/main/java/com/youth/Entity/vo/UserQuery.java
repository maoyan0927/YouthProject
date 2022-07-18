package com.youth.Entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author maoyan
 * @date 2022/7/18 11:09
 */
@Data
public class UserQuery {

    @ApiModelProperty("用户name")
    private String userName;

    @ApiModelProperty(value = "查询开始时间", example = "2022-07-18 10:12:10")
    private String begin;

    @ApiModelProperty(value = "查询结束时间", example = "2022-07-18 10:12:10")
    private String end;
}
