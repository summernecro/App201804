package com.summer.record.ui.main.video.videos;

//by summer on 2018-06-26.

import android.view.View;

import com.android.lib.base.fragment.BaseUIFrag;
import com.summer.record.R;
import com.summer.record.data.Record;
import com.summer.record.ui.main.image.image.ImageFrag;
import com.summer.record.ui.main.main.RefreshI;
import com.summer.record.ui.main.video.video.VideoFrag;

import java.util.ArrayList;

import butterknife.OnClick;
import butterknife.Optional;

public class VideosFrag extends BaseUIFrag<VideosUIOpe,VideosDAOpe,VideosValue> implements RefreshI {

    @Override
    public void initNow() {
        super.initNow();
        getP().getU().initFrag(this,getP().getD().getYears(getContext()),getP().getV().getVideoFrags());
        getP().getU().initViewPager(getChildFragmentManager(),getContext(),getP().getV().getVideoFrags());
    }

    @Optional
    @OnClick({ R.id.tv_refresh,R.id.tv_down})
    public void onClick(View v) {
        super.onClick(v);
        getP().getU().getCurrentFrag(getP().getV().getVideoFrags()).onClick(v);
    }

    @Override
    public void refresh(ArrayList<Record> o) {
        VideoFrag videoFrag = (VideoFrag) getP().getV().getVideoFrags().get(0);
        if(o==null){
            videoFrag.getP().getV().getRecords().clear();
            videoFrag.getP().getV().getRecords().addAll(videoFrag.getP().getV().getAllRecords());
            videoFrag.getP().getU().loadVideos(videoFrag.getP().getV().getRecords(),videoFrag);
            return;
        }
        ArrayList<Record> records = new ArrayList<>();
        for(int i=0;o!=null&&i<o.size();i++){
            if(o.get(i).getAtype().equals("video")){
                records.add(o.get(i));
            }
        }
        if(records.size()==0){
            return;
        }
        videoFrag.getP().getV().getRecords().clear();
        videoFrag.getP().getV().getRecords().addAll(videoFrag.getP().getD().dealRecord(records));
        videoFrag.getP().getU().loadVideos(videoFrag.getP().getV().getRecords(),videoFrag);

    }
}
