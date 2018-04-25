package com.summer.record.ui.main.image.imagedetail.imageshow;

//by summer on 2018-03-28.

import android.os.Bundle;

import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.base.ope.BaseUIOpe;
import com.android.lib.constant.ValueConstant;
import com.summer.record.data.Record;

public class ImageShowFrag extends BaseUIFrag<ImageShowUIOpe,ImageShowDAOpe> {

    public static ImageShowFrag getInstance(Record image){
        ImageShowFrag imageShowFrag = new ImageShowFrag();
        imageShowFrag.setArguments(new Bundle());
        imageShowFrag.getArguments().putSerializable(ValueConstant.DATA_DATA,image);

        return imageShowFrag;
    }

    @Override
    public void initdelay() {
        super.initdelay();
        getP().getU().showImage((Record) getArguments().getSerializable(ValueConstant.DATA_DATA));
    }

    @Override
    protected int delayTime() {
        return 0;
    }
}
