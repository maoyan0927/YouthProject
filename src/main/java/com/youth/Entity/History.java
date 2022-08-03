package com.youth.Entity;

import com.youth.Util.EnDecoderUtil;
import lombok.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

/**
 * @author maoyan
 * @date 2022/7/19 10:12
 */
@Data
public class History implements Serializable {

    private static final long serialVersionUID = 1L;

    private String youthName;

    private String youthCardId;

    private Date physicalTime;

    private Double aiScore;

    private Double expertScore;

    private Double heightForecast;

    private Date updateTime;

    private int slicingId;


}
