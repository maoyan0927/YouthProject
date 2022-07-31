package com.youth.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youth.Entity.Slicing;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface SlicingInfoService extends IService<Slicing> {

    int saveSlice(Slicing slicing);

    int uploadDicomWithSave(MultipartFile file);

    List<Slicing> getSlicingByYouthId(Integer youthId);

    Boolean deleteFile(File file);
}
