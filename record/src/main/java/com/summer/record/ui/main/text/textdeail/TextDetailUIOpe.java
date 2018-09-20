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
import com.summer.record.tool.TitleUtil;

import java.util.ArrayList;

import butterknife.ButterKnife;


public class TextDetailUIOpe extends BaseUIOpe<FragTextTextdetailBinding>{


    @Override
    public void initUI() {
        super.initUI();
        TitleUtil.initTitle(getActivity(),getView().findViewById(R.id.recordtitle));
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

}
