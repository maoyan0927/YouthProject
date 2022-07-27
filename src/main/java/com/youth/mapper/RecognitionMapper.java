package com.youth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youth.Entity.RecognitionResult;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecognitionMapper extends BaseMapper<RecognitionResult> {
    int add(RecognitionResult recognitionResult);
}
