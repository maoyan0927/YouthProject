package com.youth.Service;

import com.youth.Entity.AdminInfo;
import com.youth.Entity.ExpertInfo;
import com.youth.Entity.User;

public interface LoginService {
    ExpertInfo getExpertInfoByPhone(String phone);
    User getUserInfoByUserPhone(String userPhone);

    AdminInfo getAdminInfoByAccount(String account);

}
