package com.summer.record.ui.main.text.textdeail;

//by summer on 2018-04-26.

import android.os.Bundle;
import android.view.View;

import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.constant.ValueConstant;
import com.android.lib.network.news.NetAdapter;
import com.android.lib.util.ShareUtil;
import com.summer.record.R;
import com.summer.record.data.NetDataWork;
import com.summer.record.data.Record;
import com.summer.record.data.Tiplab;
import com.summer.record.ui.main.main.AddTipI;

import java.util.ArrayList;

import butterknife.OnClick;
import butterknife.Optional;

public class TextDetailFrag extends BaseUIFrag<TextDetailUIOpe,TextDetailDAOpe,TextDetailValue> implements AddTipI {

    public static TextDetailFrag getInstance(Record record){
        TextDetailFrag textDetailFrag = new TextDetailFrag();
        textDetailFrag.setArguments(new Bundle());
        textDetailFrag.getArguments().putSerializable(ValueConstant.DATA_DATA,record);
        return textDetailFrag;
    }

    @Override
    public void initNow() {
        super.initNow();
        getPV().setRecord((Record) getArguments().getSerializable(ValueConstant.DATA_DATA));
        getPU().initText(getPV().getRecord());
    }

    @Optional
    @OnClick({ R.id.tv_upload,R.id.tv_down,R.id.tv_search})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_upload:
                ShareUtil.shareText(this,getPV().getRecord().getContent());
                break;
        }
    }

    @Override
    public int getBaseUILayout() {
        return R.layout.frag_main_text_textdetail_baseui;
    }

    @Override
    protected int delayTime() {
        return 0;
    }

    @Override
    public void addTip(String content) {
        ArrayList<Tiplab> tiplabs = new ArrayList<>();
        tiplabs.add(new Tiplab());
        tiplabs.get(0).setContent(content);
        getPV().getRecord().setTiplabs(tiplabs);
        NetDataWork.Tip.addTextTipsInfo(getContext(),getPV().getRecord(),new NetAdapter<Record>(getContext()));
    }
}
