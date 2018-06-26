package com.summer.record.ui.main.video.video;

//by summer on 2018-06-25.

import com.android.lib.base.ope.BaseValue;
import com.android.lib.util.LoadUtil;
import com.summer.record.ui.main.video.videos.VideosFrag;
import com.summer.record.ui.view.UpdateIndicator;

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

    public VideoValue(){
        UpdateIndicator updateIndicator = new UpdateIndicator();
        loadUtil.setIndicator(updateIndicator);
    }

}
