package com.youth;

import com.youth.Entity.Slicing;
import com.youth.Service.CalculateService;
import org.apache.commons.collections4.map.ListOrderedMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;
import java.util.Set;

/**
 * @author
 * @date 2022/7/28 14:26
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CalculateTest {
    @Autowired
    private CalculateService calculateService;

    @Test
    public void TestCalculate() throws Exception{
        Map<String,Object> map = new ListOrderedMap();
        map.put("age",1);
        map.put("sex",0);
        map.put("rao",1);
        map.put("zhang1",1);
        map.put("zhang3",1);
        map.put("zhang5",1);
        map.put("jin1",1);
        map.put("jin3",1);
        map.put("jin5",1);
        map.put("zhong3",1);
        map.put("zhong5",1);
        map.put("yuan1",1);
        map.put("yuan3",1);
        map.put("yuan5",1);
        map.put("tou",1);
        map.put("gou",1);
        map.put("chi",1);
        System.out.println(map);
        if(map.size()!=17){
            System.out.println("error");
        }else{
            Map<String,Object> result = new ListOrderedMap<>();
            int sex = Integer.parseInt(map.get("sex").toString());
            double age = Double.parseDouble(map.get("age").toString());
            Set<String> keys = map.keySet();
            keys.remove("sex");
            keys.remove("age");
            Integer sumScoreChn = 0;
            Integer sumScoreTw3 = 0;
            for(String bonetype:keys){
                float rank = Float.parseFloat(map.get(bonetype).toString());
                System.out.println(rank);
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

            System.out.println(result);
        }
    }

}
