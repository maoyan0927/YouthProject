package com.youth.Service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youth.Entity.DicomEntity;
import com.youth.Entity.HttpResult;
import com.youth.Entity.Slicing;
import com.youth.Service.SlicingInfoService;
import com.youth.Util.HttpAPIUtil;
import com.youth.mapper.SlicingInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@Slf4j
public class SlicingInfoServiceImpl extends ServiceImpl<SlicingInfoMapper, Slicing> implements SlicingInfoService {

    @Autowired
    SlicingInfoMapper slicingInfoMapper;

    @Autowired
    HttpAPIUtil httpAPIUtil;

    @Value("${url.dicomToJpg}")
    private String dicomToJpgUrl;

    /**
     * 将骨龄片路径添加，并插入数据表生成slicingId
     */
    @Override
    public int saveSlice(Slicing slicing) {
        int slicingId = slicingInfoMapper.save(slicing);
        if(slicingId>0){
            return slicing.getSlicingId();
        }else{
            return 0;
        }
    }

    /**
     * 上传骨龄图片,将骨龄片转化为jpg格式存储
     */
    @Override
    public int uploadDicomWithSave(MultipartFile file) {
        try {
            HttpResult result = httpAPIUtil.doPostWithFile(dicomToJpgUrl, "file", file);
            if (result.getCode() == 200) {
                log.debug("【JSON】:{}", result.getBody());
                DicomEntity dicomEntity = JSON.parseObject(result.getBody(), DicomEntity.class);

                if (dicomEntity.getError().equals("0")) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    Slicing slice = new Slicing();
                    slice.setDicomOriginPath(dicomEntity.getDicomPath());
                    slice.setDicomPath(dicomEntity.getJpgSavePath());
                    slice.setHttpPath(dicomEntity.getHttpPath());
                    if(dicomEntity.getStudyDate().length() > 0) {
                        slice.setPhysicalTime(sdf.parse(dicomEntity.getStudyDate()));
                    }else {
                        slice.setPhysicalTime(null);
                    }
                    slice.setState(0);
                    int slicingId = saveSlice(slice);
                    if (slicingId > 0) {
                        log.info("slicingId:" + slicingId);
                        return slicingId;
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Slicing> getSlicingByYouthId(Integer youthId) {
        List<Slicing> slicingByYouthId = slicingInfoMapper.getSlicingByYouthId(youthId);
        return slicingByYouthId;
    }

    @Override
    public Boolean deleteFile(File file) {
        //判断文件不为null或文件目录存在
        if (file == null || !file.exists()) {
            System.out.println("文件删除失败,请检查文件是否存在以及文件路径是否正确");
            return false;
        }
        //获取目录下子文件
        File[] files = file.listFiles();
        //遍历该目录下的文件对象
        for (File f : files) {
            //判断子目录是否存在子目录,如果是文件则删除
            if (f.isDirectory()) {
                //递归删除目录下的文件
                deleteFile(f);
            } else {
                //文件删除
                f.delete();
            }
        }
        //文件夹删除
        file.delete();
        return true;
    }
}
