package com.youth.Util;

import lombok.Getter;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: KingRainGrey
 * Date: 2020/7/10
 */
@Getter
public enum ResultEnum {
    CODE_OK(200, "请求成功"),
    DICOM_INTERFACE_ERROR(100, "DICOM上传接口错误"),
    SLICE_INTERFACE_ERROR(101, "切割接口错误"),
    RECO_INTERFACE_ERROR(102, "识别接口错误"),
    HEIGHT_INTERFACE_ERROR(103, "身高预测接口错误"),


    SLICE_ADD_ERROR(104, "DATABASE-ERROR-104"),
    SLICE_UPDATE_ERROR(105,"DATABASE-ERROR-105"),
    RECO_ADD_ERROR(106,"DATABASE-ERROR-106"),
    RECO_UPDATE_ERROR(107,"DATA-BASE-ERROR-107"),
    DEFAULT_ERROR(108, "ERROR-108"),
    SCORE_ADD_ERROR(112, "DATABASE-ERROR-112"),
    HEIGHT_ADD_ERROR(113, "DATABASE-ERROR-113"),

    PARA_ERROR(109, "参数错误"),
    NULL_ERROR(110, "空值错误"),
    HTTP_ERROR(111,"网络错误"),

    ;


    private Integer code;
    private String message;

    private Map<String, Object> resultParam;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    ResultEnum(Integer code, String message, Map<String, Object> resultParam) {
        this.code = code;
        this.message = message;
        this.resultParam = resultParam;
    }
}
