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
        ExpertInfo expertInfo = new ExpertInfo();
        expertInfo.setExpertName("test1");
        expertInfo.setExpertPassword("test1");
        expertInfo.setExpertPhone("test1");
        expertInfo.setState(1);
        boolean save = expertInfoService.save(expertInfo);
        System.out.println(save);

    }

}
