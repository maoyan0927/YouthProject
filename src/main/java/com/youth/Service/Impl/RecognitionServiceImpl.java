package com.youth.Service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youth.Entity.*;
import com.youth.Entity.vo.AnalysisParm;
import com.youth.Entity.vo.CutEntity;
import com.youth.Entity.vo.HeightEntity;
import com.youth.Entity.vo.RecoEntity;
import com.youth.Service.*;
import com.youth.Util.EnDecoderUtil;
import com.youth.Util.HttpAPIUtil;
import com.youth.Util.ResultEnum;
import com.youth.Util.StringUtil;
import com.youth.mapper.RecognitionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;

@Service
@Slf4j
public class RecognitionServiceImpl extends ServiceImpl<RecognitionMapper, RecognitionResult> implements RecognitionService{

    @Autowired
    RecognitionMapper recognitionMapper;

    @Autowired
    SlicingInfoService slicingInfoService;

    @Autowired
    YouthInfoService youthInfoService;

    @Autowired
    BoneageResultService boneageResultService;

    @Autowired
    HeightForecastService heightForecastService;

    @Autowired
    RecognitionService recognitionService;

    @Autowired
    HttpAPIUtil httpAPIUtil;

    @Value("${url.slice}")
    String sliceUrl;

    @Value("${url.reco}")
    String recoUrl;

    @Value("${url.heightplot}")
    String heightPlotUrl;

    @Value("${des.key}")
    String key;

    @Override
    public int add(RecognitionResult RecognitionResult) {
        int result = recognitionMapper.add(RecognitionResult);
        if (result > 0) {
            return RecognitionResult.getRecoResultId();
        }else {
            return result;
        }
    }

    @Override
    public ResultEnum analysisProcess(AnalysisParm analysisParm) {
        try {
            log.info("recogSId:" + analysisParm.getSlicingId());
            ResultEnum result = getSlicePathAndSql(analysisParm.getSlicingId(), analysisParm.getHeight(), analysisParm.getWeight(), analysisParm.getYouthId());
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultEnum.DEFAULT_ERROR;
    }

    @Override
    public List<RecognitionResult> getRecognitionBySlicingId(Integer slicingId) {
        return recognitionMapper.getRecognitionBySlicingId(slicingId);
    }

    private ResultEnum getSlicePathAndSql(Integer slicingId, Double height, Double weight, Integer youthId) {
        try{
            //查询 获取接口参数信息
            Slicing slice = slicingInfoService.getById(slicingId);
            if (slice == null) {
                return ResultEnum.NULL_ERROR;
            }else {
                Map<String, Object> param = new HashMap<>();
                String strPath = slice.getDicomPath();
                String [] array = strPath.split("/");
                String str = array[array.length-2];
                param.put("dir", str);
                param.put("path", str);
                HttpResult result = httpAPIUtil.doGet(sliceUrl + "/" + str + "/" + str);
                if (result.getCode() == 200) {
                    log.debug("切割接口请求成功");
                    CutEntity cutEntity = JSON.parseObject(result.getBody(), CutEntity.class);
                    if (cutEntity.getCode() == 0) {//todo
                        log.debug("切割接口返回正确");
                        String slicePath = cutEntity.getData();
                        Slicing sliceinfo = new Slicing();
                        sliceinfo.setSlicePath(slicePath);
                        sliceinfo.setSlicingId(slicingId);
                        sliceinfo.setYouthHeight(height);
                        sliceinfo.setYouthWeight(weight);
                        sliceinfo.setYouthId(youthId);
                        sliceinfo.setState(1);
                        boolean sliceTrue = slicingInfoService.updateById(sliceinfo);
                        log.debug("切割接口返回数据：" + cutEntity.getData());
                        if (sliceTrue) {
                            log.debug("slice更新成功");
                            ResultEnum recoResult = getAnalysisResultAndSql(slicePath, youthId, slicingId);
                            return recoResult;
                        }else {
                            return ResultEnum.SLICE_UPDATE_ERROR;
                        }
                    }else {
                        return ResultEnum.SLICE_INTERFACE_ERROR;
                    }
                }else {
                    return ResultEnum.HTTP_ERROR;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ResultEnum.DEFAULT_ERROR;
    }

    private ResultEnum getAnalysisResultAndSql(String slicePath, Integer youthId, Integer slicingId) {
        try {
            YouthInfo youth = youthInfoService.getById(youthId);
            Slicing slice = slicingInfoService.getById(slicingId);
            if (youth == null) {
                return ResultEnum.NULL_ERROR;
            }else {
                log.debug("youth读取成功");
                Map<String, Object> param = new HashMap<>();
                param.put("image_path", slicePath);
                param.put("sex", youth.getYouthSex()); //0男  1女
                param.put("age", StringUtil.getAge(youth.getYouthBirth(), slice.getPhysicalTime()));

                HttpResult result = httpAPIUtil.doGet(recoUrl, param);
                if (result.getCode() == 200) {
                    RecoEntity recoEntity = JSON.parseObject(result.getBody(), RecoEntity.class);

                    if (recoEntity.getCode().equals("success")) {
                        log.debug("识别接口请求正确");
                        log.debug("识别接口返回内容:" + recoEntity.getData());
                        RecognitionResult recognization = new RecognitionResult();
                        recognization.setRaoRank(recoEntity.getData().getRao());
                        recognization.setTouRank(recoEntity.getData().getTou());
                        recognization.setGouRank(recoEntity.getData().getGou());
                        //recognization.setChiRank(recoEntity.getData().getChi());
                        recognization.setChiRank(0);
                        recognization.setZhang1Rank(recoEntity.getData().getZhang1());
                        recognization.setZhang3Rank(recoEntity.getData().getZhang3());
                        recognization.setZhang5Rank(recoEntity.getData().getZhang5());
                        recognization.setJin1Rank(recoEntity.getData().getJin1());
                        recognization.setJin3Rank(recoEntity.getData().getJin3());
                        recognization.setJin5Rank(recoEntity.getData().getJin5());
                        recognization.setZhong3Rank(recoEntity.getData().getZhong3());
                        recognization.setZhong5Rank(recoEntity.getData().getZhong5());
                        recognization.setYuan1Rank(recoEntity.getData().getYuan1());
                        recognization.setYuan3Rank(recoEntity.getData().getYuan3());
                        recognization.setYuan5Rank(recoEntity.getData().getYuan5());
                        recognization.setSlicingId(slicingId);
                        recognization.setKind(1); //ai
                        recognization.setState(0);
                        int recoId = add(recognization);

                        if (recoId > 0){
                            log.debug("识别信息入库成功");
                            //分值入库
                            BoneageResult boneageResult = new BoneageResult();
                            boneageResult.setRecoResultId(recoId);
                            boneageResult.setChnBoneage(recoEntity.getData().getChnBoneage());
                            boneageResult.setTw3Boneage(recoEntity.getData().getTw3Boneage());
//                            boneageResult.setTwcBoneage(recoEntity.getData().getTwcBoneage());
                            boneageResult.setSituation(recoEntity.getData().getSituation());
                            boneageResult.setState(0);
                            boolean save = boneageResultService.save(boneageResult);
                            if (save) {
                                log.debug("骨龄信息入库成功");
                                //分数 骨龄入库
                                //调用身高接口 todo
                                ResultEnum heightResultEnum = getHeightPlotAndSql(youth, slicingId, recoId, recoEntity.getData().getChnBoneage());
                                return heightResultEnum;
                            }else {
                                return ResultEnum.SCORE_ADD_ERROR;
                            }
                        }else {
                            return ResultEnum.RECO_ADD_ERROR;
                        }
                    }
                }else {
                    return ResultEnum.HTTP_ERROR;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultEnum.DEFAULT_ERROR;
    }

    //身高接口调用  骨龄直接传
    private ResultEnum getHeightPlotAndSql(YouthInfo youth, Integer sliceId, Integer recoId, Double boneAge) {
        try {
            Slicing slice = slicingInfoService.getById(sliceId);
            if (slice == null) {
                return ResultEnum.NULL_ERROR;
            }else {
                log.debug("slice获取成功");
                Map<String, Object> param = new HashMap<>();
                //计算年龄替换数据todo
                param.put("age", StringUtil.getAge(youth.getYouthBirth(),slice.getPhysicalTime()));
                param.put("boneage", boneAge);
                String sexArray[] = {"男", "女"};
                param.put("sex", sexArray[youth.getYouthSex()]);
                param.put("height",slice.getYouthHeight());
                param.put("weight",slice.getYouthWeight());
                param.put("yichuanx", youth.getYouthMotherHeight());
                param.put("yichuany", youth.getYouthFatherHeight());

                HttpResult result = httpAPIUtil.doGet(heightPlotUrl, param);
                if (result.getCode() == 200) {
                    log.debug("身高接口调用成功:" + result.getBody());
                    HeightEntity heightEntity = JSON.parseObject(result.getBody(), HeightEntity.class);
                    HeightForecastResult heightInfo = new HeightForecastResult();
                    heightInfo.setRecoResultId(recoId);
                    heightInfo.setFinalHeightBp(heightEntity.getBp());
                    heightInfo.setFinalHeightImprove(heightEntity.getBp2());
                    heightInfo.setStageHeight(heightEntity.getStateheight());
                    heightInfo.setGeneticHeight(heightEntity.getTarget());
                    heightInfo.setGrowthTrend(StringUtil.jsonArrayToString(heightEntity.getForecast()));
                    heightInfo.setEvaluate(heightEntity.getMessage());
                    heightInfo.setState(0);
                    boolean save = heightForecastService.save(heightInfo);
                    if (save) {
                        log.debug("身高信息入库成功");
                        Slicing slicing = new Slicing();
                        slicing.setSlicingId(sliceId);
                        slicing.setState(2);
                        slicingInfoService.updateById(slicing);
                        return ResultEnum.CODE_OK;
                    }else {
                        return ResultEnum.HEIGHT_ADD_ERROR;
                    }
                }else {
                    return ResultEnum.HTTP_ERROR;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ResultEnum.DEFAULT_ERROR;
    }

    private ReportInfo getReport(String sliceId, Model model) {
        try {
            int type = 1;
            byte[] byteStr = Base64.getDecoder().decode(sliceId);
            byte[] decode_bytes = EnDecoderUtil.DESDecrypt(key, byteStr);
            Integer sliceIdInt = Integer.parseInt(new String(decode_bytes));
            ReportInfo report = new ReportInfo();
            Slicing slice = slicingInfoService.getById(sliceIdInt);
            if (slice != null) {
                if (slice.getState() >= 4) {
                    type = 2;
                }
                report.setWeight(slice.getYouthWeight());
                report.setHeight(slice.getYouthHeight());
                report.setCheckDate(slice.getPhysicalTime());
                Integer youthId = slice.getYouthId();
                YouthInfo youthInfo = youthInfoService.getById(youthId);
                List<RecognitionResult> recoList = getRecognitionBySlicingId(sliceIdInt);
                if (youthInfo != null && recoList.size() > 0) {
                    String sexArray[] = {"男", "女"};
                    report.setName(youthInfo.getYouthName());
                    report.setSex(sexArray[youthInfo.getYouthSex()]);
                    report.setBirth(youthInfo.getYouthBirth());
                    report.setSlicingId(StringUtil.encryption(slice.getSlicingId()));
                    report.setAge(StringUtil.getAge(youthInfo.getYouthBirth(), slice.getPhysicalTime()).doubleValue());
                    report.setState(slice.getState());
                    for (RecognitionResult recognization : recoList) {
                        Integer recoId = recognization.getRecoResultId();
                        BoneageResult boneageResult = boneageResultService.getBoneageByRecoId(recoId);
                        HeightForecastResult heightInfo = heightForecastService.getHeightByRecoId(recoId);
                        List<List<Float>> point = StringUtil.stringToArray(heightInfo!=null?heightInfo.getGrowthTrend():null);
                        //recog kind=1 ai kind=2 expert
                        if (recognization.getKind() == type) {
                            report.setChnBoneAge(boneageResult.getChnBoneage());
                            report.setTw3BoneAge(boneageResult.getTw3Boneage());
                            report.setTwcBoneAge(boneageResult.getTwcBoneage());
                            report.getCurveChn().setBp(heightInfo!=null?heightInfo.getFinalHeightBp():null);
                            report.getCurveChn().setBp2(heightInfo!=null?heightInfo.getFinalHeightImprove():null);
                            report.getCurveChn().setTarget(heightInfo!=null?heightInfo.getGeneticHeight():null);
                            report.getCurveChn().setPoints(point);
                            report.getCurveChn().setHeightEvaluate(heightInfo!=null?heightInfo.getEvaluate():null);//身高评价
                            report.setWholeAgeGrowth(boneageResult.getSituation());//骨龄评价
                            report.setExpertSuggest(boneageResult.getExpertSuggestion());//专家建议
                            if (model != null) {
                                model.addAttribute("recogStr", StringUtil.encryption(recognization.getRecoResultId()));
                            }
                        }
                    }
                    return report;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
