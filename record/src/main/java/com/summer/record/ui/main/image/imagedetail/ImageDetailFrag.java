package com.summer.record.ui.main.image.imagedetail;

//by summer on 2018-03-28.

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.base.interf.OnFinishListener;
import com.android.lib.base.listener.ViewListener;
import com.android.lib.constant.ValueConstant;
import com.android.lib.network.news.NetAdapter;
import com.android.lib.util.LogUtil;
import com.android.lib.util.NullUtil;
import com.android.lib.util.ShareUtil;
import com.android.lib.util.StringUtil;
import com.android.lib.util.system.HandleUtil;
import com.github.florent37.viewanimator.ViewAnimator;
import com.summer.record.R;
import com.summer.record.data.NetDataWork;
import com.summer.record.data.Record;
import com.summer.record.data.Tiplab;
import com.summer.record.ui.main.main.AddTipI;
import com.summer.record.ui.main.main.MainAct;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class ImageDetailFrag extends BaseUIFrag<ImageDetailUIOpe,ImageDetailDAOpe,ImageDetailValue> implements AddTipI {


    public static ImageDetailFrag getInstance(ArrayList<Record> images, int pos){
        ImageDetailFrag imageDetailFrag = new ImageDetailFrag();
        imageDetailFrag.setArguments(new Bundle());
        imageDetailFrag.getP().getV().setImages(images);
        imageDetailFrag.getArguments().putInt(ValueConstant.DATA_INDEX,pos);
        return imageDetailFrag;

    }

    @Override
    public void initNow() {
        super.initNow();
        getP().getU().initImages(getChildFragmentManager(), getP().getV().getImages(), getArguments().getInt(ValueConstant.DATA_INDEX), getP().getV().getCurrentPos(), new OnFinishListener() {
            @Override
            public void onFinish(Object o) {
                HandleUtil.getInstance().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        NetDataWork.Tip.getRecordTips(getContext(),getP().getV().getImages().get(getP().getV().getCurrentPos()[0]),new com.summer.record.ui.main.image.imagedetail.NetAdapter<ArrayList<Tiplab>>(getContext()){
                            @Override
                            public void onSuccess(ArrayList<Tiplab> o) {
                                super.onSuccess(o);
                                if(getP()!=null&&getP().getU()!=null){
                                    getP().getU().setTips(o);
                                }
                            }
                        });
                    }
                }, 200);
            }
        });
    }

    @Override
    public int getBaseUILayout() {
        return R.layout.frag_main_image_imagedetail_baseui;
    }

    @Override
    protected int delayTime() {
        return 0;
    }

    @Optional
    @OnClick({ R.id.tv_refresh,R.id.tv_down,R.id.tv_search})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_refresh:
                ShareUtil.shareImage(getBaseUIAct(),getP().getV().getImages().get(getP().getV().getCurrentPos()[0]).getLocpath());
                break;
        }
    }

    @Override
    public void addTip(String content){
        Record record = getP().getV().getImages().get(getP().getV().getCurrentPos()[0]);
        ArrayList<Tiplab> tiplabs = new ArrayList<>();
        tiplabs.add(new Tiplab());
        tiplabs.get(0).setContent(content);
        record.setTiplabs(tiplabs);
        NetDataWork.Tip.addRecordTipsInfo(getContext(),record,new NetAdapter<Record>(getContext()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainAct)getBaseAct()).getP().getU().setTitleAndBottomVisible(true);
        ((MainAct)getBaseAct()).getP().getU().changeRightImage2(R.drawable.drawable_record_upload);
    }
}
