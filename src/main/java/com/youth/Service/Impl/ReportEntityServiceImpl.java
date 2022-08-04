package com.youth.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youth.Entity.vo.ReportEntity;
import com.youth.Service.ReportEntityService;
import com.youth.mapper.ReportEntityMapper;
import org.springframework.stereotype.Service;

@Service
public class ReportEntityServiceImpl extends ServiceImpl<ReportEntityMapper, ReportEntity> implements ReportEntityService {

}
