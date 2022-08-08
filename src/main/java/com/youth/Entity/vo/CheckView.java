package com.youth.Entity.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("checking")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "待检测列表")
public class CheckView implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer slicingId;

    private String httpPath;
    private String youthId;
    private String youthName;
    private String youthCardId;
    private Date physicalTime;
    private Double youthHeight;
    private Double youthWeight;

    private Date updateTime;//骨龄结果得出时间
}
