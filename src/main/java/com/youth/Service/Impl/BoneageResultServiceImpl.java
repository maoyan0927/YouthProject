package com.youth.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youth.Entity.BoneageResult;
import com.youth.Service.BoneageResultService;
import com.youth.mapper.BoneageResultMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BoneageResultServiceImpl extends ServiceImpl<BoneageResultMapper, BoneageResult> implements BoneageResultService {
}
