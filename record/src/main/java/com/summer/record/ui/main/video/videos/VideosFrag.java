package com.summer.record.ui.main.video.videos;

//by summer on 2018-06-26.

import android.view.View;

import com.android.lib.base.fragment.BaseUIFrag;
import com.summer.record.R;

import butterknife.OnClick;
import butterknife.Optional;

public class VideosFrag extends BaseUIFrag<VideosUIOpe,VideosDAOpe,VideosValue> {

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
    public int getBaseUILayout() {
        return R.layout.frag_base;
    }
}
