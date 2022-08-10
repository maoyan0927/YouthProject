package com.youth.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.youth.Entity.ExpertInfo;
import com.youth.Entity.User;
import com.youth.Entity.vo.ExpertQuery;
import com.youth.Entity.vo.UserQuery;
import com.youth.Service.ExpertInfoService;
import com.youth.Util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/expert")
@RestController
@Slf4j
@Api(tags = "专家控制器")
@CrossOrigin
public class ExpertInfoController {
    @Autowired
    private ExpertInfoService expertInfoService;

    @ApiOperation("增加专家")
    @PostMapping("/addExpertInfo")
    public R addExpertInfo(@RequestBody ExpertInfo expertInfo){
        boolean save = expertInfoService.save(expertInfo);
        if (save){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation("根据id查询专家信息")
    @GetMapping("/findExpertInfoById/{expertId}")
    public R findExpertInfoById(@PathVariable Integer expertId){
        ExpertInfo expertInfo = expertInfoService.getById(expertId);
        if (expertInfo!=null){
            return R.ok().data("expertInfo",expertInfo);
        }else{
            return R.error();
        }
    }

    @ApiOperation("根据id删除专家信息")
    @DeleteMapping("/deleteExpertInfo/{expertId}")
    public R deleteExpertInfo(@PathVariable Integer expertId){
        boolean removeById = expertInfoService.removeById(expertId);
        if(removeById){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation("更新专家信息")
    @PostMapping("/updateExpertInfo")
    public R updateExpertInfo(@RequestBody ExpertInfo expertInfo){
        boolean updateById = expertInfoService.updateById(expertInfo);
        if (updateById){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation("条件查询专家信息")
    @PostMapping("/searchExpert")
    public R searchUser(@RequestBody ExpertQuery expertQuery){
        QueryWrapper<ExpertInfo> wrapper = new QueryWrapper<>();
        String name = expertQuery.getExpertName();
        String phone = expertQuery.getExpertPhone();
        String unit = expertQuery.getExpertUnit();
        String title = expertQuery.getExpertTitle();
        String begin = expertQuery.getBegin();
        String end = expertQuery.getEnd();
        //判断条件，进行拼接
        if(!StringUtils.isEmpty(name)) {
            //构建条件
            wrapper.like("expert_name","%"+name+"%");
        }
        if(!StringUtils.isEmpty(phone)) {
            //构建条件
            wrapper.eq("expert_phone",phone);
        }
        if(!StringUtils.isEmpty(unit)) {
            //构建条件
            wrapper.like("expert_unit","%"+unit+"%");
        }
        if(!StringUtils.isEmpty(title)) {
            //构建条件
            wrapper.eq("expert_title","%"+title+"%");
        }
        if(!StringUtils.isEmpty(begin)) {
            wrapper.ge("create_time",begin);
        }
        if(!StringUtils.isEmpty(end)) {
            wrapper.le("create_time",end);
        }

        //排序
        wrapper.orderByDesc("update_time");
        List<ExpertInfo> list = expertInfoService.list(wrapper); //数据list集合
        return R.ok().data("items",list);

    }
    @ApiOperation("所有专家列表")
    @GetMapping("/findAllExpertInfo")
    public R findAllExpertInfo(){
        QueryWrapper<ExpertInfo> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("update_time");
        wrapper.ne("expert_id",0);
        List<ExpertInfo> list = expertInfoService.list(wrapper);
        return R.ok().data("items", list);
    }

    @ApiOperation("修改专家密码和手机")
    @PostMapping("/updatePasswordAndPhone")
    public R searchUser(@RequestBody ExpertInfo expertInfo){
        ExpertInfo ori = expertInfoService.getById(expertInfo.getExpertId());
        ori.setExpertPassword(expertInfo.getExpertPassword());
        ori.setExpertPhone(expertInfo.getExpertPhone());
        boolean b = expertInfoService.updateById(ori);
        if (b){
            return R.ok();
        }else{
            return R.error();
        }
    }
}
