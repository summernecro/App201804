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
    ArrayList<Record> oriRecords = new ArrayList<>();

    @Setter
    int year = 0;
    @Setter
    @Getter
    ImageDetailFrag imageDetailFrag;

    @Setter
    @Getter
    String type;

    public static int num = 6;

    public RecordValue(){
        UpdateIndicator updateIndicator = new UpdateIndicator();
        loadUtil.setIndicator(updateIndicator);
    }

    public void reAddAllRecord(ArrayList<Record> records){
        reAddUsedRecord(records);
        reAddOriRecord(records);
    }

    public void clearAllRecord(){
        usedRecords.clear();
        oriRecords.clear();
    }

    public void reAddUsedRecord(ArrayList<Record> records){
        usedRecords.clear();
        usedRecords.addAll(records);
    }

    public void reAddOriRecord(ArrayList<Record> records){
        oriRecords.clear();
        oriRecords.addAll(records);
    }

    public String getYear(){
        return "\u3000\u3000"+year;
    }


}
