package com.youth.Util;

public interface ResultCode {

    public static Integer SUCCESS = 20000; //成功

    public static Integer ERROR = 20001; //失败

    public static Integer DeleteDicom = 20005; //删除图片失败

    public static Integer SlicingERROR = 20002; //切割失败

    public static Integer RecognitionERROR = 20003; //识别失败

    public static Integer HeightERROR = 20004; //身高失败
}
