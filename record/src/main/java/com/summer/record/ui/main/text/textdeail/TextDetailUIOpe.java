package com.summer.record.ui.main.text.textdeail;

//by summer on 2018-04-26.

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.android.lib.base.adapter.AppsDataBindingAdapter;
import com.android.lib.base.interf.OnFinishListener;
import com.android.lib.base.listener.BaseTextWather;
import com.android.lib.base.listener.ViewListener;
import com.android.lib.base.ope.BaseUIOpe;
import com.summer.record.BR;
import com.summer.record.R;
import com.summer.record.data.Record;
import com.summer.record.data.Tiplab;
import com.summer.record.databinding.FragTextTextdetailBinding;
import com.summer.record.databinding.FragTextWebBinding;
import com.summer.record.databinding.ItemRecordTitleSearchBinding;

import java.util.ArrayList;

import butterknife.ButterKnife;


public class TextDetailUIOpe extends BaseUIOpe<FragTextTextdetailBinding>{


    @Override
    public void initUI() {
        super.initUI();
        addSearhView();
    }

    public void initText(Record record){

        getBind().ivText.setText(record.getContent());

        if(record.getContent().startsWith("http")){
            FragTextWebBinding fragTextWebBinding = FragTextWebBinding.inflate(LayoutInflater.from(getActivity()));
            fragTextWebBinding.wbWeb.setWebViewClient(new WebViewClient());
            WebSettings webSettings=fragTextWebBinding.wbWeb.getSettings();
            webSettings.setJavaScriptEnabled(true);//允许使用js
            webSettings.setDisplayZoomControls(false);
            fragTextWebBinding.wbWeb.loadUrl(record.getContent());
            ((ViewGroup)getView().findViewById(R.id.container)).addView(fragTextWebBinding.getRoot(),new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    public int showHideSearch(){
        ItemRecordTitleSearchBinding itemRecordTitleSearchBinding = DataBindingUtil.bind(((ViewGroup)getView()).getChildAt( ((ViewGroup) getView()).getChildCount()-1));
        if(itemRecordTitleSearchBinding.getRoot().getVisibility()== View.GONE){
            itemRecordTitleSearchBinding.getRoot().setVisibility(View.VISIBLE);
        }else{
            itemRecordTitleSearchBinding.getRoot().setVisibility(View.GONE);
        }
        return itemRecordTitleSearchBinding.getRoot().getVisibility();
    }

    public void addSearhView(){
        ViewGroup viewGroup = (ViewGroup) getView();
        ItemRecordTitleSearchBinding itemRecordTitleSearchBinding = ItemRecordTitleSearchBinding.inflate(LayoutInflater.from(getActivity()));
        itemRecordTitleSearchBinding.getRoot().setVisibility(View.GONE);
        viewGroup.addView(itemRecordTitleSearchBinding.getRoot());
        ButterKnife.bind(getFrag(),getView());
        itemRecordTitleSearchBinding.recycleTips.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(getFrag() instanceof TextView.OnEditorActionListener){
            itemRecordTitleSearchBinding.edtSearch.setOnEditorActionListener((TextView.OnEditorActionListener) getFrag());
        }

        if(getFrag() instanceof OnFinishListener){
            final OnFinishListener listener = (OnFinishListener) getFrag();
            itemRecordTitleSearchBinding.edtSearch.addTextChangedListener(new BaseTextWather(){
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    super.onTextChanged(s, start, before, count);
                    listener.onFinish(s.toString());
                }
            });
        }
    }

    public void refreshList(ArrayList<Tiplab> tiplabs, ViewListener listener){
        ItemRecordTitleSearchBinding itemRecordTitleSearchBinding = DataBindingUtil.bind(((ViewGroup)getView()).getChildAt( ((ViewGroup) getView()).getChildCount()-1));
        if(itemRecordTitleSearchBinding.recycleTips.getAdapter()==null){
            itemRecordTitleSearchBinding.recycleTips.setAdapter(new AppsDataBindingAdapter(getActivity(), R.layout.item_tiplab_text, BR.item_tiplab_text,tiplabs,listener));
        }else{
            itemRecordTitleSearchBinding.recycleTips.getAdapter().notifyDataSetChanged();
        }
    }

}
