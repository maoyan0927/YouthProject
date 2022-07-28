package com.youth.Util;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AutoFiledDateUtil implements MetaObjectHandler {

    /**
     * 在插入的时候响应相关操作
     */
    @Override
    public void insertFill(org.apache.ibatis.reflection.MetaObject metaObject) {
        setFieldValByName("createTime",new Date(),metaObject);
        setFieldValByName("updateTime",new Date(),metaObject);
    }

    /**
     * 在插入的时候响应相关操作
     */
    @Override
    public void updateFill(org.apache.ibatis.reflection.MetaObject metaObject) {
        setFieldValByName("updateTime",new Date(),metaObject);
    }
}
