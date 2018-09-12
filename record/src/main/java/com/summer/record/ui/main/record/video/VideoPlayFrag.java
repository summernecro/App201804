package com.summer.record.ui.main.record.video;

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
        getPV().setRecord((Record) getArguments().getSerializable(ValueConstant.DATA_DATA));
        getPU().play(getPV().getRecord());
    }

    @Override
    public void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    public int getBaseUILayout() {
        return R.layout.frag_main_video_videoplay_baseui;
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
        return 0;
    }

    @Override
    public void addTip(String content) {
        ArrayList<Tiplab> tiplabs = new ArrayList<>();
        tiplabs.add(new Tiplab());
        tiplabs.get(0).setContent(content);
        getPV().getRecord().setTiplabs(tiplabs);
        NetDataWork.Tip.addRecordTipsInfo(getContext(),getPV().getRecord(),new NetAdapter<Record>(getContext()));
    }
}
