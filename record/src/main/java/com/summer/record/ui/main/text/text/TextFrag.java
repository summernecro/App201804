package com.summer.record.ui.main.text.text;

//by summer on 2018-03-27.

import android.view.View;

import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.base.interf.OnFinishListener;
import com.android.lib.base.listener.ViewListener;
import com.android.lib.network.bean.res.BaseResBean;
import com.android.lib.network.news.NetAdapter;
import com.android.lib.network.news.UINetAdapter;
import com.android.lib.util.GsonUtil;
import com.android.lib.util.LogUtil;
import com.android.lib.util.fragment.two.FragManager2;
import com.android.lib.view.bottommenu.Msg;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.summer.record.R;
import com.summer.record.data.NetDataWork;
import com.summer.record.data.Record;
import com.summer.record.data.Records;
import com.summer.record.ui.main.record.record.RecordDAOpe;
import com.summer.record.ui.main.main.MainValue;
import com.summer.record.ui.main.main.RefreshI;
import com.summer.record.ui.main.text.textdeail.TextDetailFrag;

import java.util.ArrayList;

import butterknife.OnClick;
import butterknife.Optional;

public class TextFrag extends BaseUIFrag<TextUIOpe,RecordDAOpe,TextValue> implements ViewListener,RefreshI {

    @Override
    public void initNow() {
        super.initNow();



        //ArrayList<Record> list = (ArrayList<Record>) new Select().from(Record.class).queryList();


        NetDataWork.Data.getAllRecords(getBaseUIAct(), "text", new UINetAdapter<ArrayList<Record>>(getBaseUIFrag()) {
            @Override
            public void onSuccess(ArrayList<Record> o) {
                super.onSuccess(o);
                if(o==null){
                    return;
                }
                getPV().getRecords().clear();
                getPV().getRecords().addAll(o);
                getPV().getList().clear();
                getPV().getList().addAll(o);
                getPU().initTexts(getPV().getList(),TextFrag.this);
            }
        });

        NetDataWork.Data.getRecordInfo(getBaseUIAct(), Record.ATYPE_TEXT, new UINetAdapter<Records>(getBaseUIFrag()) {
            @Override
            public void onSuccess(Records o) {
                super.onSuccess(o);
                if(o==null){
                    return;
                }
                getPD().setRecordsInfo(o);
                getPU().updateTitle(o.getDoneNum()+"/"+o.getAllNum());
            }
        });
    }

    @Override
    protected boolean is注册事件总线() {
        return true;
    }


    @Override
    public void dealMesage(Msg event) {
        LogUtil.E("TextDB");
        ArrayList<Record> list = (ArrayList<Record>) new Select().from(Record.class).queryList();
        getPU().initTexts(list,this);
        ArrayList<Record> records = new ArrayList<>();
        records.add((Record) event.data);
        if(GsonUtil.getInstance().toJson(records.get(0)).equals(getPV().getTemStr())){
            return;
        }
        getPV().setTemStr(GsonUtil.getInstance().toJson(records.get(0)));
        getPD().updateRecords(getBaseUIFrag(),records,new NetAdapter<ArrayList<Record>>(getBaseUIAct()));

    }


    @Optional
    @OnClick({ R.id.tv_upload,R.id.tv_down,R.id.iv_search_back,R.id.tv_search})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_upload:
                getPD().setPagesize(10);
                ArrayList<Record> list = (ArrayList<Record>) new Select().from(Record.class).queryList();
                final ArrayList<Record> records = new ArrayList<>();
                getPD().updateRecordsStep(0,records,getBaseUIFrag(), list, new OnFinishListener() {
                    @Override
                    public void onFinish(Object o) {
                        if(!(o instanceof String)){
                            NetDataWork.Data.getAllRecords(getBaseAct(), Record.ATYPE_TEXT,new UINetAdapter<ArrayList<Record>>(getBaseUIFrag(),UINetAdapter.Loading) {
                                @Override
                                public void onSuccess(ArrayList<Record> o) {
                                    super.onSuccess(o);
                                    getPU().initTexts(o,TextFrag.this);
                                }

                                @Override
                                public void onNetFinish(boolean haveData, String url, BaseResBean baseResBean) {
                                    super.onNetFinish(haveData, url, baseResBean);
                                    getPU().updateTitle(baseResBean.getOther());
                                }
                            });
                        }
                    }
                });
                break;
            case R.id.tv_down:

                break;
            case R.id.iv_search_back:
                getPU().showHideSearch();
                getPV().getList().clear();
                getPV().getList().addAll(getPV().getRecords());
                getPU().initTexts(getPV().getList(),this);
                break;
            case R.id.tv_search:
                getPU().showHideSearch();
                break;
        }
    }


    @Override
    public void onInterupt(int i, View view) {
        switch (i){
            case ViewListener.TYPE_ONCLICK:
                FragManager2.getInstance().setStartAnim(R.anim.fade_in,R.anim.fade_out).setFinishAnim(R.anim.fade_in,R.anim.fade_out).start(getBaseUIAct(), MainValue.文字, TextDetailFrag.getInstance((Record) view.getTag(R.id.data)));
                break;
        }
    }

    @Override
    public void refresh(ArrayList<Record> o) {
        ArrayList<Record> records = new ArrayList<>();
        for(int i=0;o!=null&&i<o.size();i++){
            if(o.get(i).getAtype().equals("text")){
                records.add(o.get(i));
            }
        }
        if(records.size()==0){
            return;
        }
        getPU().showHideSearch();
        getPV().getList().clear();
        getPV().getList().addAll(records);
        getPU().initTexts(getPV().getList(),TextFrag.this);
    }
}
