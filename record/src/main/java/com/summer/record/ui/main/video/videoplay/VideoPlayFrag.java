package com.summer.record.ui.main.video.videoplay;

//by summer on 2018-03-28.

import android.os.Bundle;

import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.constant.ValueConstant;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.summer.record.R;
import com.summer.record.data.Record;

public class VideoPlayFrag extends BaseUIFrag<VideoPlayUIOpe,VideoPlayDAOpe> {


    public static VideoPlayFrag getInstance(Record video){
        VideoPlayFrag videoPlayFrag = new VideoPlayFrag();
        videoPlayFrag.setArguments(new Bundle());
        videoPlayFrag.getArguments().putSerializable(ValueConstant.DATA_DATA,video);
        return videoPlayFrag;
    }

    @Override
    public void initNow() {
        super.initNow();

    }

    @Override
    public void initdelay() {
        super.initdelay();
        getP().getU().play((Record) getArguments().getSerializable(ValueConstant.DATA_DATA));
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
    public int getBaseUILayout() {
        return R.layout.frag_base;
    }

    @Override
    protected int delayTime() {
        return 220;
    }
}
