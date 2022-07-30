package com.youth.Entity.vo;

import lombok.Data;

@Data
public class RegisterForm {
    private String userPhone;
    private String userName;
    private String userPassword;
    private String checkPassword;
}
