package com.test.examine.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class Admin extends User implements Serializable {
    @JsonProperty(value = "email")
    @Email
    private String email;

    @JsonProperty(value = "name")
    @NotNull
    private String name;
}
