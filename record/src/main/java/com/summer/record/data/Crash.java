package com.summer.record.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Crash {

    private int id;

    private String error;

    private Long createdtime;

    private String user;

    private int platform;
}