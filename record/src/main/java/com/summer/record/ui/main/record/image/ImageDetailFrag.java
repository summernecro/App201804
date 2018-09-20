package com.summer.record.ui.main.record.image;

//by summer on 2018-03-28.

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.base.interf.OnFinishListener;
import com.android.lib.bean.databean.XYBean;
import com.android.lib.constant.ValueConstant;
import com.android.lib.network.news.NetAdapter;
import com.android.lib.util.ShareUtil;
import com.android.lib.util.system.HandleUtil;
import com.summer.record.R;
import com.summer.record.data.NetDataWork;
import com.summer.record.data.Record;
import com.summer.record.data.Tiplab;
import com.summer.record.tool.TitleUtil;
import com.summer.record.ui.main.main.AddTipI;
import com.summer.record.ui.main.main.MainAct;

import java.util.ArrayList;

import butterknife.OnClick;
import butterknife.Optional;

public class ImageDetailFrag extends BaseUIFrag<ImageDetailUIOpe,ImageDetailDAOpe,ImageDetailValue> implements AddTipI,TextView.OnEditorActionListener {


    public static ImageDetailFrag getInstance(ArrayList<Record> images, int pos,XYBean xyBean){
        ImageDetailFrag imageDetailFrag = new ImageDetailFrag();
        imageDetailFrag.setArguments(new Bundle());
        imageDetailFrag.getPV().setImages(images);
        imageDetailFrag.getArguments().putInt(ValueConstant.DATA_INDEX,pos);
        imageDetailFrag.getPV().setLocal(xyBean);
        return imageDetailFrag;

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View group = inflater.inflate(this.getBaseUILayout(), container, false);
        baseUIRoot = (ViewGroup)group.findViewById(com.android.lib.R.id.container);
        group.findViewById(R.id.tv_upload).setBackgroundResource(R.drawable.icon_record_share);
        TitleUtil.initTitle(getActivity(),group.findViewById(R.id.recordtitle));
        return group;
    }

    @Override
    public void initNow() {
        super.initNow();
        getPU().initImages(getChildFragmentManager(), getPV().getImages(), getArguments().getInt(ValueConstant.DATA_INDEX), getPV().getCurrentPos(), new OnFinishListener() {
            @Override
            public void onFinish(Object o) {
                HandleUtil.getInstance().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        NetDataWork.Tip.getRecordTips(getContext(),getPV().getImages().get(getPV().getCurrentPos()[0]),new com.summer.record.ui.main.record.image.NetAdapter<ArrayList<Tiplab>>(getContext()){
                            @Override
                            public void onSuccess(ArrayList<Tiplab> o) {
                                super.onSuccess(o);
                                if(getP()!=null&&getPU()!=null){
                                    getPU().setTips(o);
                                }
                            }
                        });
                    }
                }, 200);
            }
        });
        getPU().anim(getPV().getLocal());
    }

    @Override
    protected int delayTime() {
        return 0;
    }

    @Override
    public int getBaseUILayout() {
        return R.layout.frag_main_image_imagedetail_baseui;
    }

    @Optional
    @OnClick({ R.id.tv_upload,R.id.tv_down,R.id.tv_search,R.id.iv_search_back})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_upload:
                ShareUtil.shareImage(getBaseUIAct(),getPV().getImages().get(getPV().getCurrentPos()[0]).getLocpath());
                break;
            case R.id.iv_search_back:
                TitleUtil.showHideSearch(this);
                break;
            case R.id.tv_search:
                TitleUtil.showHideSearch(this);
                break;
        }
    }

    @Override
    public void addTip(String content){
        Record record = getPV().getImages().get(getPV().getCurrentPos()[0]);
        ArrayList<Tiplab> tiplabs = new ArrayList<>();
        tiplabs.add(new Tiplab());
        tiplabs.get(0).setContent(content);
        record.setTiplabs(tiplabs);
        NetDataWork.Tip.addRecordTipsInfo(getContext(),record,new NetAdapter<Record>(getContext()));
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId== EditorInfo.IME_ACTION_GO){
            TitleUtil.showHideSearch(this);
            addTip(v.getText().toString());
            return true;
        }
        return false;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainAct)getBaseAct()).getPU().setTitleAndBottomVisible(true);
        ((MainAct)getBaseAct()).getPU().changeRightImage2(R.drawable.drawable_record_upload);
    }
}
