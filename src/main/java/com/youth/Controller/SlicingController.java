package com.youth.Controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youth.Entity.Slicing;
import com.youth.Entity.YouthInfo;
import com.youth.Service.SlicingInfoService;
import com.youth.Util.EnDecoderUtil;
import com.youth.Util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author maoyan
 * @date 2022/7/18 15:12
 */

@RequestMapping("/slicing")
@RestController
@Slf4j
@Api(tags = "骨龄图片类")
@CrossOrigin
public class SlicingController {

    @Autowired
    private SlicingInfoService slicingInfoService;

    @Value("${des.key}")
    String desKey;

    @PostMapping("/addSlicingInfo")
    @ApiOperation("添加切片信息")
    public R addSlicing(@RequestBody Slicing slicing){
        boolean save = slicingInfoService.save(slicing);
        if(save){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation("所有图片列表")
    @GetMapping("/findAllSlicing")
    public R findAllSlicing(){
        List<Slicing> list = slicingInfoService.list(null);
        return R.ok().data("items", list);
    }

    /***
     * 图片信息上传
     *
     */
    @PostMapping(value = "/file/uploadDicom")
    @ApiOperation("图片信息上传")
    @ResponseBody
    public R uploadDicom(@RequestParam("file") MultipartFile file) {
        Map<String, Object> resMap = new HashMap<>();
        try {
            int sliceId = slicingInfoService.uploadDicomWithSave(file);
            if (sliceId > 0) {
                Slicing slicing = slicingInfoService.getById(sliceId);
                if (slicing.getPhysicalTime() == null) {
                    resMap.put("PhysicalTime", 0);
                }else {
                    resMap.put("PhysicalTime", slicing.getPhysicalTime());
                }
                resMap.put("code", 1);
                byte[] encode_bytes = EnDecoderUtil.DESEncrypt(desKey, sliceId + "");
                String sliceStr = Base64.getEncoder().encodeToString(encode_bytes);
                resMap.put("sliceId", sliceStr);
                resMap.put("HttpPath", slicing.getHttpPath());
            }else {
                resMap.put("code", 0);
            }
        }catch (Exception e) {
            resMap.put("code", 0);
            e.printStackTrace();
        }
        return R.ok().data("resMap",resMap);
    }

    @ApiOperation("分页查询切片信息")
    @GetMapping("/pageSlicing/{current}/{limit}")
    public R pageListYouthInfo(@PathVariable long current,
                               @PathVariable long limit){
        //创建page对象
        Page<Slicing> slicingPage = new Page<>(current,limit);

        //实现分页方法
        slicingInfoService.page(slicingPage,null);
        //获取总页数
        long total = slicingPage.getTotal();
        //获取list集合
        List<Slicing> records = slicingPage.getRecords();

        return R.ok().data("total",total).data("rows",records);
    }



}
