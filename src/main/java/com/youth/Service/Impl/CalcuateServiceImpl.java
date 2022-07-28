package com.youth.Service.Impl;

import com.youth.Service.CalculateService;
import com.youth.mapper.CalculateMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CalcuateServiceImpl implements CalculateService {

    @Autowired
    private CalculateMapper calculateMapper;

    @Override
    public int selectChnScoreBySexAndNameRank(String boneType, Integer sex, Float newrank) {
        String nameRank;
        Integer score;
        Integer rank = newrank.intValue();
        switch (boneType){
            case "jin1":
                nameRank = "近节指骨Ⅰ_" + rank.toString();
                break;

            case "jin3":
                nameRank = "近节指骨Ⅲ_" + rank.toString();
                break;

            case "jin5":
                nameRank = "近节指骨Ⅴ_" + rank.toString();
                break;

            case "rao":
                nameRank = "桡骨_" + rank.toString();
                break;

            case "zhang1":
                nameRank = "掌骨Ⅰ_" + rank.toString();
                break;

            case "zhang3":
                nameRank = "掌骨Ⅲ_" + rank.toString();
                break;

            case "zhang5":
                nameRank = "掌骨Ⅴ_" + rank.toString();
                break;

            case "zhong3":
                nameRank = "中节指骨Ⅲ_" + rank.toString();
                break;

            case "zhong5":
                nameRank = "中节指骨Ⅴ_" + rank.toString();
                break;

            case "yuan1":
                nameRank = "远节指骨Ⅰ_" + rank.toString();
                break;

            case "yuan3":
                nameRank = "远节指骨Ⅲ_" + rank.toString();
                break;

            case "yuan5":
                nameRank = "远节指骨Ⅴ_" + rank.toString();
                break;

            case "tou":
                nameRank = "头状骨_" + rank.toString();
                break;

            case "chi":
                nameRank = "尺骨_" + rank.toString();
                score = 0;                              //chn不计算尺骨
                return score;
            default://gou
                nameRank = "钩骨_" + rank.toString();

        }
        score = calculateMapper.selectScoreBySexAndNameRank(sex, nameRank);
        return score;
    }

    @Override
    public int getChnBoneScore(Integer sex, float score) {
        return calculateMapper.getBoneScore(sex,score);
    }

    @Override
    public int selectTw3ScoreBySexAndNameRank(String boneType, Integer sex, Float newrank) {
        String nameRank;
        Integer score;
        Integer rank = newrank.intValue();
        switch (boneType){
            case "jin1":
                nameRank = "近节指骨Ⅰ_" + rank.toString();
                break;

            case "jin3":
                nameRank = "近节指骨Ⅲ_" + rank.toString();
                break;

            case "jin5":
                nameRank = "近节指骨Ⅴ_" + rank.toString();
                break;

            case "rao":
                nameRank = "桡骨_" + rank.toString();
                break;

            case "zhang1":
                nameRank = "掌骨Ⅰ_" + rank.toString();
                break;

            case "zhang3":
                nameRank = "掌骨Ⅲ_" + rank.toString();
                break;

            case "zhang5":
                nameRank = "掌骨Ⅴ_" + rank.toString();
                break;

            case "zhong3":
                nameRank = "中节指骨Ⅲ_" + rank.toString();
                break;

            case "zhong5":
                nameRank = "中节指骨Ⅴ_" + rank.toString();
                break;

            case "yuan1":
                nameRank = "远节指骨Ⅰ_" + rank.toString();
                break;

            case "yuan3":
                nameRank = "远节指骨Ⅲ_" + rank.toString();
                break;

            case "yuan5":
                nameRank = "远节指骨Ⅴ_" + rank.toString();
                break;

            case "tou":
                nameRank = "头状骨_" + rank.toString();
                score = 0;                              //tw3不计算头状骨
                return score;

            case "chi":
                nameRank = "尺骨_" + rank.toString();
                if(rank==0){
                    score=0;
                }else {
                    score = score = calculateMapper.selectTw3ScoreBySexAndNameRank(sex,nameRank);
                }
                return score;

            default://gou
                nameRank = "钩骨_" + rank.toString();
                score = 0;                              //tw3不计算钩骨
                return score;
        }
        score = calculateMapper.selectTw3ScoreBySexAndNameRank(sex, nameRank);
        return score;
    }

    @Override
    public int getTw3BoneScore(Integer sex, float score) {
        return calculateMapper.getTw3BoneScore(sex,score);
    }

    @Override
    public int getTwcBoneScore(Integer sex, float score) {
        return calculateMapper.getTwcBoneScore(sex, score);
    }

    public String BoneSituation(double age,double boneAge){
        double gap = age-boneAge;
        String NOMAL ="正常发育";
        String DELAY ="延迟发育";
        String EARLY ="提早发育";

        if(gap>1){
            String situation="您的孩子发育情况属于"+DELAY+"："+"生活年龄为"+age+"岁，"+"CHN骨龄为"+boneAge+"岁，"+"差距为"+Double.valueOf(String.format("%.2f", gap ))+"岁。";
            return situation;
        }
        if(gap<-1){
            String situation="您的孩子发育情况属于"+EARLY+"："+"生活年龄为"+age+"岁，"+"CHN骨龄为"+boneAge+"岁，"+"差距为"+Double.valueOf(String.format("%.2f", gap ))+"岁。";
            return situation;
        }
        else {
            String situation="您的孩子发育情况属于"+NOMAL+"："+"生活年龄为"+age+"岁，"+"CHN骨龄为"+boneAge+"岁，"+"差距为"+Double.valueOf(String.format("%.2f", gap ))+"岁。";
            return situation;
        }
    }
}
