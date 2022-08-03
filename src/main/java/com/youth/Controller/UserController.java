package com.youth.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youth.Entity.User;
import com.youth.Entity.YouthInfo;
import com.youth.Entity.vo.UserQuery;
import com.youth.Entity.vo.YouthInfoQuery;
import com.youth.Response.Result;
import com.youth.Service.UserService;
import com.youth.Util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * @author maoyan
 * @date 2022/7/16 19:20
 */

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
        user.setCreateTime(new Date());
        boolean save = userService.save(user);
        if(save){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation("删除用户信息")
    @DeleteMapping("/deleteUser/{userId}")
    public R delete(@PathVariable int userId){
        boolean success = userService.removeById(userId);
        if(success){
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

    @ApiOperation("条件查询用户信息")
    @PostMapping("/searchUser")
    public R searchUser(@RequestBody UserQuery userQuery){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        String name = userQuery.getUserName();
        String phone = userQuery.getUserPhone();
        String begin = userQuery.getBegin();
        String end = userQuery.getEnd();
        //判断条件，进行拼接
        if(!StringUtils.isEmpty(name)) {
            //构建条件
            wrapper.like("user_name","%"+name+"%");
        }
        if(!StringUtils.isEmpty(phone)) {
            //构建条件
            wrapper.eq("user_phone",phone);
        }
        if(!StringUtils.isEmpty(begin)) {
            wrapper.ge("create_time",begin);
        }
        if(!StringUtils.isEmpty(end)) {
            wrapper.le("create_time",end);
        }

        //排序
        wrapper.orderByDesc("update_time");
        List<User> list = userService.list(wrapper); //数据list集合
        return R.ok().data("items",list);

    }


    @ApiOperation("分页查询用户信息")
    @GetMapping("/pageUser/{current}/{limit}")
    public R pageListUser(@PathVariable long current,
                               @PathVariable long limit){
        //创建page对象
        Page<User> userPage = new Page<>(current,limit);

        //实现分页方法
        userService.page(userPage,null);
        //获取总页数
        long total = userPage.getTotal();
        //获取list集合
        List<User> records = userPage.getRecords();

        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation("条件分页查询用户信息")
    @PostMapping("/pageUserCondition/{current}/{limit}")
    public R pageUserCondition(@PathVariable long current, @PathVariable long limit,
                                    @RequestBody(required = false) UserQuery userQuery){
        //创建page对象
        Page<User> userPage = new Page<>(current, limit);
        //构建条件
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        //组合查询
        String name = userQuery.getUserName();
        String begin = userQuery.getBegin();
        String end = userQuery.getEnd();
        //判断条件，进行拼接
        if(!StringUtils.isEmpty(name)) {
            //构建条件
            wrapper.like("user_name",name);
        }
        if(!StringUtils.isEmpty(begin)) {
            wrapper.ge("create_time",begin);
        }
        if(!StringUtils.isEmpty(end)) {
            wrapper.le("create_time",end);
        }

        //排序
        wrapper.orderByDesc("create_time");

        //调用方法实现条件查询分页
        userService.page(userPage,wrapper);

        long total = userPage.getTotal();//总记录数
        List<User> records = userPage.getRecords(); //数据list集合
        return R.ok().data("total",total).data("rows",records);

    }

}
