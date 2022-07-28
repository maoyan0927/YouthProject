package com.youth.Controller;

import com.youth.Entity.vo.CalculateEntity;
import com.youth.Service.CalculateService;
import com.youth.Util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.ListOrderedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RequestMapping("/")
@RestController
@Slf4j
@Api(tags = "骨龄计算类")
@CrossOrigin
public class CalculateController {

    @Autowired
    CalculateService calculateService;

    @ApiOperation("计算骨龄值")
    @GetMapping( "/calculate")
    public R calculate(@RequestParam Map<String,Object> info) {
        if(info.size()!=17){
            return R.error();
        }else{
            Map<String,Object> result = new ListOrderedMap<>();
            int sex = Integer.parseInt(info.get("sex").toString());
            double age = Double.parseDouble(info.get("age").toString());
            Set<String> keys = info.keySet();
            keys.remove("sex");
            keys.remove("age");
            Integer sumScoreChn = 0;
            Integer sumScoreTw3 = 0;
            for(String bonetype:keys){
                float rank = Float.parseFloat(info.get(bonetype).toString());
                sumScoreChn += calculateService.selectChnScoreBySexAndNameRank(bonetype,sex,rank);
                sumScoreTw3 += calculateService.selectTw3ScoreBySexAndNameRank(bonetype,sex,rank);
            }
            /*根据总成熟度得分计算骨龄*/
            double boneAge = calculateService.getChnBoneScore(sex,sumScoreChn);
            double boneAgeTw3 = calculateService.getTw3BoneScore(sex,sumScoreTw3);
            double boneAgeTwc = calculateService.getTwcBoneScore(sex, sumScoreTw3);

            result.put("chn_boneage",boneAge);
            result.put("tw3_boneage",boneAgeTw3);
            result.put("twc_boneage",boneAgeTwc);
            result.put("situation",calculateService.BoneSituation(age,boneAge));

            return R.ok().data(result);
        }
    }
}
