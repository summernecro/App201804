package com.summer.record.ui.main.image.imagedetail;

//by summer on 2018-03-28.

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.base.ope.BaseUIOpe;
import com.summer.record.data.Record;
import com.summer.record.databinding.FragMainImageImagedetailBinding;
import com.summer.record.ui.main.image.imagedetail.imageshow.ImageShowFrag;

import java.util.ArrayList;

public class ImageDetailUIOpe extends BaseUIOpe<FragMainImageImagedetailBinding> {

    public void initImages(FragmentManager fragmentManager, final ArrayList<Record> images, int pos){
        getBind().viewpager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return ImageShowFrag.getInstance(images.get(position));
            }

            @Override
            public int getCount() {
                return images.size();
            }
        });
        getBind().viewpager.setCurrentItem(pos);
    }
}
