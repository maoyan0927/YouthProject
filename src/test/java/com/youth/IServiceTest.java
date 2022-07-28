package com.youth;

import com.youth.Entity.ExpertInfo;
import com.youth.Service.AdminInfoService;
import com.youth.Service.ExpertInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
@SpringBootTest
public class IServiceTest {
    @Autowired
    private ExpertInfoService expertInfoService;

    @Test
    void test1() throws Exception{
        ExpertInfo expertInfo = expertInfoService.getById(1);
        System.out.println(expertInfo);

    }

}
