package com.summer.record.data.db;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = RecordDataBase.NAME, version = RecordDataBase.VERSION)
public class RecordDataBase {
    //数据库名称
    public static final String NAME = "RecordDataBase";
    //数据库版本
    public static final int VERSION = 2;
}
