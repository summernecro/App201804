package com.summer.record.ui.main.text.textdeail;

//by summer on 2018-04-26.

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.base.interf.OnFinishListener;
import com.android.lib.base.listener.ViewListener;
import com.android.lib.constant.ValueConstant;
import com.android.lib.network.news.NetAdapter;
import com.android.lib.util.NullUtil;
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
        getP().getV().setRecord((Record) getArguments().getSerializable(ValueConstant.DATA_DATA));
        getP().getU().initText(getP().getV().getRecord());
    }




    @Override
    public void addTip(String content) {
        ArrayList<Tiplab> tiplabs = new ArrayList<>();
        tiplabs.add(new Tiplab());
        tiplabs.get(0).setContent(content);
        getP().getV().getRecord().setTiplabs(tiplabs);
        NetDataWork.Tip.addTextTipsInfo(getContext(),getP().getV().getRecord(),new NetAdapter<Record>(getContext()));
    }
}
