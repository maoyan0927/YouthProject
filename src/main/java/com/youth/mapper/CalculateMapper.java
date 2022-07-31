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
    Double getBoneScore(Integer sex, float score);
    /*
      根据骨龄和14块骨头获取分数
   */
    int selectTw3ScoreBySexAndNameRank(Integer sex,String nameRank);
    /*
       获取分数
    */
    Double getTw3BoneScore(Integer sex, float score);


    Double getTwcBoneScore(Integer sex, float score);
}
