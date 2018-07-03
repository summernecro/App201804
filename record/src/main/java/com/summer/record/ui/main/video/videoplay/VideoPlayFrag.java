package com.summer.record.ui.main.video.videoplay;

//by summer on 2018-03-28.

import android.os.Bundle;

import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.constant.ValueConstant;
import com.android.lib.network.news.NetAdapter;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.summer.record.R;
import com.summer.record.data.NetDataWork;
import com.summer.record.data.Record;
import com.summer.record.data.Tiplab;
import com.summer.record.ui.main.main.AddTipI;

import java.util.ArrayList;

public class VideoPlayFrag extends BaseUIFrag<VideoPlayUIOpe,VideoPlayDAOpe,VideoPlayValue> implements AddTipI{


    public static VideoPlayFrag getInstance(Record video){
        VideoPlayFrag videoPlayFrag = new VideoPlayFrag();
        videoPlayFrag.setArguments(new Bundle());
        videoPlayFrag.getArguments().putSerializable(ValueConstant.DATA_DATA,video);
        return videoPlayFrag;
    }


    @Override
    public void initNow() {
        super.initNow();
        getP().getV().setRecord((Record) getArguments().getSerializable(ValueConstant.DATA_DATA));
        getP().getU().play(getP().getV().getRecord());
    }

    @Override
    public void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }

    @Override
    protected int delayTime() {
        return 220;
    }

    @Override
    public void addTip(String content) {
        ArrayList<Tiplab> tiplabs = new ArrayList<>();
        tiplabs.add(new Tiplab());
        tiplabs.get(0).setContent(content);
        getP().getV().getRecord().setTiplabs(tiplabs);
        NetDataWork.Tip.addRecordTipsInfo(getContext(),getP().getV().getRecord(),new NetAdapter<Record>(getContext()));
    }
}
