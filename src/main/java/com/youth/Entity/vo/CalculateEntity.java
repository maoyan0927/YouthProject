package com.youth.Entity.vo;

import lombok.Data;
import org.apache.commons.collections4.map.ListOrderedMap;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author
 * @date 2022/7/27 19:27
 */
@Data
public class CalculateEntity {
    /*结果类，用于在“根据专家判定等级计算骨龄”接口返回数据*/
    private String code;
    private String message;
    private Map<String,Object> data = new ListOrderedMap<>();
    public static CalculateEntity success(){
        CalculateEntity result = new CalculateEntity();
        result.setCode("success");
        result.setMessage("计算完成");
        return result;
    }

    public static CalculateEntity error(String str){
        CalculateEntity result = new CalculateEntity();
        result.setCode("fail");
        result.setMessage(str);
        return result;
    }

    public CalculateEntity add(String key, Object value){
        this.getData().put(key, value);
        return this;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
