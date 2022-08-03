package com.youth.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youth.Entity.YouthInfo;

import java.util.List;

public interface YouthInfoService extends IService<YouthInfo> {

    List<YouthInfo> getYouthInfoByUserId(Integer userId);
    YouthInfo getYouthInfoByCardId(String cardId);
    List<YouthInfo> getYouthInfoByYouthNameAndUserId(String youthName,int userId);
    Boolean deleteYouthInfoByYouthId(int youthId);

}
