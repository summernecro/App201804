package com.summer.record.ui.main.record.records;

//by summer on 2018-06-25.

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.android.lib.base.adapter.AppBasePagerAdapter;
import com.android.lib.base.adapter.AppsDataBindingAdapter;
import com.android.lib.base.listener.BaseOnPagerChangeListener;
import com.android.lib.base.listener.ViewListener;
import com.android.lib.base.ope.BaseUIOpe;
import com.android.lib.util.LogUtil;
import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.summer.record.BR;
import com.summer.record.R;
import com.summer.record.databinding.FragMainImagesBinding;
import com.summer.record.tool.TitleUtil;
import com.summer.record.ui.main.record.record.RecordFrag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecordsUIOpe extends BaseUIOpe<FragMainImagesBinding> {


    @Override
    public void initUI() {
        super.initUI();
//        GlideApp.with(getActivity()).asBitmap()
//                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530075649656&di=dd57d7701506ea32e336a6c2424c26eb&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fmobile%2Fd%2F53917b6fcaa98_200_300.jpg")
//                .into(getBind().ivBg);
        getBind().ivBg.setBackgroundColor(getActivity().getResources().getColor(R.color.color_grey_200));
       // TitleUtil.initTitle(getActivity(),getBind().recordtitle.getRoot());

    }

    public void initViewPager(FragmentManager fm, Context context, final List<Fragment> fragments, final ArrayList<Integer> pos){
        getBind().viewpager.setAdapter(new AppBasePagerAdapter(fm, context, fragments));
        getBind().viewpager.setOffscreenPageLimit(fragments.size());
        getBind().viewpager.addOnPageChangeListener(new BaseOnPagerChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                pos.set(0,position);
                updateTitle(((RecordFrag)fragments.get(position)).getPV().getTitleStr());
            }
        });
    }

    public void initFrag(RecordsFrag recordsFrag, String type, ArrayList<String[]> strs, ArrayList<Fragment> imageFrags ){
        imageFrags.clear();
        for(int i=0;i<strs.size();i++){
            RecordFrag recordFrag = RecordFrag.getInstance(strs.get(i),type, recordsFrag,null);
            imageFrags.add(recordFrag);
        }

    }

    public int getCurrentItem(){
        return getBind().viewpager.getCurrentItem();
    }

    public RecordFrag getCurrentFrag(ArrayList<Fragment> imageFrags){
        return (RecordFrag) imageFrags.get(getBind().viewpager.getCurrentItem());
    }

    public void setTitleVisible(int visible){
        //getBind().recordtitle.getRoot().setVisibility(visible);
    }

    public void updateTitle(String title){
        //getBind().recordtitle.tvLab.setText(title);
    }

    public void showHideSort(boolean show, ArrayList<String> sorts, View.OnClickListener listener){
        if(show){
            if(getBind().sortlist.getAdapter()==null){
                getBind().sortlist.setLayoutManager(new LinearLayoutManager(getActivity()));
                getBind().sortlist.setAdapter(new AppsDataBindingAdapter(getActivity(),R.layout.item_sort_text,BR.item_sort_text,sorts,listener));
            }
            ViewAnimator.animate( getBind().sortlist).translationY(-getBind().sortlist.getHeight(),0).duration(500).onStart(new AnimationListener.Start() {
                @Override
                public void onStart() {
                    getBind().sortlist.setVisibility(View.VISIBLE);
                }
            }).start();
        }else{
            ViewAnimator.animate(getBind().sortlist).translationY(0,-getBind().sortlist.getHeight()).duration(500).onStop(new AnimationListener.Stop() {
                @Override
                public void onStop() {
                    getBind().sortlist.setVisibility(View.GONE);
                }
            }).start();
        }
    }

    public void switchSort(ArrayList<String> sorts,View.OnClickListener listener){
        showHideSort(getBind().sortlist.getVisibility()!=View.VISIBLE,sorts,listener);
    }

//
//    public void onStart() {
//    }
//
//    public void onBackOut() {
//    }
//
//    public void onRemove(AnimationListener.Stop stopListener) {
//        stopListener.onStop();
//    }
//
//    public void onBackIn() {
//    }
}
