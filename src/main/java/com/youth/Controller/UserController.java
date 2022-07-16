package com.youth.Controller;

import com.youth.Entity.User;
import com.youth.Response.Result;
import com.youth.Service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/User")
@RestController
@Slf4j
@Api(tags = "用户信息类")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("添加用户信息")
    @PostMapping("/addUser")
    public Result add(@RequestParam("userName") String userName,
                      @RequestParam("userPhone") String userPhone,
                      @RequestParam("userPassword") String userPassword,
                      @RequestParam("state") Integer state){
        User user = new User();
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        user.setUserPhone(userPhone);
        user.setState(state);
        boolean save = userService.save(user);
        if(save){
            return Result.success("success");
        }else{
            return Result.success("final");
        }
    }


    @ApiOperation("所有用户列表")
    @GetMapping("/findAllUser")
    public List<User> findAllUser(){
        List<User> list = userService.list(null);
        System.out.println(list);
        return list;
    }


}
