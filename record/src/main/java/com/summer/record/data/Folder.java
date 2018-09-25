package com.summer.record.data;

//by summer on 2018-09-25.

import com.summer.record.base.BaseBean;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Folder extends BaseBean{

    private String name;

    private ArrayList<Record> records;

    public Folder() {
    }

    public Folder(String name, ArrayList<Record> records) {
        this.name = name;
        this.records = records;
    }
}
