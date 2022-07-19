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

    private String name;

    private String sex;

    private String nation;

    private String uploadTime;

    private String expertState;

    private String sliceId;

    /**
     * 性别判断
     */
    public void setSex(Integer sex) {
        if (sex == 0) {
            this.sex = "男";
        }else {
            this.sex = "女";
        }
    }

    public void setSliceId(Integer sliceId) {
        byte[] encode_bytes = EnDecoderUtil.DESEncrypt("20220720", sliceId + "");
        String sliceStr = Base64.getEncoder().encodeToString(encode_bytes);
        this.sliceId = sliceStr;
    }

    /**
     * 上传时间
     */
    public void setUploadTime(Date time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.uploadTime = sdf.format(time);
    }

    /**
     * 状态判断
     */
    public void setExpertState(Integer state) {
        switch (state){
            case 3:
            case 4:
                this.expertState = "审核中";
                break;
            case 5:
                this.expertState = "已审核";
                break;
            default:
                this.expertState = "未审核";
                break;
        }
    }

    public History() {}
}
