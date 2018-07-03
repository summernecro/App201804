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

import java.util.ArrayList;

import butterknife.OnClick;
import butterknife.Optional;

public class TextDetailFrag extends BaseUIFrag<TextDetailUIOpe,TextDetailDAOpe,TextDetailValue> implements OnFinishListener,TextView.OnEditorActionListener{

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


    @Optional
    @OnClick({ R.id.tv_refresh,R.id.tv_down,R.id.tv_search,R.id.iv_search_back})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.iv_search_back:
            case R.id.tv_search:
                getP().getU().showHideSearch();
                break;
        }
    }


    @Override
    public int getBaseUILayout() {
        return R.layout.frag_base;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId== EditorInfo.IME_ACTION_GO){
            ArrayList<Tiplab> tiplabs = new ArrayList<>();
            tiplabs.add(new Tiplab());
            tiplabs.get(0).setContent(v.getText().toString());
            getP().getV().getRecord().setTiplabs(tiplabs);
            NetDataWork.Tip.addTextTipsInfo(getContext(),getP().getV().getRecord(),new NetAdapter<Record>(getContext()){
                @Override
                public void onSuccess(Record o) {
                    super.onSuccess(o);
                    getP().getU().showHideSearch();
                }
            });
            return true;
        }
        return false;
    }

    @Override
    public void onFinish(Object o) {
        if(NullUtil.isStrEmpty(o.toString())){
            return;
        }

        NetDataWork.Tip.getLikeTiplab(getContext(),o.toString(),new NetAdapter<ArrayList<Tiplab>>(getContext()){

            @Override
            public void onSuccess(ArrayList<Tiplab> o) {
                super.onSuccess(o);
                getP().getV().getTiplabs().clear();
                if(o!=null&&o.size()>0){
                    getP().getV().getTiplabs().addAll(o);
                }
                getP().getU().refreshList(getP().getV().getTiplabs(), new ViewListener() {
                    @Override
                    public void onInterupt(int i, View view) {
                        ArrayList<Tiplab> tiplabs = new ArrayList<>();
                        tiplabs.add(new Tiplab());
                        tiplabs.get(0).setContent(getP().getV().getTiplabs().get(i).getContent());
                        getP().getV().getRecord().setTiplabs(tiplabs);
                        NetDataWork.Tip.addTextTipsInfo(getContext(),getP().getV().getRecord(),new NetAdapter<Record>(getContext()));
                    }
                });
            }
        });
    }
}
