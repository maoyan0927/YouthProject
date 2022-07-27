package com.youth.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youth.Entity.RecognitionResult;
import com.youth.Entity.vo.AnalysisParm;
import com.youth.Util.ResultEnum;

public interface RecognitionService extends IService<RecognitionResult> {

    int add(RecognitionResult RecognitionResult);

    ResultEnum analysisProcess(AnalysisParm analysisParm);

}
