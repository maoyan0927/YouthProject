package com.youth.Entity;

import lombok.Data;

import java.util.Date;

@Data
public class ReportInfo {
    private String name;
    private String sex;
    private Date birth;

    private Double age;
    private Double weight;
    private Double height;

    private Date checkDate;

    private Double chnBoneAge;
    private Double tw3BoneAge;
    private Double twcBoneAge;

    //曲线数据
    private Curve curveChn;
    private Curve curveTw3;
    private Curve curveTwc;

    private Double boneAgeHeight; //骨龄身高

    private String wholeAgeGrowth; //发育情况

    private String expertSuggest; //专家意见

    private String slicingId;
    private Integer state;
}
