package com.youth.Controller;

import com.youth.Entity.User;
import com.youth.Response.Result;
import com.youth.Service.UserService;
import com.youth.Util.R;
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
    public R add(@RequestBody User user){
        boolean save = userService.save(user);
        if(save){
            return R.ok();
        }else{
            return R.error();
        }
    }


    @ApiOperation("所有用户列表")
    @GetMapping("/findAllUser")
    public R findAllUser(){
        List<User> list = userService.list(null);
        return R.ok().data("items", list);
    }


    @ApiOperation("根据id查用户信息")
    @GetMapping("/findUser/{userId}")
    public R findUser(@PathVariable String userId){
        User user = userService.getById(userId);
        return R.ok().data("user", user);
    }

    @ApiOperation("修改用户信息")
    @PostMapping("/updateUser")
    public R update(@RequestBody User user){
        boolean flag = userService.updateById(user);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }

}
