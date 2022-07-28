package com.youth.Controller;

import com.youth.Entity.User;
import com.youth.Entity.vo.LoginForm;
import com.youth.Entity.vo.RegisterForm;
import com.youth.Service.Impl.RegisterServiceImpl;
import com.youth.Service.RegisterService;
import com.youth.Util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/register")
@RestController
@Slf4j
@Api(tags = "注册控制器")
@CrossOrigin
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @ApiOperation("用户注册")
    @PostMapping("/user")
    public R login(@RequestBody RegisterForm registerForm){
        User user = new User();
        user.setUserPassword(registerForm.getUserPassword());
        user.setUserPhone(registerForm.getUserPhone());
        user.setUserName(registerForm.getUserName());
        user.setState(0);
        if ( registerService.getUserInfoByUserPhone(user.getUserPhone()) ==null ){
            Boolean success = registerService.addUser(user);
            user = registerService.getUserInfoByUserPhone(user.getUserPhone());
            if (success){
                return R.ok().data("user",user);
            }else
                return  R.error().message("注册失败！");
        }
        return R.error().message("手机号已经被注册！");
    }

}
