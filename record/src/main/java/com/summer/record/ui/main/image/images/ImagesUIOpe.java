package com.summer.record.ui.main.image.images;

//by summer on 2018-06-25.

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.android.lib.base.adapter.AppBasePagerAdapter2;
import com.android.lib.base.ope.BaseUIOpe;
import com.summer.record.databinding.FragMainImagesBinding;
import com.summer.record.ui.main.image.image.ImageFrag;

import java.util.ArrayList;
import java.util.List;

public class ImagesUIOpe extends BaseUIOpe<FragMainImagesBinding> {



    public void initViewPager(FragmentManager fm, Context context, List<Fragment> fragments){
        getBind().viewpager.setAdapter(new AppBasePagerAdapter2(fm, context, fragments));
    }

    public void initFrag(ArrayList<String[]> strs,ArrayList<Fragment> imageFrags ){
        for(int i=0;i<strs.size();i++){
            ImageFrag imageFrag = ImageFrag.getInstance(strs.get(i));
            imageFrags.add(imageFrag);
        }
    }
}
