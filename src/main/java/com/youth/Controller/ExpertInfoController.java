package com.youth.Controller;

import com.youth.Entity.ExpertInfo;
import com.youth.Service.ExpertInfoService;
import com.youth.Util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("/deleteExpertInfo/{expertId}")
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

    @ApiOperation("所有专家列表")
    @GetMapping("/findAllExpertInfo")
    public R findAllExpertInfo(){
        List<ExpertInfo> list = expertInfoService.list(null);
        return R.ok().data("items", list);
    }
}
