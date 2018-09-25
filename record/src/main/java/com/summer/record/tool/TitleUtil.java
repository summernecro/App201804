package com.summer.record.tool;

//by summer on 2018-08-29.

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.lib.base.adapter.AppsDataBindingAdapter;
import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.base.interf.OnFinishListener;
import com.android.lib.base.listener.BaseTextWather;
import com.android.lib.base.listener.ViewListener;
import com.android.lib.util.ScreenUtil;
import com.summer.record.BR;
import com.summer.record.R;
import com.summer.record.data.Tiplab;
import com.summer.record.databinding.ItemRecordTitleSearchBinding;
import com.summer.record.ui.main.main.MainAct;

import java.util.ArrayList;

import butterknife.ButterKnife;

import static com.android.lib.R2.id.context;

public class TitleUtil {


    public static void initTitle(Context context, View view){
        view.setPadding(0, (int) ScreenUtil.getInstance().getStatusBarHeight(context),0,0);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = (int) (ScreenUtil.getInstance().getStatusBarHeight(context)+ScreenUtil.最小DIMEN*38);
        view.setLayoutParams(params);
    }

    public static void addSearhView(BaseUIFrag baseUIFrag){
        ViewGroup viewGroup = baseUIFrag.getBaseUIRoot();
        ItemRecordTitleSearchBinding itemRecordTitleSearchBinding = ItemRecordTitleSearchBinding.inflate(LayoutInflater.from(baseUIFrag.getBaseUIAct()));
        ViewGroup.LayoutParams params = itemRecordTitleSearchBinding.searchtitle.getLayoutParams();
        itemRecordTitleSearchBinding.searchtitle.setPadding(0, (int) ScreenUtil.getInstance().getStatusBarHeight(baseUIFrag.getBaseUIAct()),0,0);
        params.height = (int) (ScreenUtil.getInstance().getStatusBarHeight(baseUIFrag.getBaseUIAct())+ScreenUtil.最小DIMEN*38);
        itemRecordTitleSearchBinding.searchtitle.setLayoutParams(params);
        itemRecordTitleSearchBinding.getRoot().setVisibility(View.GONE);
        viewGroup.addView(itemRecordTitleSearchBinding.getRoot());
        ButterKnife.bind(viewGroup);
        itemRecordTitleSearchBinding.recycleTips.setLayoutManager(new LinearLayoutManager(baseUIFrag.getBaseUIAct()));
        if(baseUIFrag instanceof TextView.OnEditorActionListener){
            itemRecordTitleSearchBinding.edtSearch.setOnEditorActionListener((TextView.OnEditorActionListener) baseUIFrag);
        }

        if(baseUIFrag instanceof OnFinishListener){
            final OnFinishListener listener = (OnFinishListener) baseUIFrag;
            itemRecordTitleSearchBinding.edtSearch.addTextChangedListener(new BaseTextWather(){
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    super.onTextChanged(s, start, before, count);
                    listener.onFinish(s.toString());
                }
            });
        }
    }

    public static int showHideSearch(BaseUIFrag baseUIFrag){
        ViewGroup viewGroup =baseUIFrag.getBaseUIRoot();
        ItemRecordTitleSearchBinding itemRecordTitleSearchBinding = DataBindingUtil.bind((viewGroup).getChildAt( (viewGroup).getChildCount()-1));
        if(itemRecordTitleSearchBinding.getRoot().getVisibility()==View.GONE){
            itemRecordTitleSearchBinding.getRoot().setVisibility(View.VISIBLE);
        }else{
            itemRecordTitleSearchBinding.getRoot().setVisibility(View.GONE);
        }
        return itemRecordTitleSearchBinding.getRoot().getVisibility();
    }

    public static void refreshList(BaseUIFrag baseUIFrag,ArrayList<Tiplab> tiplabs, ViewListener listener){
        ViewGroup viewGroup =baseUIFrag.getBaseUIRoot();
        ItemRecordTitleSearchBinding itemRecordTitleSearchBinding = DataBindingUtil.bind((viewGroup).getChildAt( (viewGroup).getChildCount()-1));
        if(itemRecordTitleSearchBinding.recycleTips.getAdapter()==null){
            itemRecordTitleSearchBinding.recycleTips.setAdapter(new AppsDataBindingAdapter(baseUIFrag.getBaseUIAct(), R.layout.item_tiplab_text, BR.item_tiplab_text,tiplabs,listener));
        }else{
            itemRecordTitleSearchBinding.recycleTips.getAdapter().notifyDataSetChanged();
        }
    }

}