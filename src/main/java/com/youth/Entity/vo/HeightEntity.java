package com.youth.Entity.vo;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

/**
 * @author maoyan
 * @date 2022/7/24 13:19
 */
@Data
public class HeightEntity {

    private Double bp;

    private Double bp2;

    private Double target;

    private String message;

    private JSONArray forecast;

    private Double stateheight;


}
