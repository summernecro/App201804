package com.summer.record.ui.main.record.image.imageshow;

//by summer on 2018-03-28.

import android.os.Bundle;
import android.view.View;

import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.constant.ValueConstant;
import com.github.florent37.viewanimator.AnimationListener;
import com.summer.record.R;
import com.summer.record.data.Record;
import com.summer.record.tool.ViewTool;
import com.summer.record.ui.main.record.image.ImageDetailFrag;
import com.summer.record.ui.main.main.MainAct;

import butterknife.OnClick;
import butterknife.Optional;

public class ImageShowFrag extends BaseUIFrag<ImageShowUIOpe,ImageShowValue> {

    public static ImageShowFrag getInstance(Record image,BaseUIFrag baseUIFrag){
        ImageShowFrag imageShowFrag = new ImageShowFrag();
        imageShowFrag.setArguments(new Bundle());
        imageShowFrag.getArguments().putSerializable(ValueConstant.DATA_DATA,image);
        imageShowFrag.getPV().initFrag(baseUIFrag);
        return imageShowFrag;
    }

    @Override
    public void initNow() {
        super.initNow();
        getPU().showImage((Record) getArguments().getSerializable(ValueConstant.DATA_DATA));
        if(getPV().getBaseUIFrag() instanceof ImageDetailFrag){
            ImageDetailFrag imageDetailFrag = (ImageDetailFrag) getPV().getBaseUIFrag();
            getPU().setVis(imageDetailFrag.getPV().isShow());
        }
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
                ((MainAct)getBaseUIAct()).getPU().swithTitleAndBottomVisible();
                getPU().switcht();
                if(getPV().getBaseUIFrag() instanceof ImageDetailFrag){
                    ImageDetailFrag imageDetailFrag = (ImageDetailFrag) getPV().getBaseUIFrag();
                    getPU().setshow(imageDetailFrag);
                }
                break;
        }
    }

    @Override
    public void onStart(AnimationListener.Stop stopListener) {
        stopListener.onStop();
    }

    @Override
    public void onRemove(AnimationListener.Stop stopListener) {
        stopListener.onStop();
    }

    @Override
    public void onBackIn() {
    }

    @Override
    public void onBackOut() {
    }
}
