package com.summer.record.data.text;

//by summer on 2018-03-28.

import com.android.lib.bean.BaseBean;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.summer.record.data.db.RecordDataBase;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(database = RecordDataBase.class)
public class Text extends BaseBean {

    @PrimaryKey(autoincrement = true)
    public int id;
    @Column
    public  String text;
    @Column
    public long time;
}
