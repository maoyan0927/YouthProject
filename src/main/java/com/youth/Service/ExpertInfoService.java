package com.youth.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youth.Entity.ExpertInfo;

public interface ExpertInfoService extends IService<ExpertInfo> {
    ExpertInfo getExpertInfoByPhone(String phone);
}
