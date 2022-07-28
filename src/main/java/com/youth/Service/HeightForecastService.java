package com.youth.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youth.Entity.HeightForecastResult;

import java.util.List;

public interface HeightForecastService extends IService<HeightForecastResult> {
    HeightForecastResult getHeightByRecoId(Integer recoId);

}
