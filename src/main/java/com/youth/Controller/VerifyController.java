package com.youth.Controller;


import com.alibaba.fastjson.JSON;
import com.youth.Entity.*;
import com.youth.Entity.vo.HeightEntity;
import com.youth.Entity.vo.VerifyEntity;
import com.youth.Service.*;
import com.youth.Util.HttpAPIUtil;
import com.youth.Util.R;
import com.youth.Util.StringUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RequestMapping("/verify")
@RestController
@Slf4j
@Api(tags = "专家验证类")
@CrossOrigin
public class VerifyController {

    @Autowired
    private CalculateController calculateController;

    @Autowired
    private YouthInfoService youthInfoService;

    @Autowired
    private RecognitionService recognitionService;

    @Autowired
    private SlicingInfoService slicingInfoService;

    @Autowired
    HttpAPIUtil httpAPIUtil;

    @Autowired
    private TaskService taskService;

    @Autowired
    private BoneageResultService boneageResultService;

    @Autowired
    private HeightForecastService heightForecastService;

    @Value("${url.heightplot}")
    String heightPlotUrl;

    @PostMapping("/expertSumbit")
    public R expertSumbit(@RequestBody RecognitionResult recognitionResult,
                          @PathVariable Integer expertId,
                          @PathVariable Integer taskId){
        try{
            Slicing slicing = slicingInfoService.getById(recognitionResult.getSlicingId());
            recognitionResult.setState(0);
            recognitionResult.setKind(2);
            int recoId = recognitionService.add(recognitionResult);
            if (recoId > 0) {
                YouthInfo youthInfo = youthInfoService.getById(slicing.getYouthId());
                HashMap<String, Object> map = new HashMap<>();
                map.put("age", StringUtil.getAge(youthInfo.getYouthBirth(), slicing.getPhysicalTime()));
                map.put("sex", youthInfo.getYouthSex());
                map.put("rao", recognitionResult.getRaoRank());
                map.put("zhang1", recognitionResult.getZhang1Rank());
                map.put("zhang3", recognitionResult.getZhang3Rank());
                map.put("zhang5", recognitionResult.getZhang5Rank());
                map.put("jin1", recognitionResult.getJin1Rank());
                map.put("jin3", recognitionResult.getJin3Rank());
                map.put("jin5", recognitionResult.getJin5Rank());
                map.put("zhong3", recognitionResult.getZhong3Rank());
                map.put("zhong5", recognitionResult.getZhong5Rank());
                map.put("yuan1", recognitionResult.getYuan1Rank());
                map.put("yuan3", recognitionResult.getYuan3Rank());
                map.put("yuan5", recognitionResult.getYuan5Rank());
                map.put("tou", recognitionResult.getTouRank());
                map.put("gou", recognitionResult.getGouRank());
                map.put("chi", recognitionResult.getChiRank());
                R calculate = calculateController.calculate(map);
                if (calculate.getCode() == 20000) {
                    Map<String, Object> data = calculate.getData();
                    BoneageResult boneageResult = new BoneageResult();
                    boneageResult.setRecoResultId(recoId);
                    boneageResult.setChnBoneage(Double.parseDouble(data.get("chn_boneage").toString()));
                    boneageResult.setTw3Boneage(Double.parseDouble(data.get("tw3_boneage").toString()));
                    boneageResult.setTwcBoneage(Double.parseDouble(data.get("twc_boneage").toString()));
                    boneageResult.setSituation(data.get("situation").toString());
                    boneageResult.setState(1);
                    boolean save = boneageResultService.save(boneageResult);
                    if(save){
                        Map<String, Object> heightParam = new HashMap<>();
                        heightParam.put("age",StringUtil.getAge(youthInfo.getYouthBirth(), slicing.getPhysicalTime()));
                        heightParam.put("boneage",Double.parseDouble(data.get("chn_boneage").toString()));
                        String sexArray[] = {"男", "女"};
                        heightParam.put("sex",sexArray[youthInfo.getYouthSex()]);
                        heightParam.put("height",slicing.getYouthHeight());
                        heightParam.put("weight", slicing.getYouthWeight());
                        heightParam.put("yichuanx",youthInfo.getYouthMotherHeight());
                        heightParam.put("yichuany",youthInfo.getYouthFatherHeight());
                        HttpResult heightResult = httpAPIUtil.doGet(heightPlotUrl, heightParam);
                        if ((heightResult.getCode()==200)){
                            HeightEntity heightEntity = JSON.parseObject(heightResult.getBody(), HeightEntity.class);
                            HeightForecastResult heightForecastResult = new HeightForecastResult();
                            heightForecastResult.setRecoResultId(recoId);
                            heightForecastResult.setFinalHeightBp(heightEntity.getBp());
                            heightForecastResult.setFinalHeightImprove(heightEntity.getBp2());
                            heightForecastResult.setGeneticHeight(heightEntity.getTarget());
                            heightForecastResult.setGrowthTrend(StringUtil.jsonArrayToString(heightEntity.getForecast()));
                            heightForecastResult.setEvaluate(heightEntity.getMessage());
                            heightForecastResult.setState(0);
                            boolean save1 = heightForecastService.save(heightForecastResult);
                            if(save1){
                                int updateTaskState = taskService.updateTaskState(expertId, 1, taskId);
                                if (updateTaskState > 0 ){
                                    return R.ok();
                                }
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return R.error();
    }

}
