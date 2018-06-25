package com.summer.record.ui.main.image.images;

//by summer on 2018-06-25.

import com.android.lib.base.fragment.BaseUIFrag;
import com.summer.record.R;

public class ImagesFrag extends BaseUIFrag<ImagesUIOpe,ImagesDAOpe,ImagesValue> {


    @Override
    public void initNow() {
        super.initNow();
        getP().getU().initFrag(getP().getD().getYears(getContext()),getP().getV().getImageFrags());
        getP().getU().initViewPager(getChildFragmentManager(),getContext(),getP().getV().getImageFrags());
    }

//    @Override
//    public int getBaseUILayout() {
//        return R.layout.frag_base;
//    }
}
