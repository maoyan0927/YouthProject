package com.youth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.xml.internal.xsom.impl.scd.Iterators;
import com.youth.Entity.ExpertInfo;
import com.youth.Service.AdminInfoService;
import com.youth.Service.ExpertInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Wrapper;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class IServiceTest {
    @Autowired
    private ExpertInfoService expertInfoService;

    @Test
    void test1() throws Exception{
        QueryWrapper<ExpertInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("expert_name","test2");
        ExpertInfo expertInfo = expertInfoService.getOne(queryWrapper);
        System.out.println(expertInfo);

    }

}
