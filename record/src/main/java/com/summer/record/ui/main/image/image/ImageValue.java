package com.summer.record.ui.main.image.image;

//by summer on 2018-06-25.

import com.android.lib.base.ope.BaseValue;
import com.android.lib.util.LoadUtil;
import com.summer.record.ui.view.UpdateIndicator;

import lombok.Getter;

public class ImageValue extends BaseValue {

    @Getter
    LoadUtil loadUtil= new LoadUtil();
    @Getter
    String[] timedu = new String[2];

    public ImageValue(){
        UpdateIndicator updateIndicator = new UpdateIndicator();
        loadUtil.setIndicator(updateIndicator);
    }



}
