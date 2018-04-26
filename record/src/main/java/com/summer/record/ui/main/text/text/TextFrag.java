package com.summer.record.ui.main.text.text;

//by summer on 2018-03-27.

import android.view.View;

import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.base.interf.OnFinishListener;
import com.android.lib.base.listener.ViewListener;
import com.android.lib.network.bean.res.BaseResBean;
import com.android.lib.network.news.NetAdapter;
import com.android.lib.network.news.UINetAdapter;
import com.android.lib.util.LogUtil;
import com.android.lib.util.fragment.two.FragManager2;
import com.android.lib.view.bottommenu.MessageEvent;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.summer.record.R;
import com.summer.record.data.NetDataWork;
import com.summer.record.data.Record;
import com.summer.record.data.Records;
import com.summer.record.ui.main.main.MainValue;
import com.summer.record.ui.main.record.RecordDAOpe;
import com.summer.record.ui.main.text.textdeail.TextDetailFrag;

import java.util.ArrayList;

import butterknife.OnClick;
import butterknife.Optional;

public class TextFrag extends BaseUIFrag<TextUIOpe,RecordDAOpe> implements ViewListener{

    @Override
    public void initdelay() {
        super.initdelay();
        ArrayList<Record> list = (ArrayList<Record>) new Select().from(Record.class).queryList();
        getP().getU().initTexts(list,this);

        NetDataWork.Data.getRecordInfo(getBaseUIAct(), Record.ATYPE_TEXT, new UINetAdapter<Records>(getBaseUIFrag()) {
            @Override
            public void onSuccess(Records o) {
                super.onSuccess(o);
                getP().getD().setRecordsInfo(o);
                getP().getU().updateTitle(o.getDoneNum()+"/"+o.getAllNum());
            }
        });
    }

    @Override
    protected boolean is注册事件总线() {
        return true;
    }


    @Override
    public void dealMesage(MessageEvent event) {
        LogUtil.E("TextDB");
        ArrayList<Record> list = (ArrayList<Record>) new Select().from(Record.class).queryList();
        getP().getU().initTexts(list,this);
        ArrayList<Record> records = new ArrayList<>();
        records.add((Record) event.data);
        getP().getD().updateRecords(getBaseUIFrag(),records,new NetAdapter<ArrayList<Record>>(getBaseUIAct()));

    }


    @Optional
    @OnClick({R.id.iv_add,R.id.tv_refresh,R.id.tv_down})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.iv_add:

                break;
            case R.id.tv_refresh:
                getP().getD().setIndex(0);
                getP().getD().setPagesize(10);
                ArrayList<Record> list = (ArrayList<Record>) new Select().from(Record.class).queryList();
                final ArrayList<Record> records = new ArrayList<>();
                getP().getD().updateRecordsStep(records,getBaseUIFrag(), list, new OnFinishListener() {
                    @Override
                    public void onFinish(Object o) {
                        if(!(o instanceof String)){
                            NetDataWork.Data.getAllRecords(getBaseAct(), Record.ATYPE_TEXT,new UINetAdapter<ArrayList<Record>>(getBaseUIFrag(),UINetAdapter.Loading) {
                                @Override
                                public void onSuccess(ArrayList<Record> o) {
                                    super.onSuccess(o);
                                    getP().getU().initTexts(o,TextFrag.this);
                                }

                                @Override
                                public void onNetFinish(boolean haveData, String url, BaseResBean baseResBean) {
                                    super.onNetFinish(haveData, url, baseResBean);
                                    getP().getU().updateTitle(baseResBean.getOther());
                                }
                            });
                        }
                    }
                });
                break;
            case R.id.tv_down:

                break;
        }
    }

    @Override
    public int getBaseUILayout() {
        return R.layout.frag_base;
    }

    @Override
    public void onInterupt(int i, View view) {
        switch (i){
            case ViewListener.TYPE_ONCLICK:
                FragManager2.getInstance().start(getBaseUIAct(), MainValue.文字, TextDetailFrag.getInstance((Record) view.getTag(R.id.data)));
                break;
        }
    }
}
