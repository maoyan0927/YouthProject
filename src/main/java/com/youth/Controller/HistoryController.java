package com.youth.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.youth.Entity.*;
import com.youth.Entity.vo.HisQuery;
import com.youth.Service.*;
import com.youth.Util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/history")
@RestController
@Slf4j
@Api(tags = "用户历史记录类")
@CrossOrigin
public class HistoryController {
    @Autowired
    YouthInfoService youthInfoService;

    @Autowired
    SlicingInfoService slicingInfoService;

    @Autowired
    RecognitionService recognitionService;

    @Autowired
    BoneageResultService boneageResultService;

    @Autowired
    HeightForecastService heightForecastService;


    @ApiOperation("获取用户历史信息")
    @GetMapping("/getHistoryData/{userId}")
    public R getHistoryData(@PathVariable Integer userId) {
        List<History> histories = new ArrayList<>();
        try {
            if (userId != null) {
                List<YouthInfo> youthInfos = youthInfoService.getYouthInfoByUserId(userId);
                if (youthInfos == null) {
                    return R.ok().message("无绑定孩子");
                } else {
                    for (YouthInfo youthInfo : youthInfos) {
                        int youthId = youthInfo.getYouthId();
                        List<Slicing> slicingList = slicingInfoService.getSlicingByYouthId(youthId);
                        if (slicingList != null) {
                            for (Slicing slicing : slicingList) {
                                History history = new History();
                                history.setYouthName(youthInfo.getYouthName());
                                history.setYouthCardId(youthInfo.getYouthCardId());
                                history.setSlicingId(slicing.getSlicingId());
                                history.setPhysicalTime(slicing.getPhysicalTime());
                                RecognitionResult aiRecognitionBySlicingId = recognitionService.getAIRecognitionBySlicingId(slicing.getSlicingId());
                                if (aiRecognitionBySlicingId != null) {
                                    int recoResultIdByAI = aiRecognitionBySlicingId.getRecoResultId();
                                    history.setAiScore(boneageResultService.getBoneageByRecoId(recoResultIdByAI).getChnBoneage());
                                    history.setHeightForecast(heightForecastService.getHeightByRecoId(recoResultIdByAI).getFinalHeightImprove());
                                } else {
                                    history.setAiScore((double) -1);
                                    history.setHeightForecast((double) -1);
                                }
                                RecognitionResult expertRecognitionBySlicingId = recognitionService.getExpertRecognitionBySlicingId(slicing.getSlicingId());
                                if (expertRecognitionBySlicingId != null) {
                                    int recoResultIdByExpert = expertRecognitionBySlicingId.getRecoResultId();
                                    history.setExpertScore(boneageResultService.getBoneageByRecoId(recoResultIdByExpert).getChnBoneage());
                                } else {
                                    history.setExpertScore((double) -1);
                                }
                                history.setUpdateTime(slicing.getUpdateTime());
                                histories.add(history);
                            }
                        }
                    }
                    return R.ok().data("items", histories);
                }
            } else {
                return R.error();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.error();
    }

    @ApiOperation("查询用户历史信息")
    @PostMapping("/searchHis")
    public R searchHis(@RequestBody HisQuery hisQuery){
        QueryWrapper<YouthInfo> youthInfoQueryWrapper = new QueryWrapper<>();
        String name = hisQuery.getYouthName();
        String cardId = hisQuery.getYouthCardId();
        String begin = hisQuery.getBegin();
        String end = hisQuery.getEnd();
        Integer userId = hisQuery.getUserId();
        //判断条件，进行拼接
        if(!StringUtils.isEmpty(name)) {
            //构建条件
            youthInfoQueryWrapper.like("youth_name","%"+name+"%");
        }
        if(!StringUtils.isEmpty(cardId)) {
            //构建条件
            youthInfoQueryWrapper.eq("youth_card_id",cardId);
        }
        if(!StringUtils.isEmpty(userId)) {
            //构建条件
            youthInfoQueryWrapper.eq("user_id",userId);
        }
        List<History> histories = new ArrayList<>();
        try {
            List<YouthInfo> youthInfos = youthInfoService.list(youthInfoQueryWrapper);
            if (youthInfos == null) {
                return R.ok().message("无绑定孩子");
            } else {
                for (YouthInfo youthInfo : youthInfos) {
                    int youthId = youthInfo.getYouthId();
                    QueryWrapper<Slicing> slicingQueryWrapper = new QueryWrapper<>();

                    if(!StringUtils.isEmpty(begin)) {
                        slicingQueryWrapper.ge("create_time",begin);
                    }
                    if(!StringUtils.isEmpty(end)) {
                        slicingQueryWrapper.le("create_time",end);
                    }
                    slicingQueryWrapper.eq("youth_id",youthId);
                    List<Slicing> slicingList = slicingInfoService.list(slicingQueryWrapper);
                    if (slicingList != null) {
                        for (Slicing slicing : slicingList) {
                            History history = new History();
                            history.setYouthName(youthInfo.getYouthName());
                            history.setYouthCardId(youthInfo.getYouthCardId());
                            history.setSlicingId(slicing.getSlicingId());
                            history.setPhysicalTime(slicing.getPhysicalTime());
                            RecognitionResult aiRecognitionBySlicingId = recognitionService.getAIRecognitionBySlicingId(slicing.getSlicingId());
                            if (aiRecognitionBySlicingId != null) {
                                int recoResultIdByAI = aiRecognitionBySlicingId.getRecoResultId();
                                history.setAiScore(boneageResultService.getBoneageByRecoId(recoResultIdByAI).getChnBoneage());
                                history.setHeightForecast(heightForecastService.getHeightByRecoId(recoResultIdByAI).getFinalHeightImprove());
                            } else {
                                history.setAiScore((double) -1);
                                history.setHeightForecast((double) -1);
                            }
                            RecognitionResult expertRecognitionBySlicingId = recognitionService.getExpertRecognitionBySlicingId(slicing.getSlicingId());
                            if (expertRecognitionBySlicingId != null) {
                                int recoResultIdByExpert = expertRecognitionBySlicingId.getRecoResultId();
                                history.setExpertScore(boneageResultService.getBoneageByRecoId(recoResultIdByExpert).getChnBoneage());
                            } else {
                                history.setExpertScore((double) -1);
                            }
                            history.setUpdateTime(slicing.getUpdateTime());
                            histories.add(history);
                        }
                    }
                }
                return R.ok().data("items", histories);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.error();
    }

}
