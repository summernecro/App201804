package com.summer.record.ui.main.image.images;

//by summer on 2018-06-25.

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;

import com.android.lib.GlideApp;
import com.android.lib.base.adapter.AppBasePagerAdapter2;
import com.android.lib.base.listener.BaseOnPagerChangeListener;
import com.android.lib.base.ope.BaseUIOpe;
import com.android.lib.util.StringUtil;
import com.summer.record.R;
import com.summer.record.databinding.FragMainImagesBinding;
import com.summer.record.ui.main.image.image.ImageFrag;

import java.util.ArrayList;
import java.util.List;

public class ImagesUIOpe extends BaseUIOpe<FragMainImagesBinding> {

    @Override
    public void initUI() {
        super.initUI();
    }

    public void initViewPager(FragmentManager fm, Context context, final List<Fragment> fragments){
        GlideApp.with(context).asBitmap()
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530018452637&di=e061f759f7931924942a2bb7066a45c4&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fblog%2F201409%2F12%2F20140912014711_yQvBz.thumb.700_0.jpeg")
                .into(getBind().ivBg);
        getBind().viewpager.setAdapter(new AppBasePagerAdapter2(fm, context, fragments));
        getBind().viewpager.addOnPageChangeListener(new BaseOnPagerChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateTitle(((ImageFrag)fragments.get(position)).getP().getV().getTitleStr());
            }
        });
    }

    public void initFrag(ImagesFrag imagesFrag,ArrayList<String[]> strs,ArrayList<Fragment> imageFrags ){
        for(int i=0;i<strs.size();i++){
            ImageFrag imageFrag = ImageFrag.getInstance(strs.get(i),imagesFrag);
            imageFrags.add(imageFrag);
        }
    }

    public ImageFrag getCurrentFrag(ArrayList<Fragment> imageFrags){
        return (ImageFrag) imageFrags.get(getBind().viewpager.getCurrentItem());
    }

    public void updateTitle(Object o){
        TextView textView = getView().findViewById(R.id.tv_lab);
        textView.setText(StringUtil.getStr(o));
    }
}
