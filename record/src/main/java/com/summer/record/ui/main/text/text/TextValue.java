package com.summer.record.ui.main.text.text;

//by summer on 2018-06-25.

import com.android.lib.base.ope.BaseValue;
import com.summer.record.data.Record;
import com.summer.record.data.Tiplab;
import com.summer.record.ui.main.record.record.RecordDAOpe;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TextValue extends BaseValue{

    private ArrayList<Record> list = new ArrayList<>();

    private ArrayList<Record> records = new ArrayList<>();


    private ArrayList<Tiplab> tiplabs = new ArrayList<>();

    private String temStr = "";

    RecordDAOpe recordDAOpe = new RecordDAOpe();
}
