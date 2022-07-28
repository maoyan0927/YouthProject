package com.youth.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youth.Entity.AdminInfo;
import com.youth.Entity.User;

public interface AdminInfoService extends IService<AdminInfo> {
    AdminInfo getAdminInfoByAccount(String account);
}
