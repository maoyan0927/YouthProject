package com.youth.Controller;

import com.youth.Entity.User;
import com.youth.Entity.YouthInfo;
import com.youth.Entity.vo.LoginForm;
import com.youth.Service.AdminInfoService;
import com.youth.Service.ExpertInfoService;
import com.youth.Service.UserService;
import com.youth.Service.YouthInfoService;
import com.youth.Util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
@Slf4j
@Api(tags = "登录控制器")
@CrossOrigin
public class LoginController {
    @Autowired
    private UserService userService;


    @ApiOperation("验证登录")
    @PostMapping("/login")
    public R login(@RequestBody LoginForm loginForm){
        if (loginForm.getRole()==1){
            User user = userService.getUserInfoByUserPhone(loginForm.getUserPhone());
            System.out.println(user);
            if (loginForm.getUserPassword().equals(user.getUserPassword())){//登录成功
                return R.ok().data("userInfo",user);
            }else {
                return R.error().message("手机号或密码错误！");
            }
        }else if (loginForm.getRole()==2){
            //TODO:读专家表
            return R.ok();
        }else if (loginForm.getRole()==3){
            //TODO:读管理员表
            return R.ok();
        }
        return R.error();
    }

}
