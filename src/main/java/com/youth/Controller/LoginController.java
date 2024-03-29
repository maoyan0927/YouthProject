package com.youth.Controller;

import cn.hutool.json.JSON;
import com.youth.Entity.AdminInfo;
import com.youth.Entity.ExpertInfo;
import com.youth.Entity.User;
import com.youth.Entity.YouthInfo;
import com.youth.Entity.vo.LoginForm;
import com.youth.Service.*;
import com.youth.Util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

@RequestMapping("/auth")
@RestController
@Slf4j
@Api(tags = "登录控制器")
@CrossOrigin
public class LoginController {
    @Autowired
    private LoginService loginService;


    @ApiOperation("验证登录")
    @PostMapping("/login")
    public R login(@RequestBody LoginForm loginForm){
        if (loginForm.getRole()==1){
            User user = loginService.getUserInfoByUserPhone(loginForm.getUserPhone());
            if(user==null){
                return R.error().message("账号不存在！");
            }
//            System.out.println(user);
            if (loginForm.getUserPassword().equals(user.getUserPassword())){//登录成功
                user.setUserPassword("");
                return R.ok().data("user", user).data("userKind",1);
            }else {
                return R.error().message("手机号或密码错误！");
            }
        }else if (loginForm.getRole()==2){
            ExpertInfo expertInfo = loginService.getExpertInfoByPhone(loginForm.getUserPhone());
            if(expertInfo==null){
                return R.error().message("账号不存在！");
            }
            if (loginForm.getUserPassword().equals(expertInfo.getExpertPassword())){//登录成功
                expertInfo.setExpertPassword("");
                return R.ok().data("expertInfo",expertInfo).data("userKind",2);
            }else {
                return R.error().message("手机号或密码错误！");
            }
        }else if (loginForm.getRole()==3){
            //TODO:读管理员表
            AdminInfo adminInfo = loginService.getAdminInfoByAccount(loginForm.getUserPhone());
            if (adminInfo==null){
                return R.error().message("账号不存在！");
            }
            if (loginForm.getUserPhone().equals(adminInfo.getAdminAccount())){
                adminInfo.setAdminPassword("");
                return R.ok().data("adminInfo",adminInfo).data("userKind",3);
            }else {
                return R.error().message("手机号或密码错误！");
            }
        }
        return R.error().message("登录身份出错！");
    }

}
