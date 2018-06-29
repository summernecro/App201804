package com.summer.record.ui.main.image.image;

//by summer on 2018-06-25.

import com.android.lib.base.ope.BaseValue;
import com.android.lib.util.LoadUtil;
import com.summer.record.data.Record;
import com.summer.record.ui.main.image.images.ImagesFrag;
import com.summer.record.ui.view.UpdateIndicator;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

public class ImageValue extends BaseValue {

    @Getter
    LoadUtil loadUtil= new LoadUtil();
    @Getter
    String[] timedu = new String[2];

    @Setter
    @Getter
    ImagesFrag imagesFrag;
    @Setter
    @Getter
    private String titleStr="";
    @Getter
    ArrayList<Record> records = new ArrayList<>();

    @Getter
    ArrayList<Record> allRecords = new ArrayList<>();

    public ImageValue(){
        UpdateIndicator updateIndicator = new UpdateIndicator();
        loadUtil.setIndicator(updateIndicator);
    }


}