package com.youth.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youth.Entity.RecognitionResult;
import com.youth.Entity.vo.AnalysisParm;
import com.youth.Util.ResultEnum;

import java.util.List;

public interface RecognitionService extends IService<RecognitionResult> {

    int add(RecognitionResult RecognitionResult);

    ResultEnum analysisProcess(AnalysisParm analysisParm);

    List<RecognitionResult> getRecognitionBySlicingId(Integer slicingId);

    List<RecognitionResult> getInVerifyList(Integer kind);

    RecognitionResult getAIRecognitionBySlicingId(Integer slicingId);

    RecognitionResult getExpertRecognitionBySlicingId(Integer slicingId);
}
