package com.test.examine.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Data
public class HealthInfo implements Serializable {

    private String id;

    @NotNull
    private boolean mT;

    @NotNull
    private boolean aT;

    @NotNull
    private boolean health;

    @NotNull
    private boolean victim;

    @NotNull
    private boolean touchVictim;

    @NotNull
    private boolean stayArea;

    @NotNull
    private boolean touchFever;

    @NotNull
    private boolean boardHistory;

    @NotNull
    private boolean touchForeigner;


    private LocalDateTime inTime;


    private LocalDateTime outTime;


    private String showInTime;

    private String showOutTime;

    private int stayTime;

}
