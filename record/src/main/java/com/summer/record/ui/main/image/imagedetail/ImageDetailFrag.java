package com.summer.record.ui.main.image.imagedetail;

//by summer on 2018-03-28.

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.base.interf.OnFinishListener;
import com.android.lib.base.listener.ViewListener;
import com.android.lib.constant.ValueConstant;
import com.android.lib.network.news.NetAdapter;
import com.android.lib.util.LogUtil;
import com.android.lib.util.NullUtil;
import com.android.lib.util.StringUtil;
import com.android.lib.util.system.HandleUtil;
import com.summer.record.R;
import com.summer.record.data.NetDataWork;
import com.summer.record.data.Record;
import com.summer.record.data.Tiplab;

import java.util.ArrayList;

import butterknife.OnClick;
import butterknife.Optional;

public class ImageDetailFrag extends BaseUIFrag<ImageDetailUIOpe,ImageDetailDAOpe,ImageDetailValue> implements TextView.OnEditorActionListener,OnFinishListener {


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
                }, 1000);
            }
        });
    }

    @Optional
    @OnClick({ R.id.tv_refresh,R.id.tv_down,R.id.tv_search,R.id.iv_search_back})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.iv_search_back:
            case R.id.tv_search:
                getP().getU().showHideSearch();
                break;
        }
    }

    @Override
    public int getBaseUILayout() {
        return R.layout.frag_base;
    }

    @Override
    protected int delayTime() {
        return 210;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId== EditorInfo.IME_ACTION_GO){
             Record record = getP().getV().getImages().get(getP().getV().getCurrentPos()[0]);
             ArrayList<Tiplab> tiplabs = new ArrayList<>();
            tiplabs.add(new Tiplab());
            tiplabs.get(0).setContent(v.getText().toString());
             record.setTiplabs(tiplabs);
            NetDataWork.Tip.addRecordTipsInfo(getContext(),record,new NetAdapter<Record>(getContext()){
                @Override
                public void onSuccess(Record o) {
                    super.onSuccess(o);
                    getP().getU().showHideSearch();
                }
            });
            return true;
        }
        return false;
    }

    @Override
    public void onFinish(Object o) {
        if(NullUtil.isStrEmpty(o.toString())){
            return;
        }

        NetDataWork.Tip.getLikeTiplab(getContext(),o.toString(),new NetAdapter<ArrayList<Tiplab>>(getContext()){



            @Override
            public void onSuccess(ArrayList<Tiplab> o) {
                super.onSuccess(o);
                getP().getV().getTiplabs().clear();
                if(o!=null&&o.size()>0){
                    getP().getV().getTiplabs().addAll(o);
                }
                getP().getU().refreshList(getP().getV().getTiplabs(), new ViewListener() {
                    @Override
                    public void onInterupt(int i, View view) {
                        Record record = getP().getV().getImages().get(getP().getV().getCurrentPos()[0]);
                        ArrayList<Tiplab> tiplabs = new ArrayList<>();
                        tiplabs.add(new Tiplab());
                        tiplabs.get(0).setContent(getP().getV().getTiplabs().get(i).getContent());
                        record.setTiplabs(tiplabs);
                        NetDataWork.Tip.addRecordTipsInfo(getContext(),record,new NetAdapter<Record>(getContext()));
                    }
                });
            }
        });
    }
}
