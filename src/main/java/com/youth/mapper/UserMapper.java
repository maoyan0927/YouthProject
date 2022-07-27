package com.youth.mapper;


import cn.hutool.system.UserInfo;
import com.youth.Entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
   User getUserInfoByUserPhone(String userPhone);
}
