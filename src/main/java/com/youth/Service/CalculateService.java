package com.youth.Service;

public interface CalculateService {

    //CHN根据骨龄和14块骨头获取分数
    int selectChnScoreBySexAndNameRank(String boneType, Integer sex, Float rank);

    //获取CHN分数
    int getChnBoneScore(Integer sex, float score);

    //TW3根据骨龄和14块骨头获取分数
    int selectTw3ScoreBySexAndNameRank(String boneType,Integer sex,Float rank);

    //获取TW3分数
    int getTw3BoneScore(Integer sex,float score);

    //获取TWC分数
    int getTwcBoneScore(Integer sex,float score);

    String BoneSituation(double age,double boneAge);
}
