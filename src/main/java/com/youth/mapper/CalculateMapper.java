package com.youth.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CalculateMapper {

    /*
       根据骨龄和14块骨头获取分数（chn）
    */
    int selectScoreBySexAndNameRank(Integer sex, String nameRank);
    /*
       获取分数（chn）
    */
    int getBoneScore(Integer sex, float score);
    /*
      根据骨龄和14块骨头获取分数
   */
    int selectTw3ScoreBySexAndNameRank(Integer sex,String nameRank);
    /*
       获取分数
    */
    int getTw3BoneScore(Integer sex, float score);


    int getTwcBoneScore(Integer sex, float score);
}
