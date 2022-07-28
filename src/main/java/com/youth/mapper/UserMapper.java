package com.youth.mapper;


import com.youth.Entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
   User getUserInfoByUserPhone(String userPhone);
}
