package com.uon.user.dto;

import lombok.Data;

@Data
public class User {
    private String userId;
    private String password;
    private String name;
    private String phone;
    private String dongCode;
    private String center;
    private String birth;
    private int experience;
    private int point;
    private int role;
    private int level;
}
