package com.youth.Controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youth.Entity.RecognitionResult;
import com.youth.Entity.Slicing;
import com.youth.Entity.vo.AnalysisParm;
import com.youth.Service.*;
import com.youth.Util.R;
import com.youth.Util.ResultEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author maoyan
 * @date 2022/7/20 9:12
 */

@RequestMapping("/Recognition")
@RestController
@Slf4j
@Api(tags = "识别类")
@CrossOrigin
public class RecognitionController {
    @Autowired
    RecognitionService recognitionService;

    @Autowired
    SlicingInfoService slicingInfoService;

    @Autowired
    BoneageResultService boneageResultService;

    @Autowired
    HeightForecastService heightForecastService;

    @Autowired
    YouthInfoService youthInfoService;

    @ApiOperation("所有识别列表")
    @GetMapping("/findAllRecognition")
    public R findAllRecognition(){
        List<RecognitionResult> list = recognitionService.list(null);
        return R.ok().data("items", list);
    }


    @ApiOperation("识别算法")
    @PostMapping(value = "/analysis")
    public R analysis(@RequestBody AnalysisParm analysisParm) {
        Map<String, Object> resMap = new HashMap<>();
        try {
            Slicing slice = slicingInfoService.getById(analysisParm.getSlicingId());
            if (slice.getPhysicalTime() == null) {
                //update
                SimpleDateFormat sdf = new SimpleDateFormat("mm/DD/yyyy");
                Date checkTime = sdf.parse(analysisParm.getTime());
                Slicing slicing = new Slicing();
                slicing.setSlicingId(analysisParm.getSlicingId());
                slicing.setUpdateTime(checkTime);
                slicingInfoService.updateById(slicing);
            }
            ResultEnum resultEnum = recognitionService.analysisProcess(analysisParm);
            if (resultEnum.getCode() == 200) {
                resMap.put("code", 1);
            }else {
                resMap.put("code" , 0);
                resMap.put("msg", resultEnum.getMessage());
            }
        }catch (Exception e){
            resMap.put("code" , 0);
            e.printStackTrace();
        }
        return R.ok().data("resMap",resMap);
    }

    @ApiOperation("分页查询识别信息")
    @GetMapping("/pageRecognitionResult/{current}/{limit}")
    public R pageListRecognitionResult(@PathVariable long current,
                               @PathVariable long limit){
        //创建page对象
        Page<RecognitionResult> recognitionResultPage = new Page<>(current,limit);

        //实现分页方法
        recognitionService.page(recognitionResultPage,null);
        //获取总页数
        long total = recognitionResultPage.getTotal();
        //获取list集合
        List<RecognitionResult> records = recognitionResultPage.getRecords();

        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation("根据id查询识别记录")
    @GetMapping("/findRecognitionById/{recoId}")
    public R findRecognitionById(@PathVariable Integer recoId){
        RecognitionResult recognitionResult = recognitionService.getById(recoId);
        if(recognitionResult!=null){
            return R.ok().data("recognitionResult", recognitionResult);
        }else{
            return R.error();
        }
    }


}
