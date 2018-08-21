package com.summer.record.ui.main.main;

//by summer on 2018-03-27.

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.lib.base.activity.BaseUIActivity;
import com.android.lib.base.adapter.AppsDataBindingAdapter;
import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.base.interf.OnFinishListener;
import com.android.lib.base.interf.view.OnAppItemSelectListener;
import com.android.lib.base.listener.BaseTextWather;
import com.android.lib.base.listener.ViewListener;
import com.android.lib.base.ope.BaseUIOpe;
import com.android.lib.util.ScreenUtil;
import com.android.lib.util.StringUtil;
import com.android.lib.util.fragment.two.FragManager2;
import com.android.lib.view.bottommenu.BottomMenuBean;
import com.summer.record.BR;
import com.summer.record.R;
import com.summer.record.data.Tiplab;
import com.summer.record.databinding.ActMainBinding;
import com.summer.record.databinding.ItemRecordTitleSearchBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.ButterKnife;

public class MainUIOpe extends BaseUIOpe<ActMainBinding>{

    ArrayList<BottomMenuBean> bottomMenuBeans = new ArrayList<>();


    @Override
    public void initUI() {
        super.initUI();
        addSearhView();
        getBind().recordtitle.getRoot().setPadding(0, (int) ScreenUtil.getInstance().getStatusBarHeight(getActivity()),0,0);
        ViewGroup.LayoutParams params = getBind().recordtitle.getRoot().getLayoutParams();
        params.height = (int) (ScreenUtil.getInstance().getStatusBarHeight(getActivity())+ScreenUtil.最小DIMEN*38);
        getBind().recordtitle.getRoot().setLayoutParams(params);
        bottomMenuBeans.add(new BottomMenuBean("视频", R.drawable.drawable_record_main_bottom_video,null,getBind().containVideo,getActivity().getResources().getColorStateList(R.color.color_white_black)));
        bottomMenuBeans.add(new BottomMenuBean("图片", R.drawable.drawable_record_main_bottom_image,null,getBind().containImage,getActivity().getResources().getColorStateList(R.color.color_white_black)));
        bottomMenuBeans.add(new BottomMenuBean("文字", R.drawable.drawable_record_main_bottom_text,null,getBind().containText,getActivity().getResources().getColorStateList(R.color.color_white_black)));
        //bottomMenuBeans.add(new BottomMenuBean("设置", R.drawable.drawable_record_main_bottom_setting,null,getBind().containSetting,getActivity().getResources().getColorStateList(R.color.color_white_black)));
        getBind().bottommenu.initItems(bottomMenuBeans);
        if(getActivity() instanceof OnAppItemSelectListener){
            getBind().bottommenu.setOnAppItemClickListener((OnAppItemSelectListener)getActivity());
            getBind().bottommenu.setIndex(0);
        }
    }

    public void showView(int pos){
       for(int i=0;i<bottomMenuBeans.size();i++){
           if(i==pos){
               bottomMenuBeans.get(i).getContainerView().setVisibility(View.VISIBLE);
           }else{
               bottomMenuBeans.get(i).getContainerView().setVisibility(View.GONE);
           }
       }
    }

    public void initFrag(BaseUIActivity activity, ArrayList<BaseUIFrag> fragments, int[] 模块ID, String[] 模块){
        for(int i=0;i<fragments.size();i++){
            FragManager2.getInstance().setAnim(false).start(activity,模块[i],模块ID[i],fragments.get(i));
        }
    }


    public void addSearhView(){
        MainAct mainAct = (MainAct) getActivity();
        ViewGroup viewGroup =mainAct.getBaseUIRoot();
        ItemRecordTitleSearchBinding itemRecordTitleSearchBinding = ItemRecordTitleSearchBinding.inflate(LayoutInflater.from(getActivity()));
        ViewGroup.LayoutParams params = itemRecordTitleSearchBinding.searchtitle.getLayoutParams();
        itemRecordTitleSearchBinding.searchtitle.setPadding(0, (int) ScreenUtil.getInstance().getStatusBarHeight(getActivity()),0,0);
        params.height = (int) (ScreenUtil.getInstance().getStatusBarHeight(getActivity())+ScreenUtil.最小DIMEN*38);
        itemRecordTitleSearchBinding.searchtitle.setLayoutParams(params);
        itemRecordTitleSearchBinding.getRoot().setVisibility(View.GONE);
        viewGroup.addView(itemRecordTitleSearchBinding.getRoot());
        ButterKnife.bind(viewGroup);
        itemRecordTitleSearchBinding.recycleTips.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(mainAct instanceof TextView.OnEditorActionListener){
            itemRecordTitleSearchBinding.edtSearch.setOnEditorActionListener((TextView.OnEditorActionListener) mainAct);
        }

        if(mainAct instanceof OnFinishListener){
            final OnFinishListener listener = (OnFinishListener) mainAct;
            itemRecordTitleSearchBinding.edtSearch.addTextChangedListener(new BaseTextWather(){
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    super.onTextChanged(s, start, before, count);
                    listener.onFinish(s.toString());
                }
            });
        }
    }

    public int showHideSearch(){
        MainAct mainAct = (MainAct) getActivity();
        ViewGroup viewGroup =mainAct.getBaseUIRoot();
        ItemRecordTitleSearchBinding itemRecordTitleSearchBinding = DataBindingUtil.bind((viewGroup).getChildAt( (viewGroup).getChildCount()-1));
        if(itemRecordTitleSearchBinding.getRoot().getVisibility()==View.GONE){
            itemRecordTitleSearchBinding.getRoot().setVisibility(View.VISIBLE);
        }else{
            itemRecordTitleSearchBinding.getRoot().setVisibility(View.GONE);
        }
        return itemRecordTitleSearchBinding.getRoot().getVisibility();
    }

    public void refreshList(ArrayList<Tiplab> tiplabs, ViewListener listener){
        MainAct mainAct = (MainAct) getActivity();
        ViewGroup viewGroup =mainAct.getBaseUIRoot();
        ItemRecordTitleSearchBinding itemRecordTitleSearchBinding = DataBindingUtil.bind((viewGroup).getChildAt( (viewGroup).getChildCount()-1));
        if(itemRecordTitleSearchBinding.recycleTips.getAdapter()==null){
            itemRecordTitleSearchBinding.recycleTips.setAdapter(new AppsDataBindingAdapter(getActivity(), R.layout.item_tiplab_text, BR.item_tiplab_text,tiplabs,listener));
        }else{
            itemRecordTitleSearchBinding.recycleTips.getAdapter().notifyDataSetChanged();
        }
    }

    public void updateTitle(Object o){
        getBind().recordtitle.tvLab.setText(StringUtil.getStr(o));
    }

    public void setTitleAndBottomVisible(boolean visible){
        getBind().recordtitle.getRoot().setVisibility(visible?View.VISIBLE:View.GONE);
        getBind().bottommenu.setVisibility(visible?View.VISIBLE:View.GONE);
    }

    public void swithTitleAndBottomVisible(){
            setTitleAndBottomVisible(getBind().recordtitle.getRoot().getVisibility()==View.VISIBLE?false:true);
    }


}
