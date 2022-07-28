package com.youth.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youth.Entity.BoneageResult;
import com.youth.Service.BoneageResultService;
import com.youth.mapper.BoneageResultMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BoneageResultServiceImpl extends ServiceImpl<BoneageResultMapper, BoneageResult> implements BoneageResultService {

    @Autowired
    private BoneageResultMapper boneageResultMapper;

    @Override
    public BoneageResult getBoneageByRecoId(Integer recoId) {
        return boneageResultMapper.getBoneageByRecoId(recoId);
    }
}
