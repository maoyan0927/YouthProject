package com.youth.Controller;


import com.alibaba.fastjson.JSON;
import com.youth.Entity.*;
import com.youth.Entity.vo.HeightEntity;
import com.youth.Service.*;
import com.youth.Util.HttpAPIUtil;
import com.youth.Util.R;
import com.youth.Util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @ApiOperation("提交审核")
    @PostMapping("/addVerify")
    public R addVerify(@RequestBody TaskInfo taskInfo){
        taskInfo.setState(0);
        boolean save = taskService.save(taskInfo);
        if (save){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation("专家未审核列表")
    @GetMapping("/NotVerifyList/{expertId}")
    public R NotVerifyList(@PathVariable Integer expertId){
        List<RecognitionResult> recognitionResults = recognitionService.getInVerifyList(expertId);
        List<RecognitionResult> alRecognitionResults = recognitionService.getInVerifyList(0);
        List<RecognitionResult> notVerifyList = new ArrayList<>();
        List<Integer> num = new ArrayList<>();
        for(RecognitionResult recognitionResult:recognitionResults){
            num.add(recognitionResult.getSlicingId());
        }
        for(RecognitionResult recognitionResult:alRecognitionResults){
            if(!num.contains(recognitionResult.getSlicingId())){
                notVerifyList.add(recognitionResult);
            }
        }
        return R.ok().data("notInVerifyList",notVerifyList);
    }

    @ApiOperation("专家已审核列表")
    @GetMapping("/InVerifyList/{expertId}")
    public R InVerifyList(@PathVariable Integer expertId){
        List<RecognitionResult> recognitionResults = recognitionService.getInVerifyList(expertId);
        return R.ok().data("inVerifyList",recognitionResults);
    }


    @ApiOperation("专家提交审核")
    @PostMapping("/expertSumbit")
    public R expertSubmit(@RequestBody RecognitionResult recognitionResult){
        try{
            Slicing slicing = slicingInfoService.getById(recognitionResult.getSlicingId());
            slicing.setState(3);
            boolean b = slicingInfoService.updateById(slicing);
            if (!b){
                return R.error().message("切片状态更新失败");
            }
            recognitionResult.setState(0);
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
                    boneageResult.setState(0);
                    boolean save = boneageResultService.save(boneageResult);
                    if(save){
                        Map<String, Object> heightParam = new HashMap<>();
                        heightParam.put("age",StringUtil.getAge(youthInfo.getYouthBirth(), slicing.getPhysicalTime()));
                        heightParam.put("boneage",Double.parseDouble(data.get("chn_boneage").toString()));
                        String[] sexArray = {"男", "女"};
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
                            heightForecastResult.setStageHeight(heightEntity.getStateheight());
                            heightForecastResult.setGeneticHeight(heightEntity.getTarget());
                            heightForecastResult.setGrowthTrend(StringUtil.jsonArrayToString(heightEntity.getForecast()));
                            heightForecastResult.setEvaluate(heightEntity.getMessage());
                            heightForecastResult.setState(0);//未修改
                            boolean save1 = heightForecastService.save(heightForecastResult);
                            if(save1){
                                return R.ok().data("boneageResult",boneageResult);
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

    @ApiOperation("专家更新审核")
    @PostMapping("/expertUpdateSumbit")
    public R updateSubmit(@RequestBody RecognitionResult recognitionResult) throws Exception {
        Slicing slicing = slicingInfoService.getById(recognitionResult.getSlicingId());
        boolean updateById = recognitionService.updateById(recognitionResult);
        if(updateById){
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
                BoneageResult boneageResult = boneageResultService.getBoneageByRecoId(recognitionResult.getRecoResultId());
                boneageResult.setChnBoneage(Double.parseDouble(data.get("chn_boneage").toString()));
                boneageResult.setTw3Boneage(Double.parseDouble(data.get("tw3_boneage").toString()));
                boneageResult.setTwcBoneage(Double.parseDouble(data.get("twc_boneage").toString()));
                boneageResult.setSituation(data.get("situation").toString());
                boneageResult.setState(1);
                boolean boneageUpdate = boneageResultService.updateById(boneageResult);
                if (boneageUpdate){
                    Map<String, Object> heightParam = new HashMap<>();
                    heightParam.put("age",StringUtil.getAge(youthInfo.getYouthBirth(), slicing.getPhysicalTime()));
                    heightParam.put("boneage",Double.parseDouble(data.get("chn_boneage").toString()));
                    String[] sexArray = {"男", "女"};
                    heightParam.put("sex",sexArray[youthInfo.getYouthSex()]);
                    heightParam.put("height",slicing.getYouthHeight());
                    heightParam.put("weight", slicing.getYouthWeight());
                    heightParam.put("yichuanx",youthInfo.getYouthMotherHeight());
                    heightParam.put("yichuany",youthInfo.getYouthFatherHeight());
                    HttpResult heightResult = httpAPIUtil.doGet(heightPlotUrl, heightParam);
                    if(heightResult.getCode() ==200){
                        HeightEntity heightEntity = JSON.parseObject(heightResult.getBody(), HeightEntity.class);
                        HeightForecastResult heightForecastResult = heightForecastService.getHeightByRecoId(recognitionResult.getRecoResultId());
                        heightForecastResult.setFinalHeightBp(heightEntity.getBp());
                        heightForecastResult.setFinalHeightImprove(heightEntity.getBp2());
                        heightForecastResult.setStageHeight(heightEntity.getStateheight());
                        heightForecastResult.setGeneticHeight(heightEntity.getTarget());
                        heightForecastResult.setGrowthTrend(StringUtil.jsonArrayToString(heightEntity.getForecast()));
                        heightForecastResult.setEvaluate(heightEntity.getMessage());
                        heightForecastResult.setState(1);//修改
                        boolean b = heightForecastService.updateById(heightForecastResult);
                        if(b){
                            return R.ok().message("修改成功").data("boneageResult",boneageResult);
                        }
                    }
                }
            }
        }
        return R.error();
    }

    @ApiOperation("专家添加建议")
    @PostMapping(value = "/addSuggestion")
    @ResponseBody
    public R addSuggestion(@PathVariable String text,
                           @PathVariable Integer recoId,
                           @PathVariable Integer slicingId) {
        try {
            BoneageResult boneageResult = boneageResultService.getBoneageByRecoId(recoId);
            if (boneageResult != null) {
                boneageResult.setExpertSuggestion(text);
                boolean b = boneageResultService.updateById(boneageResult);
                if (b) {
                    Slicing slicing = slicingInfoService.getById(slicingId);
                    slicing.setState(5);
                    boolean b1 = slicingInfoService.updateById(slicing);
                    if(b1){
                       return R.ok();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return R.error();
    }

    @ApiOperation("专家修改建议")
    @PostMapping(value = "/updateSuggestion/{recoId}/{text}")
    @ResponseBody
    public R updateSuggestion(@PathVariable("text") String text,
                           @PathVariable("recoId") Integer recoId) {
        try {
            BoneageResult boneageResult = boneageResultService.getBoneageByRecoId(recoId);
            if (boneageResult != null) {
                boneageResult.setExpertSuggestion(text);
                boolean b = boneageResultService.updateById(boneageResult);
                if (b) {
                    return R.ok();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return R.error();
    }

}
