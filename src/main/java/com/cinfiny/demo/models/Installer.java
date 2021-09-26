package com.cinfiny.demo.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class Installer extends User {

    private String pwHash;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public Installer(String employeeNumber, String password, String email) {
        super.getEmployeeNumber(employeeNumber);
        super.getEmail(email);
        this.pwHash = encoder.encode(password);
    }

    public String getPwHash() {
        return pwHash;
    }

    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }
}
