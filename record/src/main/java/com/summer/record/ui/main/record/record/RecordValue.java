package com.summer.record.ui.main.record.record;

//by summer on 2018-06-25.

import com.android.lib.base.ope.BaseValue;
import com.android.lib.util.LoadUtil;
import com.summer.record.data.Record;
import com.summer.record.ui.main.record.image.ImageDetailFrag;
import com.summer.record.ui.main.record.records.RecordsFrag;
import com.summer.record.ui.view.UpdateIndicator;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

public class RecordValue extends BaseValue {

    @Getter
    LoadUtil loadUtil= new LoadUtil();
    @Getter
    String[] timedu = new String[2];

    @Setter
    @Getter
    RecordsFrag recordsFrag;
    @Setter
    @Getter
    private String titleStr="";
    @Getter
    ArrayList<Record> usedRecords = new ArrayList<>();

    @Getter
    ArrayList<Record> dateOriRecords = new ArrayList<>();

    //未加工的数据
    @Getter
    @Setter
    ArrayList<Record> OriRecords = new ArrayList<>();

    @Setter
    int year = 0;
    @Setter
    @Getter
    ImageDetailFrag imageDetailFrag;

    @Setter
    @Getter
    String type;

    public static int num = 6;
    @Setter
    @Getter
    RecordDAOpe recordDAOpe = new RecordDAOpe();
    @Setter
    @Getter
    private boolean isdoing = false;

    public RecordValue(){
        UpdateIndicator updateIndicator = new UpdateIndicator();
        loadUtil.setIndicator(updateIndicator);
    }

    public void reAddAllDateRecord(ArrayList<Record> records){
        reAddDateUsedRecord(records);
        reAddDateOriRecord(records);
    }

    public void clearAllDateRecord(){
        usedRecords.clear();
        dateOriRecords.clear();
    }

    public void reAddDateUsedRecord(ArrayList<Record> records){
        usedRecords.clear();
        usedRecords.addAll(records);
    }

    public void reAddDateOriRecord(ArrayList<Record> records){
        dateOriRecords.clear();
        dateOriRecords.addAll(records);
    }

    public String getYear(){
        return "\u3000\u3000"+year;
    }


}
