package com.youth.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youth.Entity.Slicing;
import com.youth.Entity.YouthInfo;
import com.youth.Entity.vo.CheckQuery;
import com.youth.Entity.vo.CheckView;
import com.youth.Service.CheckViewService;
import com.youth.Service.SlicingInfoService;
import com.youth.Service.YouthInfoService;
import com.youth.Util.EnDecoderUtil;
import com.youth.Util.R;
import com.youth.Util.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

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
    @Autowired
    private CheckViewService checkViewService;

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

    @GetMapping("/getImage/{slicingId}")
    public void getImage(@PathVariable Integer slicingId, HttpServletResponse response) throws IOException{
        Slicing slicing = slicingInfoService.getById(slicingId);
        File file = new File(slicing.getHttpPath());
        BufferedImage image = ImageIO.read(file);
        response.setHeader("Cache-Control","no-store, no-store");
        response.setContentType("image/jpg");
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "jpg", outputStream);
    }


    @ApiOperation("上传切片信息")
    @PutMapping("/uploadSlicingInfo")
    public R upload(@RequestBody Slicing slicing){
        System.out.println(slicing);
        slicing.setCreateTime(new Date());
        slicing.setState(1);
        boolean flag = slicingInfoService.updateById(slicing);
        if(flag){
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
//                byte[] encode_bytes = EnDecoderUtil.DESEncrypt(desKey, sliceId + "");
//                String sliceStr = Base64.getEncoder().encodeToString(encode_bytes);
                resMap.put("slicingId", sliceId);
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

    @ApiOperation("删除图片信息")
    @DeleteMapping ("/file/deleteDicom/{slicingId}")
    public R deleteBySlicingId(@PathVariable Integer slicingId){
        Slicing slicing = slicingInfoService.getById(slicingId);
        String dicomUrl = slicing.getDicomPath();
        String[] temp = dicomUrl.split("/");
        String deleteByFile = "/home/kth/files/nyouth/output/dicom/"+temp[temp.length-2];
        File file = new File(deleteByFile);
        Boolean deleteFile = slicingInfoService.deleteFile(file);
        if (!deleteFile){
            return R.deleteDicomError();
        }
        boolean removeById = slicingInfoService.removeById(slicingId);
        if (removeById){
            return R.ok();
        }else{
            return R.error();
        }
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

    @ApiOperation("查询待审核列表")
    @PostMapping("/checking")
    public R checkingList (@RequestBody CheckQuery checkQuery){
        QueryWrapper<CheckView> wrapper = new QueryWrapper<>();
        if (checkQuery == null){
            List<CheckView> list = checkViewService.list(null);
            return R.ok().data("items", list);
        }
        String name = checkQuery.getYouthName();
        String cardId = checkQuery.getYouthCardId();
        String begin = checkQuery.getBegin();
        String end = checkQuery.getEnd();

        if(!StringUtils.isEmpty(name)) {
            //构建条件
            wrapper.like("youth_name","%"+name+"%");
        }
        if(!StringUtils.isEmpty(cardId)) {
            wrapper.eq("youth_card_id",cardId);
        }
        if(!StringUtils.isEmpty(begin)) {
            wrapper.ge("update_time",begin);
        }
        if(!StringUtils.isEmpty(end)) {
            wrapper.le("update_time",end);
        }
        List<CheckView> list = checkViewService.list(wrapper);
        return R.ok().data("items", list);
    }



}
