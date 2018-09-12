package com.summer.record.ui.main.record.image;

//by summer on 2018-03-28.

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import com.android.lib.base.interf.OnFinishListener;
import com.android.lib.base.listener.BaseOnPagerChangeListener;
import com.android.lib.base.ope.BaseUIOpe;
import com.summer.record.R;
import com.summer.record.data.Record;
import com.summer.record.data.Tiplab;
import com.summer.record.databinding.FragMainImageImagedetailBinding;
import com.summer.record.tool.TitleUtil;
import com.summer.record.ui.main.record.image.imageshow.ImageShowFrag;

import java.util.ArrayList;

public class ImageDetailUIOpe extends BaseUIOpe<FragMainImageImagedetailBinding> {

    @Override
    public void initUI() {
        super.initUI();
        changeRightImage2(R.drawable.icon_record_share);
        TitleUtil.initTitle(getActivity(),getView().findViewById(R.id.recordtitle));
        TitleUtil.addSearhView(getFrag());
    }

    public void initImages(FragmentManager fragmentManager, final ArrayList<Record> images, final int pos, final int[] currentpos, final OnFinishListener onFinishListener){
        getBind().viewpager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return ImageShowFrag.getInstance(images.get(position),getFrag());
            }

            @Override
            public int getCount() {
                return images.size();
            }
        });
        BaseOnPagerChangeListener baseOnPagerChangeListener = new BaseOnPagerChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentpos[0] = position;
                onFinishListener.onFinish(position);
            }
        };
        getBind().viewpager.addOnPageChangeListener(baseOnPagerChangeListener);
        getBind().viewpager.setCurrentItem(pos);
        baseOnPagerChangeListener.onPageSelected(pos);
    }

    public void setTips(ArrayList<Tiplab> tips){
        String tip = "";
        for(int i=0;tips!=null&&i<tips.size();i++){
            tip+=tips.get(i).getContent()+",";
        }
        if(tip.endsWith(",")){
            tip = tip.substring(0,tip.length()-1);
        }
        getBind().tvTips.setText(tip);
    }

    public void setTitleVisible(boolean visible){
        getView().findViewById(R.id.recordtitle).setVisibility(visible?View.VISIBLE:View.GONE);
    }

    public void switchTitleVisible(){
        setTitleVisible(getView().findViewById(R.id.recordtitle).getVisibility()==View.VISIBLE?false:true);
    }

    public void changeRightImage2(int res){
        getView().findViewById(R.id.tv_upload).setBackgroundResource(res);
    }
}
