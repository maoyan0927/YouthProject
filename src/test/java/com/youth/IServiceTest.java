package com.youth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.sun.xml.internal.xsom.impl.scd.Iterators;
import com.youth.Entity.AdminInfo;
import com.youth.Entity.ExpertInfo;
import com.youth.Entity.User;
import com.youth.Service.AdminInfoService;
import com.youth.Service.ExpertInfoService;
import com.youth.Service.UserService;
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
    private AdminInfoService adminInfoService;
    @Autowired
    private UserService userService;
    @Autowired
    private ExpertInfoService expertInfoService;

    @Test
    void test1(){
//        QueryWrapper<ExpertInfo> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("expert_id",8);
//        queryWrapper.last("LIMIT 1");
//        List<ExpertInfo> list = expertInfoService.list();
//        System.out.println(list);
//        AdminInfo adminInfo = new AdminInfo();
//        adminInfo.setAdminAccount("123");
//        adminInfo.setAdminName("123");
//        adminInfo.setAdminPassword("123");
//        adminInfo.setAdminPhone("123");
//        adminInfo.setState(0);
//        boolean save = adminInfoService.save(adminInfo);
//        System.out.println(save);
//        AdminInfo adminInfoByAccount = adminInfoService.getAdminInfoByAccount("123");
//        System.out.println(adminInfoByAccount);
        User user = new User();
        user.setUserPassword("123");
        user.setUserPhone("15862397458");
        user.setUserName("三毛儿");
        user.setState(0);
        boolean save = userService.save(user);
        System.out.println(save);
        System.out.println(userService.getUserInfoByUserPhone("15862397458"));


    }

}
