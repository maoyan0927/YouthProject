package com.youth.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.youth.Entity.*;
import com.youth.Entity.vo.HisCheckQuery;
import com.youth.Entity.vo.HisQuery;
import com.youth.Entity.vo.ReportEntity;
import com.youth.Service.*;
import com.youth.Util.R;
import com.youth.Util.StringUtil;
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

    @Autowired
    ReportEntityService reportEntityService;


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
                                history.setYouthId(youthInfo.getYouthId());
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
    public R searchHis(@RequestBody HisQuery hisQuery) {
        QueryWrapper<YouthInfo> youthInfoQueryWrapper = new QueryWrapper<>();
        String name = hisQuery.getYouthName();
        String cardId = hisQuery.getYouthCardId();
        String begin = hisQuery.getBegin();
        String end = hisQuery.getEnd();
        Integer userId = hisQuery.getUserId();
        //判断条件，进行拼接
        if (!StringUtils.isEmpty(name)) {
            //构建条件
            youthInfoQueryWrapper.like("youth_name", "%" + name + "%");
        }
        if (!StringUtils.isEmpty(cardId)) {
            //构建条件
            youthInfoQueryWrapper.eq("youth_card_id", cardId);
        }
        if (!StringUtils.isEmpty(userId)) {
            //构建条件
            youthInfoQueryWrapper.eq("user_id", userId);
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

                    if (!StringUtils.isEmpty(begin)) {
                        slicingQueryWrapper.ge("update_time", begin);
                    }
                    if (!StringUtils.isEmpty(end)) {
                        slicingQueryWrapper.le("update_time", end);
                    }
                    slicingQueryWrapper.eq("youth_id", youthId);
                    List<Slicing> slicingList = slicingInfoService.list(slicingQueryWrapper);
                    if (slicingList != null) {
                        for (Slicing slicing : slicingList) {
                            History history = new History();
                            history.setYouthId(youthInfo.getYouthId());
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

    @ApiOperation("根据slicingId查询详细报告")
    @GetMapping("/details/{slicingId}")
    public R getDetails(@PathVariable Integer slicingId) {

        QueryWrapper<ReportEntity> reportEntityQueryWrapper = new QueryWrapper<>();
        reportEntityQueryWrapper.eq("slicing_id", slicingId);
        reportEntityQueryWrapper.orderByDesc("update_time");
        List<ReportEntity> list = reportEntityService.list(reportEntityQueryWrapper);
        List<Curve> curveList = new ArrayList<>();
        for (ReportEntity r : list) {
            Curve curve = new Curve();
            List<List<Float>> point = StringUtil.stringToArray(r != null ? r.getGrowthTrend() : null);
            curve.setBp(r != null ? r.getFinalHeightBp() : null);
            curve.setBp2(r != null ? r.getFinalHeightImprove() : null);
            curve.setTarget(r != null ? r.getGeneticHeight() : null);
            curve.setPoints(point);
            curveList.add(curve);
        }
        return R.ok().data("items", list).data("dialogData", curveList);

    }

    @ApiOperation("根据slicingId查询简略报告")
    @GetMapping("/simple/{expertId}")
    public R getSimple(@PathVariable Integer expertId) {
        List<History> histories = new ArrayList<>();
        QueryWrapper<Slicing> slicingQueryWrapper = new QueryWrapper<>();
        slicingQueryWrapper.gt("state", 2);
        slicingQueryWrapper.orderByDesc("update_time");
        List<Slicing> slicingList = slicingInfoService.list(slicingQueryWrapper);
        for (Slicing slicing : slicingList) {
            History history = new History();
            //添加基础信息
            history.setYouthId(slicing.getYouthId());
            history.setYouthWeight(slicing.getYouthWeight());
            history.setYouthHeight(slicing.getYouthHeight());
            history.setSlicingId(slicing.getSlicingId());
            history.setPhysicalTime(slicing.getPhysicalTime());

            QueryWrapper<ReportEntity> reportEntityQueryWrapper = new QueryWrapper<>();
            reportEntityQueryWrapper.eq("slicing_id", slicing.getSlicingId());
            reportEntityQueryWrapper.eq("expert_id", 0);
            reportEntityQueryWrapper.last("LIMIT 1");
            ReportEntity reportEntityByAi = reportEntityService.getOne(reportEntityQueryWrapper);
            //添加基础信息
            history.setYouthCardId(reportEntityByAi.getYouthCardId());
            history.setYouthName(reportEntityByAi.getYouthName());
            history.setYouthSex(reportEntityByAi.getYouthSex() == 1 ? "男" : "女");
            //添加年龄
            history.setYouthAge(String.format("%.1f", StringUtil.getAge(reportEntityByAi.getYouthBirth(), reportEntityByAi.getPhysicalTime())));
            //添加AI分数
            history.setAiScore(reportEntityByAi.getChnBoneage());


            QueryWrapper<ReportEntity> reportEntityQueryWrapper1 = new QueryWrapper<>();
            reportEntityQueryWrapper1.eq("expert_id", expertId);
            reportEntityQueryWrapper1.eq("slicing_id", slicing.getSlicingId());
            reportEntityQueryWrapper1.orderByDesc("update_time");
            ReportEntity reportEntityByEx = reportEntityService.getOne(reportEntityQueryWrapper1);
            if (reportEntityByEx == null) {
                QueryWrapper<ReportEntity> reportEntityQueryWrapper2 = new QueryWrapper<>();
                reportEntityQueryWrapper2.ne("expert_id", 0);
                reportEntityQueryWrapper2.eq("slicing_id", slicing.getSlicingId());
                reportEntityQueryWrapper2.orderByDesc("update_time");
                reportEntityQueryWrapper2.last("LIMIT 1");
                reportEntityByEx = reportEntityService.getOne(reportEntityQueryWrapper2);
            }
            //添加专家打分
            history.setExpertScore(reportEntityByEx.getChnBoneage());
            //添加身高预测
            history.setHeightForecast(reportEntityByEx.getFinalHeightImprove());
            history.setExpertId(reportEntityByEx.getExpertId());
            history.setRecoResultId(reportEntityByEx.getRecoResultId());
            history.setUpdateTime(reportEntityByEx.getUpdateTime());
            histories.add(history);
        }
        return R.ok().data("items", histories);
    }

    @ApiOperation("根据搜索信息slicingId查询简略报告")
    @PostMapping("/simpleQuery")
    public R querySimple(@RequestBody HisCheckQuery hisCheckQuery) {
        List<History> histories = new ArrayList<>();
        String name = hisCheckQuery.getYouthName();
        String cardId = hisCheckQuery.getYouthCardId();
        String begin = hisCheckQuery.getBegin();
        String end = hisCheckQuery.getEnd();
        Integer expertId = hisCheckQuery.getExpertId();
        boolean isOnlyMe = hisCheckQuery.getIsOnlyMe();
        QueryWrapper<YouthInfo> youthInfoQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            //构建条件
            youthInfoQueryWrapper.like("youth_name", "%" + name + "%");
        }
        if (!StringUtils.isEmpty(cardId)) {
            //构建条件
            youthInfoQueryWrapper.eq("youth_card_id", cardId);
        }
        List<YouthInfo> youthInfos = youthInfoService.list(youthInfoQueryWrapper);
        List<Slicing> slicingList = new ArrayList<>();
        for (YouthInfo youthInfo : youthInfos) {
            QueryWrapper<Slicing> slicingQueryWrapper = new QueryWrapper<>();
            if (!StringUtils.isEmpty(begin)) {
                slicingQueryWrapper.ge("update_time", begin);
            }
            if (!StringUtils.isEmpty(end)) {
                slicingQueryWrapper.le("update_time", end);
            }
            slicingQueryWrapper.eq("youth_id", youthInfo.getYouthId());
            slicingQueryWrapper.gt("state", 2);
            slicingQueryWrapper.orderByDesc("update_time");
            List<Slicing> slicings = slicingInfoService.list(slicingQueryWrapper);
            slicingList.addAll(slicings);
        }
        for (Slicing slicing : slicingList) {
            History history = new History();
            QueryWrapper<ReportEntity> reportEntityQueryWrapper1 = new QueryWrapper<>();
            reportEntityQueryWrapper1.eq("expert_id", expertId);
            reportEntityQueryWrapper1.eq("slicing_id", slicing.getSlicingId());
            reportEntityQueryWrapper1.orderByDesc("update_time");
            ReportEntity reportEntityByEx = reportEntityService.getOne(reportEntityQueryWrapper1);
            if (reportEntityByEx == null) {
                if (isOnlyMe) {
                    continue;
                } else {
                    QueryWrapper<ReportEntity> reportEntityQueryWrapper2 = new QueryWrapper<>();
                    reportEntityQueryWrapper2.ne("expert_id", 0);
                    reportEntityQueryWrapper2.eq("slicing_id", slicing.getSlicingId());
                    reportEntityQueryWrapper2.orderByDesc("update_time");
                    reportEntityQueryWrapper2.last("LIMIT 1");
                    reportEntityByEx = reportEntityService.getOne(reportEntityQueryWrapper2);
                }
            }
            //添加专家打分
            history.setExpertScore(reportEntityByEx.getChnBoneage());
            //添加身高预测
            history.setHeightForecast(reportEntityByEx.getFinalHeightImprove());
            history.setExpertId(reportEntityByEx.getExpertId());
            history.setRecoResultId(reportEntityByEx.getRecoResultId());
            history.setUpdateTime(reportEntityByEx.getUpdateTime());
            //添加基础信息
            history.setYouthId(slicing.getYouthId());
            history.setYouthWeight(slicing.getYouthWeight());
            history.setYouthHeight(slicing.getYouthHeight());
            history.setSlicingId(slicing.getSlicingId());
            history.setPhysicalTime(slicing.getPhysicalTime());

            QueryWrapper<ReportEntity> reportEntityQueryWrapper = new QueryWrapper<>();
            reportEntityQueryWrapper.eq("slicing_id", slicing.getSlicingId());
            reportEntityQueryWrapper.eq("expert_id", 0);
            reportEntityQueryWrapper.last("LIMIT 1");
            ReportEntity reportEntityByAi = reportEntityService.getOne(reportEntityQueryWrapper);
            //添加基础信息
            history.setYouthCardId(reportEntityByAi.getYouthCardId());
            history.setYouthName(reportEntityByAi.getYouthName());
            history.setYouthSex(reportEntityByAi.getYouthSex() == 1 ? "男" : "女");
            //添加年龄
            history.setYouthAge(String.format("%.1f", StringUtil.getAge(reportEntityByAi.getYouthBirth(), reportEntityByAi.getPhysicalTime())));
            //添加AI分数
            history.setAiScore(reportEntityByAi.getChnBoneage());

            histories.add(history);

        }
        return R.ok().data("items", histories);
    }


}
