package com.summer.record.ui.main.text.textdeail;

//by summer on 2018-04-26.

import android.os.Bundle;

import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.constant.ValueConstant;
import com.summer.record.R;
import com.summer.record.data.Record;

public class TextDetailFrag extends BaseUIFrag<TextDetailUIOpe,TextDetailDAOpe>{

    public static TextDetailFrag getInstance(Record record){
        TextDetailFrag textDetailFrag = new TextDetailFrag();
        textDetailFrag.setArguments(new Bundle());
        textDetailFrag.getArguments().putSerializable(ValueConstant.DATA_DATA,record);
        return textDetailFrag;
    }

    @Override
    public void initdelay() {
        super.initdelay();
        getP().getU().initText((Record) getArguments().getSerializable(ValueConstant.DATA_DATA));
    }

    @Override
    public int getBaseUILayout() {
        return R.layout.frag_base;
    }
}
