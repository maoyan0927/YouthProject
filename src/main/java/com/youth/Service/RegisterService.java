package com.youth.Service;

import com.youth.Entity.User;

public interface RegisterService {
    Boolean addUser(User user);
    User getUserInfoByUserPhone(String userPhone);
}
