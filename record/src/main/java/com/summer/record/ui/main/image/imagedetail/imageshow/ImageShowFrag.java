package com.summer.record.ui.main.image.imagedetail.imageshow;

//by summer on 2018-03-28.

import android.os.Bundle;
import android.view.View;

import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.base.ope.BaseUIOpe;
import com.android.lib.constant.ValueConstant;
import com.summer.record.R;
import com.summer.record.data.Record;
import com.summer.record.ui.main.image.imagedetail.ImageDetailFrag;
import com.summer.record.ui.main.main.MainAct;

import butterknife.OnClick;
import butterknife.Optional;

public class ImageShowFrag extends BaseUIFrag<ImageShowUIOpe,ImageShowDAOpe,ImageShowValue> {

    public static ImageShowFrag getInstance(Record image,BaseUIFrag baseUIFrag){
        ImageShowFrag imageShowFrag = new ImageShowFrag();
        imageShowFrag.setArguments(new Bundle());
        imageShowFrag.getArguments().putSerializable(ValueConstant.DATA_DATA,image);
        imageShowFrag.getP().getV().setBaseUIFrag(baseUIFrag);
        return imageShowFrag;
    }

    @Override
    public void initNow() {
        super.initNow();
        getP().getU().showImage((Record) getArguments().getSerializable(ValueConstant.DATA_DATA));
    }

    @Override
    protected int delayTime() {
        return 0;
    }



    @Optional
    @OnClick(R.id.iv_image)
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.iv_image:
                ((MainAct)getBaseAct()).getP().getU().swithTitleAndBottomVisible();
                if(getP().getV().getBaseUIFrag() instanceof ImageDetailFrag){
                    ImageDetailFrag imageDetailFrag = (ImageDetailFrag) getP().getV().getBaseUIFrag();
                    imageDetailFrag.getP().getU().switchTitleVisible();

                }
                break;
        }
    }
}
