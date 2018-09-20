package com.summer.record.ui.main.record.image;

//by summer on 2018-06-25.

import android.view.View;

import com.android.lib.base.ope.BaseValue;
import com.android.lib.bean.databean.XYBean;
import com.summer.record.data.Record;
import com.summer.record.data.Tiplab;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ImageDetailValue extends BaseValue {

    private int[] currentPos = new int[1];

    private ArrayList<Record> images;

    ArrayList<Tiplab> tiplabs = new ArrayList<>();

    private boolean show = true;

    private XYBean local;

    public void setShow(View view){
        show = view.getVisibility()==View.VISIBLE;
    }
}
