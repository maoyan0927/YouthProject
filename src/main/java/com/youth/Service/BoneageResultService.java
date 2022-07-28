package com.youth.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youth.Entity.BoneageResult;

import java.util.List;

public interface BoneageResultService extends IService<BoneageResult> {

    BoneageResult getBoneageByRecoId(Integer recoId);

}
