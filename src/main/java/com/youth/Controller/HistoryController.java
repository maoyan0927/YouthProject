package com.youth.Controller;

import com.youth.Entity.History;
import com.youth.Entity.Slicing;
import com.youth.Entity.YouthInfo;
import com.youth.Service.SlicingInfoService;
import com.youth.Service.YouthInfoService;
import com.youth.Util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/getHistoryData/{userId}")
    @ApiOperation("获取用户历史信息")
    public R getHistoryData(@PathVariable Integer userId){
        try{
            if(userId != null){
                List<YouthInfo> youthInfos = youthInfoService.getYouthInfoByUserId(userId);
                List<History> histories = new ArrayList<>();
                for(YouthInfo youthInfo:youthInfos){
                    int youthId = youthInfo.getYouthId();
                    List<Slicing> slicingList = slicingInfoService.getSlicingByYouthId(youthId);
                    for(Slicing slicing: slicingList){
                        History history = new History();
                        history.setName(youthInfo.getYouthName());
                        history.setSex(youthInfo.getYouthSex());
                        history.setSliceId(slicing.getSlicingId());
                        history.setUploadTime(slicing.getCreateTime());
                        history.setExpertState(slicing.getState());
                        histories.add(history);
                    }
                }
                return R.ok().data("histories",histories);
            }else{
                return R.error();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return R.error();
    }
}
