package com.test.examine.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

@Data
public class Student extends User implements Serializable {

    @NotNull
    @Size(min = 8, max = 10, message = "学号范围为8~10")
    @JsonProperty(value = "id")
    private String id;

    @NotNull
    @Size(min = 2, message = "请输入正确名称")
    @JsonProperty(value = "name")
    private String name;

    @NotNull
    @Size(min = 1, max = 1)
    @JsonProperty(value = "sex")
    private String sex;

    @NotNull
    @JsonProperty(value = "labid")
    private int labid;

    @NotNull
    @JsonProperty(value = "password")
    private String password;

    @NotNull
    @Email
    @JsonProperty(value = "email")
    private String email;

    @NotNull
    @JsonProperty(value = "dormitory")
    private String dormitory;

    @NotNull
    @JsonProperty(value = "phone")
    private String phone;

    private int mark;

}
