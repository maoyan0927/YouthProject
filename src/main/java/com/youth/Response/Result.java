package com.youth.Response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Result<T> {

    private int code;
    private String msg;
    private T data;

    /**
     *  成功时候调用+返回数据使用
     * */
    public static  <T> Result<T> success(T data){
        Result<T> result = new Result<T>(data);
        result.code = com.youth.Response.CodeMsg.SUCCESS.getCode();
        result.msg = com.youth.Response.CodeMsg.SUCCESS.getMsg();
        return result;
    }

    /**
     *  成功时候调用+不返回数据使用
     * */
    public static <T> Result<T> success(){
        Result<T> result = new Result<T>(com.youth.Response.CodeMsg.SUCCESS.getCode(), com.youth.Response.CodeMsg.SUCCESS.getMsg());
        return result;
    }

    /**
     *  失败时候的调用
     * */
    public static  <T> Result<T> error(com.youth.Response.CodeMsg codeMsg){
        return new Result<T>(codeMsg);
    }

    private Result(T data) {
        this.data = data;
    }

    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Result(com.youth.Response.CodeMsg codeMsg) {
        if(codeMsg != null) {
            this.code = codeMsg.getCode();
            this.msg = codeMsg.getMsg();
        }
    }
}
