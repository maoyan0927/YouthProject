package com.youth.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeMsg {

    private int code;
    private String msg;

    /**
     * 通用的错误码
     */
    public static CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(101, "服务端异常");
    public static CodeMsg SMS_ERROR = new CodeMsg(102,"短信发送失败");
    public static CodeMsg LOGIN_FAIL = new CodeMsg(103,"账号或密码错误");
    public static CodeMsg CLINIC_LOST = new CodeMsg(104,"坐诊医院失效");
    public static CodeMsg CHILD_EXIST = new CodeMsg(105,"儿童已存在");
    public static CodeMsg BIND_FAIL = new CodeMsg(106,"绑定失败");
    public static CodeMsg APPOINTMENT_EXIST = new CodeMsg(107,"预约记录已存在");
    public static CodeMsg APPOINTMENT_LIMIT = new CodeMsg(108,"预约人数已满");
    public static CodeMsg BIND_EXIST = new CodeMsg(109,"绑定已存在");
    public static CodeMsg CANCEL_APPOINTMENT_FAIL = new CodeMsg(110,"取消预约记录失败");
    public static CodeMsg RESTORE_APPOINTMENT_FAIL = new CodeMsg(111,"恢复预约记录失败");
    public static CodeMsg ALREADY_CANCEL_APPOINTMENT = new CodeMsg(112,"预约记录已被取消");
    public static CodeMsg ALREADY_RESTORE_APPOINTMENT = new CodeMsg(113,"预约记录已被恢复");
    public static CodeMsg EXIST_APPOINTMENT = new CodeMsg(114,"已存在相同预约");
    public static CodeMsg VCODE_TIME = new CodeMsg(115,"不能在60s内连续发送");


    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }

    @Override
    public String toString() {
        return "CodeMsg [code=" + code + ", msg=" + msg + "]";
    }
}
