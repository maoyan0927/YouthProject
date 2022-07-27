package com.youth.Entity.vo;

import lombok.Data;

@Data
public class LoginForm {
    private String userPhone;
    private String userPassword;
    private int role;

}
