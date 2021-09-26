package com.cinfiny.demo.models.dto;

import java.util.ArrayList;
import java.util.List;

public class RegisterFormDTO extends LoginFormDTO {

    private String verifyPassword;

    private String email;

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
