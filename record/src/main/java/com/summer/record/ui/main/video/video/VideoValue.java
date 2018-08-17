package com.summer.record.ui.main.video.video;

//by summer on 2018-06-25.

import com.android.lib.base.ope.BaseValue;
import com.android.lib.util.LoadUtil;
import com.summer.record.data.Record;
import com.summer.record.ui.main.video.videos.VideosFrag;
import com.summer.record.ui.view.UpdateIndicator;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

public class VideoValue extends BaseValue {

    @Getter
    LoadUtil loadUtil= new LoadUtil();
    @Getter
    String[] timedu = new String[2];

    @Setter
    @Getter
    private String titleStr="";
    @Setter
    @Getter
    private VideosFrag videosFrag;

    @Getter
    ArrayList<Record> records = new ArrayList<>();

    private int[] pos = new int[2];

    @Setter
    int year = 0;

    @Getter
    ArrayList<Record> allRecords = new ArrayList<>();

    public VideoValue(){
        UpdateIndicator updateIndicator = new UpdateIndicator();
        loadUtil.setIndicator(updateIndicator);
    }

    public String getYear(){
        return "\u3000\u3000"+year;
    }

}
