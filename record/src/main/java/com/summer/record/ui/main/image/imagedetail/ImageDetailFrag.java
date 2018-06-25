package com.summer.record.ui.main.image.imagedetail;

//by summer on 2018-03-28.

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.constant.ValueConstant;
import com.android.lib.util.LogUtil;
import com.summer.record.R;
import com.summer.record.data.Record;

import java.util.ArrayList;

public class ImageDetailFrag extends BaseUIFrag<ImageDetailUIOpe,ImageDetailDAOpe,ImageDetailValue> {


    public static ImageDetailFrag getInstance(ArrayList<Record> images, int pos){
        ImageDetailFrag imageDetailFrag = new ImageDetailFrag();
        imageDetailFrag.setArguments(new Bundle());
        imageDetailFrag.getP().getD().setImages(images);
        imageDetailFrag.getArguments().putInt(ValueConstant.DATA_INDEX,pos);
        return imageDetailFrag;

    }

    @Override
    public void initNow() {
        super.initNow();
        getP().getU().initImages(getP().getD().getImages(),getArguments().getInt(ValueConstant.DATA_INDEX));
    }

    @Override
    public int getBaseUILayout() {
        return R.layout.frag_base;
    }

    @Override
    protected int delayTime() {
        return 210;
    }
}
