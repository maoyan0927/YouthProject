package com.youth.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youth.Entity.YouthInfo;
import com.youth.Entity.vo.YouthInfoQuery;
import com.youth.Service.YouthInfoService;
import com.youth.Util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author maoyan
 * @date 2022/7/17 15:10
 */

@RequestMapping("/YouthInfo")
@RestController
@Slf4j
@Api(tags = "孩童信息类")
@CrossOrigin
public class YouthInfoController {

    @Autowired
    private YouthInfoService youthInfoService;

    @ApiOperation("添加孩童信息")
    @PostMapping("/addYouthInfo")
    public R add(@RequestBody YouthInfo youthInfo){
        boolean save = youthInfoService.save(youthInfo);
        if(save){
            return R.ok();
        }else{
            return R.error();
        }
    }


    @ApiOperation("所有孩童列表")
    @GetMapping("/findAllYouthInfo")
    public R findAllYouthInfo(){
        List<YouthInfo> list = youthInfoService.list(null);
        return R.ok().data("items", list);
    }


    @ApiOperation("根据孩童id查孩童信息")
    @GetMapping("/findyouthInfo/{youthInfoId}")
    public R findYouthInfo(@PathVariable Integer youthInfoId){
        YouthInfo youthInfo = youthInfoService.getById(youthInfoId);
        return R.ok().data("youthInfo", youthInfo);
    }

    @ApiOperation("修改用户信息")
    @PostMapping("/updateYouthInfo")
    public R update(@RequestBody YouthInfo youthInfo){
        boolean flag = youthInfoService.updateById(youthInfo);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation("根据用户信息查询所有孩童信息")
    @GetMapping("/findYouthInfo/{userId}")
    public R findYouthInfoByUserId(@PathVariable Integer userId){
        List<YouthInfo> youthInfolist = youthInfoService.getYouthInfoByUserId(userId);
        return R.ok().data("items", youthInfolist);
    }

    @ApiOperation("分页查询孩童信息")
    @GetMapping("/pageTeacher/{current}/{limit}")
    public R pageListYouthInfo(@PathVariable long current,
                             @PathVariable long limit){
        //创建page对象
        Page<YouthInfo> youthInfoPage = new Page<>(current,limit);

        //实现分页方法
        youthInfoService.page(youthInfoPage,null);
        //获取总页数
        long total = youthInfoPage.getTotal();
        //获取list集合
        List<YouthInfo> records = youthInfoPage.getRecords();

        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation("条件分页查询孩童信息")
    @PostMapping("/pageYouthInfoCondition/{current}/{limit}")
    public R pageYouthInfoCondition(@PathVariable long current, @PathVariable long limit,
                                    @RequestBody(required = false) YouthInfoQuery youthInfoQuery){
        //创建page对象
        Page<YouthInfo> youthInfoPage = new Page<>(current, limit);
        //构建条件
        QueryWrapper<YouthInfo> wrapper = new QueryWrapper<>();

        //组合查询
        String name = youthInfoQuery.getYouthName();
        Integer sex = youthInfoQuery.getYouthSex();
        String begin = youthInfoQuery.getBegin();
        String end = youthInfoQuery.getEnd();
        //判断条件，进行拼接
        if(!StringUtils.isEmpty(name)) {
            //构建条件
            wrapper.like("youth_name",name);
        }
        if(!StringUtils.isEmpty(sex)) {
            wrapper.eq("youth_sex",sex);
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
        youthInfoService.page(youthInfoPage,wrapper);

        long total = youthInfoPage.getTotal();//总记录数
        List<YouthInfo> records = youthInfoPage.getRecords(); //数据list集合
        return R.ok().data("total",total).data("rows",records);
    }

}
