package com.summer.record.service;

import com.android.lib.bean.BaseBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeepRun extends BaseBean {

    private Integer id;

    private String time;

    private String text;
}
