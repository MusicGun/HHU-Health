package com.test.examine.entity;


import lombok.Data;

import java.io.Serializable;

@Data
public class HealthAnalyse implements Serializable {

    private String time;

    private int health;

    private int offline;

    private int exception;

    private double stayTime;
}
