package com.test.examine.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class Lab implements Serializable {

    @JsonProperty(value = "labid")
    @NotNull
    private int labid;
    @JsonProperty(value = "capacity")
    private int capacity;
    private String id;

    private int total;


}
