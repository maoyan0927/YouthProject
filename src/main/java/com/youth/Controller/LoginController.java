package com.youth.Controller;

import cn.hutool.json.JSON;
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
            System.out.println(user);
            if (loginForm.getUserPassword().equals(user.getUserPassword())){//登录成功
                user.setUserPassword("");
                return R.ok().data("user", user).data("userKind",1);
            }else {
                return R.error().message("手机号或密码错误！");
            }
        }else if (loginForm.getRole()==2){
            //TODO:读专家表
            ExpertInfo expertInfo = loginService.getExpertInfoByPhone(loginForm.getUserPhone());
            if (loginForm.getUserPassword().equals(expertInfo.getExpertPassword())){//登录成功
                return R.ok().data("expertInfo",expertInfo).data("userKind",2);
            }else {
                return R.error().message("手机号或密码错误！");
            }
        }else if (loginForm.getRole()==3){
            //TODO:读管理员表
            return R.ok().data("userKind",3);
        }
        return R.error();
    }

}
