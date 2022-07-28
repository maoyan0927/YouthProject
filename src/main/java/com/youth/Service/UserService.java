package com.youth.Service;

import cn.hutool.system.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youth.Entity.User;
import com.youth.Entity.YouthInfo;
import com.youth.Service.Impl.UserImpl;

import java.util.List;

public interface UserService extends IService<User> {

    User getUserInfoByUserPhone(String userPhone);

    Boolean addUser(User user);

}
