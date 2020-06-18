package com.test.examine.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class StudentInfo implements Serializable {
    
    
    private String id;
    
    private String email;

    private String name;

    private String dormitory;

    private String labid;

    private String phone;
}
