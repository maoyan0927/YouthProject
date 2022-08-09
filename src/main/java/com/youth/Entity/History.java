package com.youth.Entity;

import com.youth.Util.EnDecoderUtil;
import io.swagger.models.auth.In;
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

    private Integer youthId;

    private String youthName;

    private String youthSex;

    private String youthCardId;

    private Double youthHeight;

    private Double youthWeight;

    private String youthAge;

    private Date physicalTime;

    private Double aiScore;

    private Integer expertId;

    private Integer recoResultId;

    private Double expertScore;

    private Double heightForecast;

    private Date updateTime;

    private int slicingId;


}
