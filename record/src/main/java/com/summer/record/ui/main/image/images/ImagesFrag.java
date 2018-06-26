package com.summer.record.ui.main.image.images;

//by summer on 2018-06-25.

import android.view.View;

import com.android.lib.base.fragment.BaseUIFrag;
import com.summer.record.R;

import butterknife.OnClick;
import butterknife.Optional;

public class ImagesFrag extends BaseUIFrag<ImagesUIOpe,ImagesDAOpe,ImagesValue> {


    @Override
    public void initNow() {
        super.initNow();
        getP().getU().initFrag(this,getP().getD().getYears(getContext()),getP().getV().getImageFrags());
        getP().getU().initViewPager(getChildFragmentManager(),getContext(),getP().getV().getImageFrags());
    }


    @Optional
    @OnClick({R.id.iv_add,R.id.tv_refresh,R.id.tv_down})
    public void onClick(View v) {
        super.onClick(v);
        getP().getU().getCurrentFrag(getP().getV().getImageFrags()).onClick(v);
    }

    @Override
    public int getBaseUILayout() {
        return R.layout.frag_base;
    }
}
